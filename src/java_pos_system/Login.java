package java_pos_system;

import java.util.Scanner;

public abstract class Login {
    private String userID;
    private String userName;
    private String userPassword;

    public Login() {
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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
            setUserID(sc.nextLine());
            if (getUserID().equals("-1")) {
                return false;
            }
            System.out.print("Enter Password (-1 to cancel): ");
            setUserPassword(sc.nextLine());
            if (getUserPassword().equals("-1")) {
                return false;
            }

            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM member WHERE memberid = '" + getUserID()
                        + "' AND memberpassword = '" + getUserPassword() + "'");
                if (!db.hasResult()) {
                    System.out.println("Invalid user ID or password");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                db.next();
                setUserName(db.getString("membername"));
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
            setUserID(sc.nextLine());
            if (getUserID().equals("-1")) {
                return false;
            }
            System.out.print("Enter Password (-1 to cancel): ");
            setUserPassword(sc.nextLine());
            if (getUserPassword().equals("-1")) {
                return false;
            }

            try {
                Database db = new Database();
                db.runCommand(
                        "SELECT * FROM staff WHERE staffid = '" + getUserID() + "' AND staffpassword = '" + getUserPassword()
                                + "'");
                if (!db.hasResult()) {
                    System.out.println("Invalid user ID or password");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                db.next();
                setUserName(db.getString("staffname"));
                return true;
            } catch (Exception e) {
                System.out.println(e);
                sc.nextLine();
                return false;
            }
        }
    }
}