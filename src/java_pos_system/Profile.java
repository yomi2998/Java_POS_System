package java_pos_system;

import java.util.Scanner;

public abstract class Profile {
    private String userID;
    private String userName;
    private String userAddress;
    private String userPhone;
    private String userEmail;
    private String userPassword;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean validateName(String name) {
        String[] names = name.split(" ");
        for (String n : names) {
            if (n.charAt(0) != Character.toUpperCase(n.charAt(0))) {
                return false;
            }
            for (int i = 0; i < n.length(); i++) {
                if (Character.isDigit(n.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        } else if (password.length() > 16) {
            return false;
        }
        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            } else if (Character.isUpperCase(password.charAt(i))) {
                hasUpper = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                hasLower = true;
            }
        }
        return hasDigit && hasUpper && hasLower;
    }
}

class MemberProfile extends Profile {
    private double userBalance;

    public MemberProfile(String userID) {
        setUserID(userID);
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public boolean updateUserInfo() {
        try {
            Database db = new Database();
            db.runCommand("UPDATE member SET memberpassword = '" + getUserPassword() + "', membername = '"
                    + getUserName() + "', memberaddress = '"
                    + getUserAddress() + "', memberphone = '" + getUserPhone() + "', memberemail = '"
                    + getUserEmail() + "', memberbalance = " + getUserBalance() + " WHERE memberid = '"
                    + getUserID() + "'");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean confirmPassword() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your password (-1 to cancel): ");
            String password = sc.nextLine();
            if (password.equals("-1")) {
                return false;
            }
            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM member WHERE memberid = '" + getUserID() + "' AND memberpassword = '"
                        + password + "'");
                if (!db.hasResult()) {
                    System.out.println("Incorrect password, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                return true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
    }

    public void retrieveUserInfo() {
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM member WHERE memberid = '" + getUserID() + "'");
            if (db.hasResult()) {
                db.next();
                setUserName(db.getString("membername"));
                setUserAddress(db.getString("memberaddress"));
                setUserPhone(db.getString("memberphone"));
                setUserEmail(db.getString("memberemail"));
                setUserBalance(db.getDouble("memberbalance"));
                setUserPassword(db.getString("memberpassword"));
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayProfile() {
        Screen.cls();
        
        System.out.println("Profile");
        System.out.println("-------");
        System.out.println("   User ID: " + getUserID());
        System.out.printf("   Balance: RM %.2f\n", getUserBalance());
        System.out.println("1. Name: " + getUserName());
        System.out.println("2. Password: ********");
        System.out.println("3. Address: " + getUserAddress());
        System.out.println("4. Phone: " + getUserPhone());
        System.out.println("5. Email: " + getUserEmail());
    }

    public void startViewProfileSession() {
        retrieveUserInfo();
        displayProfile();
        Scanner sc = new Scanner(System.in);
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    public boolean startEditProfileSession() {
        retrieveUserInfo();
        while (true) {
            displayProfile();
            System.out.println("-1. Cancel");
            System.out.println("99. Save changes");
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            while (true) {
                System.out.print("Which field do you want to edit? (1-5, -1 to cancel): ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == -1) {
                        return false;
                    } else if ((choice > 5 && choice != 99) || (choice < 1 && choice != -1)) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1 -> {
                    while (true) {
                        System.out.print("Enter your name (-1 to cancel): ");
                        String name = sc.nextLine();
                        if (name.equals("-1")) {
                            break;
                        }
                        if (validateName(name)) {
                            setUserName(name);
                            break;
                        } else {
                            System.out.println("Invalid name, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter your password (-1 to cancel): ");
                        String password = sc.nextLine();
                        if (password.equals("-1")) {
                            break;
                        }
                        if (validatePassword(password)) {
                            setUserPassword(password);
                            break;
                        } else {
                            System.out.println("Incorrect password format, password must be 8-16 characters long, "
                                    + "contain at least one digit, one uppercase letter, and one lowercase letter, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Enter your address (-1 to cancel): ");
                    String address = sc.nextLine();
                    if (address.equals("-1")) {
                        break;
                    }
                    setUserAddress(address);
                }
                case 4 -> {
                    while (true) {
                        System.out.print("Enter your phone number (-1 to cancel): ");
                        String phone = sc.nextLine();
                        if (phone.equals("-1")) {
                            break;
                        }
                        if (phone.length() < 10 || phone.length() > 12 || !phone.matches("[0-9]+")) {
                            System.out.println(
                                    "Invalid phone number, number length must be between 10 to 12, enter numbers only.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            continue;
                        }
                        try {
                            setUserPhone(phone);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid phone number, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 5 -> {
                    while (true) {
                        System.out.print("Enter your email (-1 to cancel): ");
                        try {
                            String email = sc.nextLine();
                            if (email.equals("-1")) {
                                break;
                            }
                            int alias = 0;
                            int dot = 0;
                            if (!email.contains("@")) {
                                throw new Exception();
                            } else {
                                alias = email.indexOf("@");
                            }
                            if (!email.contains(".")) {
                                throw new Exception();
                            } else {
                                dot = email.indexOf(".");
                            }
                            if (alias > dot) {
                                throw new Exception();
                            }
                            setUserEmail(email);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid email format, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 99 -> {
                    if (confirmPassword() && updateUserInfo())
                        return true;
                    break;
                }
            }
        }
    }
}

class StaffProfile extends Profile {
    private double userSalary;
    private String userPosition;

    public StaffProfile(String userID) {
        setUserID(userID);
    }

    public double getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(double userSalary) {
        this.userSalary = userSalary;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public boolean updateUserInfo() {
        try {
            Database db = new Database();
            db.runCommand("UPDATE staff SET staffpassword = '" + getUserPassword() + "', staffname = '"
                    + getUserName() + "', staffaddress = '"
                    + getUserAddress() + "', staffphone = '" + getUserPhone() + "', staffemail = '"
                    + getUserEmail() + "', staffsalary = '" + getUserSalary() + "', staffposition = '" + getUserPosition() + "' WHERE staffid = '"
                    + getUserID() + "'");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Press enter to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            return false;
        }
    }

    public boolean confirmPassword() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your password (-1 to cancel): ");
            String password = sc.nextLine();
            if (password.equals("-1")) {
                return false;
            }
            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM staff WHERE staffid = '" + getUserID() + "' AND staffpassword = '"
                        + password + "'");
                if (!db.hasResult()) {
                    System.out.println("Incorrect password, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                return true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
    }

    public void retrieveUserInfo() {
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM staff WHERE staffid = '" + getUserID() + "'");
            if (db.hasResult()) {
                db.next();
                setUserName(db.getString("staffname"));
                setUserAddress(db.getString("staffaddress"));
                setUserPhone(db.getString("staffphone"));
                setUserEmail(db.getString("staffemail"));
                setUserSalary(db.getDouble("staffsalary"));
                setUserPosition(db.getString("staffposition"));
                setUserPassword(db.getString("staffpassword"));
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayProfile() {
        Screen.cls();
        
        System.out.println("Profile");
        System.out.println("-------");
        System.out.println("   User ID: " + getUserID());
        System.out.printf("   Salary: RM %.2f\n", getUserSalary());
        System.out.println("   Position: " + getUserPosition());
        System.out.println("1. Name: " + getUserName());
        System.out.println("2. Password: ********");
        System.out.println("3. Address: " + getUserAddress());
        System.out.println("4. Phone: " + getUserPhone());
        System.out.println("5. Email: " + getUserEmail());
    }

    public void startViewProfileSession() {
        retrieveUserInfo();
        displayProfile();
        Scanner sc = new Scanner(System.in);
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    public boolean startEditProfileSession() {
        retrieveUserInfo();
        while (true) {
            displayProfile();
            System.out.println("-1. Cancel");
            System.out.println("99. Save changes");
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            while (true) {
                System.out.print("Which field do you want to edit? (1-5, -1 to cancel): ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == -1) {
                        return false;
                    } else if ((choice > 5 && choice != 99) || (choice < 1 && choice != -1)) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1 -> {
                    while (true) {
                        System.out.print("Enter your name (-1 to cancel): ");
                        String name = sc.nextLine();
                        if (name.equals("-1")) {
                            break;
                        }
                        if (validateName(name)) {
                            setUserName(name);
                            break;
                        } else {
                            System.out.println("Invalid name, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter your password (-1 to cancel): ");
                        String password = sc.nextLine();
                        if (password.equals("-1")) {
                            break;
                        }
                        if (validatePassword(password)) {
                            setUserPassword(password);
                            break;
                        } else {
                            System.out.println("Incorrect password format, password must be 8-16 characters long, "
                                    + "contain at least one digit, one uppercase letter, and one lowercase letter, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Enter your address (-1 to cancel): ");
                    String address = sc.nextLine();
                    if (address.equals("-1")) {
                        break;
                    }
                    setUserAddress(address);
                }
                case 4 -> {
                    while (true) {
                        System.out.print("Enter your phone number (-1 to cancel): ");
                        String phone = sc.nextLine();
                        if (phone.equals("-1")) {
                            break;
                        }
                        if (phone.length() < 10 || phone.length() > 12 || !phone.matches("[0-9]+")) {
                            System.out.println(
                                    "Invalid phone number, number length must be between 10 to 12, enter numbers only.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            continue;
                        }
                        try {
                            setUserPhone(phone);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid phone number, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 5 -> {
                    while (true) {
                        System.out.print("Enter your email (-1 to cancel): ");
                        try {
                            String email = sc.nextLine();
                            if (email.equals("-1")) {
                                break;
                            }
                            int alias = 0;
                            int dot = 0;
                            if (!email.contains("@")) {
                                throw new Exception();
                            } else {
                                alias = email.indexOf("@");
                            }
                            if (!email.contains(".")) {
                                throw new Exception();
                            } else {
                                dot = email.indexOf(".");
                            }
                            if (alias > dot) {
                                throw new Exception();
                            }
                            setUserEmail(email);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid email format, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 99 -> {
                    if (confirmPassword() && updateUserInfo())
                        return true;
                    break;
                }
            }
        }
    }
}