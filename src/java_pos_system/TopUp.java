package java_pos_system;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class TopUp {
    private String userID;
    private String paymentID;

    public TopUp(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return this.userID;
    }

    public boolean isPaymentMethodAvailable() {
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM payment_method WHERE memberid = '" + this.userID + "'");
            if (db.hasResult()) {
                db.closeConnection();
                return true;
            } else {
                db.closeConnection();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean performTopUpOperation() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (!isPaymentMethodAvailable()) {
                System.out.println("No payment method available.");
                System.out.println("Please add a payment method first.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                return false;
            }
            System.out.println("Top Up");
            System.out.println("------");
            System.out.println("User ID: " + this.userID);
            double topUpAmount = 0;
            while (true)
                try {
                    System.out.print("Enter top up amount (-1 to cancel): RM ");
                    topUpAmount = Double.parseDouble(sc.nextLine());
                    if (topUpAmount == -1) {
                        return false;
                    } else if (topUpAmount < 0) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid top up amount.");
                }
            List<PaymentMethod> paymentMethodList = new ArrayList<PaymentMethod>();
            System.out.println("Payment Methods:");
            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM payment_method WHERE memberid = '" + this.userID + "'");
                while (db.hasResult()) {
                    PaymentMethod paymentMethod = new PaymentMethod();
                    paymentMethod.setPaymentID(db.getString("paymentid"));
                    paymentMethod.setPaymentMethod(db.getString("paymentmethod"));
                    paymentMethod.setCardNumber(db.getString("cardnumber"));
                    paymentMethodList.add(paymentMethod);
                }
                db.closeConnection();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            for (int i = 0; i < paymentMethodList.size(); i++) {
                System.out.println((i + 1) + ". " + paymentMethodList.get(i).getPaymentMethod() + " - "
                        + paymentMethodList.get(i).getCensoredCardNumber());
            }
            System.out.println("-1. Cancel");
            System.out.print("Choose payment method: ");
            int choice = 0;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == -1) {
                        return false;
                    } else if (choice > paymentMethodList.size() || choice < 1) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                }
            }
            paymentID = paymentMethodList.get(choice - 1).getPaymentID();
            try {
                Database db = new Database();
                IDGenerator idg = new IDGenerator("topup_transaction");
                String transactionID = idg.getID("transactionid");
                db.runCommand("SELECT * FROM member WHERE memberid = '" + this.userID + "'");
                db.next();
                double newBalance = db.getDouble("memberbalance") + topUpAmount;
                db.runCommand("UPDATE member SET memberbalance = memberbalance + " + topUpAmount + " WHERE memberid = '"
                        + this.userID + "'");
                db.runCommand("INSERT INTO topup_transaction VALUES ('" + transactionID + "', '" + this.userID + "', '"
                        + paymentID + "', " + topUpAmount + ", " + newBalance + ")");
                return true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
                return false;
            }
        }
    }
}
