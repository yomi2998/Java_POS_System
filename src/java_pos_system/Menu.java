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

    private boolean retrieveUserInfo() {
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

    private boolean updateUserInfo() {
        Database db = new Database();
        try {
            db.runCommand("UPDATE member SET membername = '" + getUserName() + "', memberaddress = '"
                    + getUserAddress() + "', memberphone = '" + getUserPhone() + "', memberemail = '"
                    + getUserEmail() + "', memberbalance = " + getUserBalance() + " WHERE memberid = '"
                    + getUserID() + "'");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void displayMemberMenu() {
        Screen.cls();
        
        System.out.println("Welcome, " + getUserName());
        System.out.printf("Your balance: RM %.2f\n", getUserBalance());
        System.out.println("1. Browse products");
        System.out.println("2. View cart/checkout");
        System.out.println("3. View profile");
        System.out.println("4. Edit profile");
        System.out.println("5. Top up");
        System.out.println("6. Payment methods");
        System.out.println("7. View order");
        System.out.println("8. Logout");
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
                if (choice > 7 || choice < 1)
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
                    if (topUp.performTopUpOperation()) {
                        System.out.println("Top up successful");
                        Screen.pause();
                    } else {
                        System.out.println("Top up cancelled");
                        Screen.pause();
                    }
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

    private boolean retrieveUserInfo() {
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

    private boolean updateUserInfo() {
        Database db = new Database();
        try {
            db.runCommand("UPDATE staff SET staffname = '" + getUserName() + "', staffaddress = '"
                    + getUserAddress() + "', staffphone = '" + getUserPhone() + "', staffemail = '"
                    + getUserEmail() + "', staffsalary = " + getUserSalary() + ", staffposition = '"
                    + getUserPosition() + "' WHERE staffid = '" + getUserID() + "'");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void displayStaffMenu() {
        Screen.cls();
        
        System.out.println("Welcome, " + getUserName());
        for (int i = 0; i < 9 + getUserName().length(); i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("1. View profile");
        System.out.println("2. Edit profile");
        System.out.println("3. View products");
        System.out.println("4. Add product");
        System.out.println("5. Edit product");
        System.out.println("6. Delete product");
        System.out.println("7. Log out");
        System.out.print("Enter your choice: ");
    }

    public void startStaffSession() {
        while (true) {
            displayStaffMenu();
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 7 || choice < 1)
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
                    System.out.println("Logout successful");
                    Screen.pause();
                    return;
                }
            }
        }
    }
}