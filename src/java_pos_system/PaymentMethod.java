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

    public boolean submitPaymentMethod_Card() {
        Screen.cls();

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
            Screen.cls();
            Title.print("Register Payment Method");
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
                Screen.pause();
                continue;
            }
            while (true) {
                System.out.print("Enter card number (-1 to cancel): ");
                this.cardNumber = sc.nextLine();
                if(this.cardNumber.equals("-1")) {
                    return false;
                }
                if (this.cardNumber.length() != 16) {
                    System.out.println("Invalid card number, please try again.");
                    Screen.pause();
                    continue;
                }
                try {
                    Database db = new Database();
                    db.runCommand("SELECT * FROM payment_method WHERE cardnumber = '" + this.cardNumber + "'");
                    if (db.hasResult()) {
                        System.out.println("Card number already registered, please try again.");
                        Screen.pause();
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    Screen.pause();
                    return false;
                }
                break;
            }
            while (true) {
                String expiryDateRegex = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$";
                System.out.print("Enter expiry date (MM/YY) (-1 to cancel): ");
                this.expiryDate = sc.nextLine();
                if (this.expiryDate.equals("-1")) {
                    return false;
                } else
                if (!this.expiryDate.matches(expiryDateRegex)) {
                    System.out.println("Invalid expiry date, please try again.");
                    Screen.pause();
                    continue;
                } else {
                    break;
                }
            }
            while(true) {
            System.out.print("Enter CVV (-1 to cancel): ");
            this.cvv = sc.nextLine();
            if (this.cvv.equals("-1")) {
                return false;
            } else if (this.cvv.length() != 3 || !this.cvv.matches("[0-9]+")) {
                System.out.println("Invalid CVV, please try again.");
                Screen.pause();
                continue;
            } else {
                break;
            }
        }
            return submitPaymentMethod_Card();
        }
    }

    public void displayPaymentMethods() {
        Screen.cls();
        Title.print("Payment Methods");
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM payment_method WHERE memberid = '" + this.userID + "'");
            List<PaymentMethod> paymentMethodList = new ArrayList<PaymentMethod>();
            if (db.hasResult())
                while (db.next()) {
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
        Screen.cls();

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
                if (db.hasResult())
                    while (db.next()) {
                        PaymentMethod paymentMethod = new PaymentMethod();
                        paymentMethod.setPaymentID(db.getString("paymentid"));
                        paymentMethod.setPaymentMethod(db.getString("paymentmethod"));
                        paymentMethod.setCardNumber(db.getString("cardnumber"));
                        paymentMethodList.add(paymentMethod);
                    }
                db.closeConnection();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                Screen.pause();
                return false;
            }
            Title.print("Delete Payment Method");
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
                Screen.pause();
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
                    Screen.pause();
                    continue;
                }
            }
            try {
                Database db = new Database();
                db.runCommand("DELETE FROM payment_method WHERE paymentid = '"
                        + paymentMethodList.get(choice - 1).getPaymentID() + "'");
                db.closeConnection();
                System.out.println("Payment method deleted successfully");
                Screen.pause();
                return true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                Screen.pause();
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
                Screen.pause();
                continue;
            }
            switch (choice) {
                case 1:
                    if (performRegisterPaymentMethodOperation()) {
                        System.out.println("Payment method registered successfully");
                        Screen.pause();
                    } else {
                        System.out.println("Payment method registration cancelled");
                        Screen.pause();
                    }
                    break;
                case 2:
                    if (performDeletePaymentMethodOperation()) {
                        System.out.println("Payment method deleted successfully");
                        Screen.pause();
                    } else {
                        System.out.println("Payment method deletion cancelled");
                        Screen.pause();
                    }
                    break;
            }
        }
    }
}
