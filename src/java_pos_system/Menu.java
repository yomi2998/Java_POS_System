package java_pos_system;

import java.util.Scanner;

public abstract class Menu {
    private String userID;
    private String userName;
    private String userAddress;
    private String userPhone;
    private String userEmail;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
}

class MemberMenu extends Menu {
    private double userBalance;

    public MemberMenu(String userID, String userName) {
        setUserID(userID);
        setUserName(userName);
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public double getUserBalance() {
        return this.userBalance;
    }

    public boolean retrieveUserInfo() {
        Database db = new Database();
        try {
            db.runCommand("SELECT * FROM member WHERE memberid = '" + getUserID() + "'");
            if (!db.hasResult()) {
                return false;
            }
            db.next();
            setUserName(db.getString("membername"));
            setUserAddress(db.getString("memberaddress"));
            setUserPhone(db.getString("memberphone"));
            setUserEmail(db.getString("memberemail"));
            setUserBalance(db.getDouble("memberbalance"));
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Screen.pause();
            return false;
        }
    }

    public int getMessageCount() {
        try {
            Database db = new Database();
            int messageCount = 0;
            db.runCommand("SELECT * FROM notification WHERE memberid = '" + getUserID() + "'");
            while (db.next()) {
                messageCount++;
            }
            db.closeConnection();
            return messageCount;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Screen.pause();
            return 0;
        }
    }

    public void displayMemberMenu() {
        Screen.cls();
        
        Title.print(String.format("Welcome, " + getUserName() + "\nYour balance: RM%.2f", getUserBalance()));
        System.out.println("1. Browse products");
        System.out.println("2. View cart/checkout");
        System.out.println("3. View profile");
        System.out.println("4. Edit profile");
        System.out.println("5. Top up");
        System.out.println("6. Payment methods");
        System.out.println("7. Order history");
        System.out.println("8. Help");
        System.out.println("9. View Messages" + (getMessageCount() > 0 ? " [" + getMessageCount() + "]" : ""));
        System.out.println("10. Logout");
        System.out.print("Enter your choice: ");
    }

    public void startMemberSession() {
        while (true) {
            retrieveUserInfo();
            displayMemberMenu();
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 10 || choice < 1)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                Screen.pause();
                continue;
            }
            switch (choice) {
                case 1 -> {
                    Order order = new Order(getUserID());
                    if (order.startOrderItemSession()) {
                        System.out.println("Order successful");
                        Screen.pause();
                    } else {
                        System.out.println("Order cancelled");
                        Screen.pause();
                    }
                }
                case 2 -> {
                    Cart cart = new Cart(getUserID());
                    cart.startCheckCartSession();
                }
                case 3 -> {
                    MemberProfile viewProfile = new MemberProfile(getUserID());
                    viewProfile.startViewProfileSession();
                }
                case 4 -> {
                    MemberProfile viewProfile = new MemberProfile(getUserID());
                    if (viewProfile.startEditProfileSession()) {
                        System.out.println("Profile updated successfully");
                        Screen.pause();
                    } else {
                        System.out.println("Profile update cancelled");
                        Screen.pause();
                    }
                }
                case 5 -> {
                    TopUp topUp = new TopUp(getUserID());
                    topUp.startTopupSession();
                }
                case 6 -> {
                    PaymentMethod paymentMethod = new PaymentMethod(getUserID());
                    paymentMethod.startPaymentMethodSession();
                }
                case 7 -> {
                    ViewOrderedItems order = new ViewOrderedItems(getUserID());
                    order.startViewOrderSession();
                }
                case 8 -> {
                    Help help = new Help(getUserID(), getUserName());
                    help.startHelpSession();
                }
                case 9 -> {
                    Screen.cls();
                    Title.print("View Messages");
                    if(getMessageCount() == 0) {
                        System.out.println("No messages found.");
                        Screen.pause();
                    } else {
                        System.out.println("Your request from contact support has been mark as resolved, if you had requested any order cancellation, please check balance if the amount has been refunded.");
                        Screen.pause();
                        try {
                            Database db = new Database();
                            db.runCommand("DELETE FROM notification WHERE memberid = '" + getUserID() + "'");
                        } catch (Exception e) {
                            System.out.println("Error: " + e);
                            Screen.pause();
                        }
                    }
                }
                case 10 -> {
                    System.out.println("Logout successful");
                    Screen.pause();
                    return;
                }
            }
        }
    }
}

