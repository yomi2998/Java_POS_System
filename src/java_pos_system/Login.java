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
            Screen.cls();
            Title.print("Login - member");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter User ID or user email (-1 to cancel): ");
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
                    db.runCommand("SELECT * FROM member WHERE memberemail = '" + getUserID()
                            + "' AND memberpassword = '" + getUserPassword() + "'");
                    if (!db.hasResult()) {
                        System.out.println("Invalid user ID/email or password");
                        Screen.pause();
                        continue;
                    }
                }
                db.next();
                setUserName(db.getString("membername"));
                setUserID(db.getString("memberid"));
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
            Screen.cls();
            Title.print("Login - staff");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter User ID or user email (-1 to cancel): ");
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
                        "SELECT * FROM staff WHERE staffid = '" + getUserID() + "' AND staffpassword = '"
                                + getUserPassword()
                                + "'");
                if (!db.hasResult()) {
                    db.runCommand("SELECT * FROM staff WHERE staffemail = '" + getUserID() + "' AND staffpassword = '"
                            + getUserPassword() + "'");
                    if (!db.hasResult()) {
                        System.out.println("Invalid user ID/email or password");
                        Screen.pause();
                        continue;
                    }
                }
                db.next();
                setUserName(db.getString("staffname"));
                setUserID(db.getString("staffid"));
                return true;
            } catch (Exception e) {
                System.out.println(e);
                sc.nextLine();
                return false;
            }
        }
    }
}