package java_pos_system;

import java.util.Scanner;

public abstract class Login {
    private String userID;
    private String userPassword;
    private String userType;

    public Login(String userID, String userPassword) {
        this.userID = userID;
        this.userPassword = userPassword;
    }

    public Login() {
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

class LoginMember extends Login {
    public boolean performLoginOperation() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Login - member");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter User ID (-1 to cancel): ");
            String userID = sc.nextLine();
            if (userID.equals("-1")) {
                return false;
            }
            System.out.print("Enter Password (-1 to cancel): ");
            String userPassword = sc.nextLine();
            if (userPassword.equals("-1")) {
                return false;
            }

            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM member WHERE memberid = '" + userID
                        + "' AND memberpassword = '" + userPassword + "'");
                if (!db.hasResult()) {
                    System.out.println("Invalid user ID or password");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                return true;
            } catch (Exception e) {
                System.out.println(e);
                sc.nextLine();
                return false;
            }
        }
    }
}

class LoginStaff extends Login {
    public boolean performLoginOperation() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Login - staff");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter User ID (-1 to cancel): ");
            String userID = sc.nextLine();
            if (userID.equals("-1")) {
                return false;
            }
            System.out.print("Enter Password (-1 to cancel): ");
            String userPassword = sc.nextLine();
            if (userPassword.equals("-1")) {
                return false;
            }

            try {
                Database db = new Database();
                db.runCommand(
                        "SELECT * FROM staff WHERE staffid = '" + userID + "' AND staffpassword = '" + userPassword
                                + "'");
                if (!db.hasResult()) {
                    System.out.println("Invalid user ID or password");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                return true;
            } catch (Exception e) {
                System.out.println(e);
                sc.nextLine();
                return false;
            }
        }
    }
}