class StaffMenu extends Menu {
    private double userSalary;
    private String userPosition;

    public StaffMenu(String userID, String userName) {
        setUserID(userID);
        setUserName(userName);
    }

    public void setUserSalary(double userSalary) {
        this.userSalary = userSalary;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public double getUserSalary() {
        return this.userSalary;
    }

    public String getUserPosition() {
        return this.userPosition;
    }

    public boolean retrieveUserInfo() {
        Database db = new Database();
        try {
            db.runCommand("SELECT * FROM staff WHERE staffid = '" + getUserID() + "'");
            if (!db.hasResult()) {
                return false;
            }
            db.next();
            setUserName(db.getString("staffname"));
            setUserAddress(db.getString("staffaddress"));
            setUserPhone(db.getString("staffphone"));
            setUserEmail(db.getString("staffemail"));
            setUserSalary(db.getDouble("staffsalary"));
            setUserPosition(db.getString("staffposition"));
            db.closeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void displayStaffMenu() {
        Screen.cls();
        retrieveUserInfo();
        Title.print("Welcome, " + getUserName() + "\nYour position: " + getUserPosition());
        System.out.println();
        System.out.println("1. View profile");
        System.out.println("2. Edit profile");
        System.out.println("3. View products");
        System.out.println("4. Add product");
        System.out.println("5. Edit product");
        System.out.println("6. Delete product");
        System.out.println("7. Manage order");
        System.out.println("8. Member's request");
        System.out.println("9. Manage member's topup");
        System.out.println("10. Log out");
        System.out.print("Enter your choice: ");
    }

    public void startStaffSession() {
        while (true) {
            displayStaffMenu();
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 10 || choice < 1)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                Screen.pause();
                continue;
            }
            switch (choice) {
                case 1 -> {
                    StaffProfile viewProfile = new StaffProfile(getUserID());
                    viewProfile.startViewProfileSession();
                }
                case 2 -> {
                    StaffProfile editProfile = new StaffProfile(getUserID());
                    if (editProfile.startEditProfileSession()) {
                        System.out.println("Profile updated successfully");
                        Screen.pause();
                    } else {
                        System.out.println("Profile update cancelled");
                        Screen.pause();
                    }
                }
                case 3 -> {
                    Product viewProduct = new Product();
                    viewProduct.startViewProductSession();
                }
                case 4 -> {
                    while (true) {
                        Product addProduct = new Product();
                        if (addProduct.performRegisterProductOperation()) {
                            System.out.println("Product added successfully");
                            Screen.pause();
                            continue;
                        } else {
                            System.out.println("Product add cancelled");
                            Screen.pause();
                            break;
                        }
                    }
                }
                case 5 -> {
                    while (true) {
                        Product editProduct = new Product();
                        if (editProduct.performEditProductOperation()) {
                            System.out.println("Product edited successfully");
                            Screen.pause();
                            continue;
                        } else {
                            System.out.println("Product edit cancelled");
                            Screen.pause();
                            break;
                        }
                    }
                }
                case 6 -> {
                    while (true) {
                        Product deleteProduct = new Product();
                        if (deleteProduct.performDeleteProductOperation()) {
                            System.out.println("Product deleted successfully");
                            Screen.pause();
                            continue;
                        } else {
                            System.out.println("Product delete cancelled");
                            Screen.pause();
                            break;
                        }
                    }
                }
                case 7 -> {
                    ManageOrderedItems manageOrder = new ManageOrderedItems(getUserID());
                    manageOrder.startManageOrderSession();
                }
                case 8 -> {
                    StaffSupport staffSupport = new StaffSupport();
                    staffSupport.startCheckMessageSession();
                }
                case 9 -> {
                    ManageTopup manageTopUp = new ManageTopup();
                    manageTopUp.startManageTopupSession();
                }
                case 10 -> {
                    System.out.println("Logout successful");
                    Screen.pause();
                    return;
                }
            }
        }
    }
}