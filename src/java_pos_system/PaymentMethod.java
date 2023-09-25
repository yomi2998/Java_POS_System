package java_pos_system;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentMethod {
    private String paymentID;
    private String userID;
    private String paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String methods[] = {
            "Credit Card",
            "Debit Card"
    };
    private int results;

    public PaymentMethod() {
    }

    public PaymentMethod(String userID) {
        this.userID = userID;
    }

    public PaymentMethod(String paymentID, String paymentMethod, String cardNumber) {
        this.paymentID = paymentID;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getPaymentID() {
        return this.paymentID;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getCensoredCardNumber() {
        return this.cardNumber.substring(0, 4) + "********" + this.cardNumber.substring(12);
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getCVV() {
        return this.cvv;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCVV(String cvv) {
        this.cvv = cvv;
    }

    private boolean submitPaymentMethod_Card() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            Database db = new Database();
            db.runCommand("INSERT INTO payment_method VALUES ('" + this.paymentID + "', '" + this.userID + "', '"
                    + this.paymentMethod + "', '"
                    + this.cardNumber + "', '" + this.expiryDate + "', '" + this.cvv + "')");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean performRegisterPaymentMethodOperation() {
        while (true) {
            IDGenerator idg = new IDGenerator("payment_method");
            try {
                this.paymentID = idg.getID("paymentid");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
            Scanner sc = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Register Payment Method");
            System.out.println("-----------------------");
            System.out.println("User ID: " + this.userID);
            System.out.println("Payment Methods:");
            for (int i = 0; i < this.methods.length; i++) {
                System.out.println((i + 1) + ". " + this.methods[i]);
            }
            System.out.println("-1. Cancel");
            System.out.print("Choose payment method: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == -1) {
                    return false;
                } else if (choice > this.methods.length || choice < 1) {
                    throw new Exception();
                }
                this.paymentMethod = this.methods[choice - 1];
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            while (true) {
                System.out.print("Enter card number: ");
                this.cardNumber = sc.nextLine();
                if (this.cardNumber.length() != 16) {
                    System.out.println("Invalid card number, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                } else {
                    break;
                }
            }
            System.out.print("Enter expiry date (MM/YY): ");
            this.expiryDate = sc.nextLine();
            System.out.print("Enter CVV: ");
            this.cvv = sc.nextLine();
            return submitPaymentMethod_Card();
        }
    }

    public void displayPaymentMethods() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM payment_method WHERE memberid = '" + this.userID + "'");
            List<PaymentMethod> paymentMethodList = new ArrayList<PaymentMethod>();
            while (db.hasResult()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentID(db.getString("paymentid"));
                paymentMethod.setPaymentMethod(db.getString("paymentmethod"));
                paymentMethod.setCardNumber(db.getString("cardnumber"));
                paymentMethodList.add(paymentMethod);
                results++;
            }
            if (results != 0) {
                System.out.println("Payment Methods:");
                for (int i = 0; i < paymentMethodList.size(); i++) {
                    System.out.println(paymentMethodList.get(i).getPaymentMethod() + " - "
                            + paymentMethodList.get(i).getCensoredCardNumber());

                }
            } else {
                System.out.println("No payment methods registered");
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("1. Register payment method");
        System.out.println("2. Delete payment method");
        System.out.println("-1. Back");
    }

    public boolean performDeletePaymentMethodOperation() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        if (results == 0) {
            System.out.println("No payment methods registered");
            return false;
        }
        while (true) {
            List<PaymentMethod> paymentMethodList = new ArrayList<PaymentMethod>();
            Scanner sc = new Scanner(System.in);
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
                System.out.print("Press enter to continue...");
                sc.nextLine();
                return false;
            }
            System.out.println("Payment Methods:");
            for (int i = 0; i < paymentMethodList.size(); i++) {
                System.out.println((i + 1) + ". " + paymentMethodList.get(i).getPaymentMethod() + " - "
                        + paymentMethodList.get(i).getCensoredCardNumber());
            }
            System.out.println("-1. Cancel");
            System.out.print("Which to delete?: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice == -1) {
                    return false;
                } else if (choice > paymentMethodList.size() || choice < 1) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
                System.out.print("Press enter to continue...");
                sc.nextLine();
                return false;
            }
            while (true) {
                System.out.print("Are you sure you want to delete this payment method? (Y/N): ");
                String confirm = "";
                try {
                    confirm = sc.nextLine();
                    if (confirm.toUpperCase().equals("Y") || confirm.toUpperCase().equals("N")) {
                        if (confirm.toUpperCase().equals("N")) {
                            return false;
                        }
                        break;
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
            }
            try {
                Database db = new Database();
                db.runCommand("DELETE FROM payment_method WHERE paymentid = '"
                        + paymentMethodList.get(choice - 1).getPaymentID() + "'");
                db.closeConnection();
                System.out.println("Payment method deleted successfully");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                return true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Press enter to continue...");
                sc.nextLine();
                return false;
            }
        }
    }

    public void startPaymentMethodSession() {
        while (true) {
            displayPaymentMethods();
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice == -1) {
                    return;
                } else if (choice > 2 || choice < 1 && choice != -1) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    if (performRegisterPaymentMethodOperation()) {
                        System.out.println("Payment method registered successfully");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                    } else {
                        System.out.println("Payment method registration cancelled");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                    }
                    break;
                case 2:
                    if (performDeletePaymentMethodOperation()) {
                        System.out.println("Payment method deleted successfully");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                    } else {
                        System.out.println("Payment method deletion cancelled");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                    }
                    break;
            }
        }
    }
}
