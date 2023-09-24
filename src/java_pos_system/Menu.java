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
            setUserName(db.getString("membername"));
            setUserAddress(db.getString("memberaddress"));
            setUserPhone(db.getString("memberphone"));
            setUserEmail(db.getString("memberemail"));
            setUserBalance(db.getDouble("memberbalance"));
            db.closeConnection();
            return true;
        } catch (Exception e) {
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
        System.out.println("Welcome, " + getUserName());
        System.out.println("Your balance: " + getUserBalance());
        System.out.println("1. Buy products");
        System.out.println("2. View cart");
        System.out.println("3. View profile");
        System.out.println("4. Edit profile");
        System.out.println("5. Top up");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }

    public void startMemberSession() {
        while (true) {
            displayMemberMenu();
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 6 || choice < 1)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
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
}