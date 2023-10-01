package java_pos_system;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class TopupInfo {
    private String transactionID;
    private String userID;
    private String paymentID;
    private double topUpAmount;
    private double newBalance;
    private String topUpDate;
    private String paymentMethod;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM payment_method WHERE paymentid = '" + paymentID + "'");
            db.next();
            String censoredCardNumber = db.getString("cardnumber");
            censoredCardNumber = censoredCardNumber.substring(0, 4) + "********" + censoredCardNumber.substring(12);
            this.paymentMethod = db.getString("paymentmethod") + " - " + censoredCardNumber;
            db.closeConnection();
        } catch (Exception e) {
            this.paymentMethod = "Deleted Payment Method";
        }
    }

    public double getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public String getTopUpDate() {
        return topUpDate;
    }

    public void setTopUpDate(String topUpDate) {
        this.topUpDate = topUpDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    TopupInfo(String transactionID) {
        setTransactionID(transactionID);
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM topup_transaction WHERE transactionid = '" + transactionID + "'");
            db.next();
            setUserID(db.getString("memberid"));
            setPaymentID(db.getString("paymentid"));
            setTopUpAmount(db.getDouble("topupamount"));
            setNewBalance(db.getDouble("newbalance"));
            setTopUpDate(db.getString("date"));
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    TopupInfo() {
    }
}

public class TopUp {
    private boolean isStaff;
    private String userID;
    private String paymentID;
    List<TopupInfo> topupInfoList = new ArrayList<TopupInfo>();

    public TopUp(String userID) {
        this.userID = userID;
    }

    public TopUp(String userID, boolean isStaff) {
        this.userID = userID;
        this.isStaff = isStaff;
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
            Screen.cls();
            if (!isPaymentMethodAvailable()) {
                System.out.println("No payment method available.");
                System.out.println("Please add a payment method first.");
                Screen.pause();
                return false;
            }
            Title.print("Top Up");
            System.out.println("User ID: " + this.userID);
            double topUpAmount = 0;
            while (true)
                try {
                    System.out.print("Enter top up amount (-1 to cancel): RM ");
                    topUpAmount = Double.parseDouble(sc.nextLine());
                    if (topUpAmount == -1) {
                        return false;
                    } else if (topUpAmount <= 0) {
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
            }
            for (int i = 0; i < paymentMethodList.size(); i++) {
                System.out.println((i + 1) + ". " + paymentMethodList.get(i).getPaymentMethod() + " - "
                        + paymentMethodList.get(i).getCensoredCardNumber());
            }
            System.out.println("-1. Cancel");
            int choice = 0;
            while (true) {
                try {
                    System.out.print("Choose payment method: ");
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
            while (true) {
                System.out.print("Confirm top up? (Y/N): ");
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    paymentID = paymentMethodList.get(choice - 1).getPaymentID();
                    try {
                        Database db = new Database();
                        IDGenerator idg = new IDGenerator("topup_transaction");
                        String transactionID = idg.getID("transactionid");
                        db.runCommand("SELECT * FROM member WHERE memberid = '" + this.userID + "'");
                        db.next();
                        double newBalance = db.getDouble("memberbalance") + topUpAmount;
                        db.runCommand("UPDATE member SET memberbalance = memberbalance + " + topUpAmount
                                + " WHERE memberid = '"
                                + this.userID + "'");
                        db.runCommand("INSERT INTO topup_transaction VALUES ('" + transactionID + "', '" + this.userID
                                + "', '"
                                + paymentID + "', " + topUpAmount + ", " + newBalance + ", NOW())");
                        return true;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        sc.nextLine();
                        return false;
                    }
                } else if (confirm.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.println("Invalid input, please try again.");
                    continue;
                }
            }

        }
    }

    public void viewTopupHistory() {
        while (true) {
            topupInfoList.clear();
            Scanner sc = new Scanner(System.in);
            Screen.cls();
            Title.print("Top Up History");
            System.out.println("User ID: " + this.userID);
            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM topup_transaction WHERE memberid = '" + this.userID + "' ORDER BY date DESC");
                if (db.hasResult()) {
                    while (db.next()) {
                        topupInfoList.add(new TopupInfo(db.getString("transactionid")));
                    }
                } else {
                    System.out.println("No top up history.");
                    Screen.pause();
                    return;
                }
                db.closeConnection();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            for(int i = 0; i < topupInfoList.size(); i++) {
                System.out.println((i + 1) + ". " + topupInfoList.get(i).getTopUpDate() + " - RM " + topupInfoList.get(i).getTopUpAmount() + " - " + topupInfoList.get(i).getPaymentMethod());
            }
            
            if(!isStaff) {
                Screen.pause();
                return;
            }
            System.out.println("-1. Back");
            int choice = 0;
            while (true) {
                try {
                    System.out.print("Choose top up transaction to undo: ");
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == -1) {
                        return;
                    } else if (choice > topupInfoList.size() || choice < 1) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                }
            }
            while (true) {
                System.out.print("Confirm undo top up? (Y/N): ");
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    try {
                        Database db = new Database();
                        db.runCommand("UPDATE member SET memberbalance = memberbalance - " + topupInfoList.get(choice - 1).getTopUpAmount()
                                + " WHERE memberid = '"
                                + this.userID + "'");
                        db.runCommand("DELETE FROM topup_transaction WHERE transactionid = '" + topupInfoList.get(choice - 1).getTransactionID() + "'");
                        System.out.println("Undo top up successful.");
                        Screen.pause();
                        break;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        sc.nextLine();
                        return;
                    }
                } else if (confirm.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.println("Invalid input, please try again.");
                    continue;
                }
            }
        }
    }

    public void startTopupSession() {
        while(true) {
            Screen.cls();
            Title.print("Top Up");
            System.out.println("1. Top Up");
            System.out.println("2. View Top Up History");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            try {
                Scanner sc = new Scanner(System.in);
                int choice = Integer.parseInt(sc.nextLine());
                if (choice > 3 || choice < 1)
                    throw new Exception();
                switch (choice) {
                    case 1 -> {
                        if (performTopUpOperation()) {
                            System.out.println("Top up successful.");
                            Screen.pause();
                        } else {
                            System.out.println("Top up cancelled.");
                            Screen.pause();
                        }
                    }
                    case 2 -> {
                        viewTopupHistory();
                    }
                    case 3 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                Screen.pause();
                continue;
            }
        }
    }
}

class ManageTopup {
    ManageTopup() {
        
    }
    public void startManageTopupSession() {
        while(true) {
            Screen.cls();
            Title.print("Manage Top Up");
            System.out.print("Enter member ID to view top up history (-1 to cancel):");
            try {
                Scanner sc = new Scanner(System.in);
                String userID = sc.nextLine();
                if(userID.equals("-1"))
                    return;
                TopUp topUp = new TopUp(userID.toUpperCase(), true);
                topUp.viewTopupHistory();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                Screen.pause();
                continue;
            }
        }
    }
}