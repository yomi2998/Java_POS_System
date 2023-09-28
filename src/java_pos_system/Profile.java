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

    public boolean validateEmail(String email) {
        int alias = email.indexOf('@');
        int dot = email.indexOf('.');
        if (alias == -1 || dot == -1) {
            return false;
        }
        return alias < dot;
    }

    public boolean validatePhone(String phone) {
        if (phone.length() < 10) {
            return false;
        } else if (phone.length() > 12) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean askPassword() {
        Scanner sc = new Scanner(System.in);
        while (true)
            try {
                System.out.print("Enter Password (-1 to cancel): ");
                String pass = sc.nextLine();
                if (pass.equals("-1")) {
                    return false;
                }
                if (!validatePassword(pass)) {
                    throw new Exception();
                }
                setUserPassword(pass);
                return true;
            } catch (Exception e) {
                System.out.println(
                        "Password must be 8-16 characters long, contain at least one digit, one uppercase and one lowercase letter.");
            }
    }

    public boolean askName() {
        Scanner sc = new Scanner(System.in);
        while (true)
            try {
                System.out.print("Enter Name (-1 to cancel): ");
                String name = sc.nextLine();
                if (name.equals("-1")) {
                    return false;
                }
                if (!validateName(name)) {
                    throw new Exception();
                }
                setUserName(name);
                return true;
            } catch (Exception e) {
                System.out.println("Name must be in format: Firstname Lastname");
            }
    }

    public boolean askAddress() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Address (-1 to cancel): ");
        String address = sc.nextLine();
        if (address.equals("-1")) {
            return false;
        }
        setUserAddress(address);
        return true;
    }

    public boolean askPhone() {
        Scanner sc = new Scanner(System.in);
        while (true)
            try {
                System.out.print("Enter Phone Number (-1 to cancel): ");
                String phone = sc.nextLine();
                if (phone.equals("-1")) {
                    return false;
                }
                if (!validatePhone(phone)) {
                    throw new Exception();
                }
                setUserPhone(phone);
                return true;
            } catch (Exception e) {
                System.out.println(
                        "Phone number must be between 10 to 12 digits long, enter digits only.");
            }
    }

    public boolean askEmail() {
        Scanner sc = new Scanner(System.in);
        while (true)
            try {
                System.out.print("Enter e-mail (-1 to cancel): ");
                String email = sc.nextLine();
                if (email.equals("-1")) {
                    return false;
                }
                if (!validateEmail(email)) {
                    throw new Exception();
                }
                setUserEmail(email);
                return true;
            } catch (Exception e) {
                System.out.println("Incorrect e-mail format! Please try again.");
            }
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
                    Screen.pause();
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

        Title.print("Profile");
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
        Screen.pause();
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
                    Screen.pause();
                }
            }
            switch (choice) {
                case 1 -> {
                    askName();
                }
                case 2 -> {
                    askPassword();
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
                    askPhone();
                }
                case 5 -> {
                    askEmail();
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
                    + getUserEmail() + "', staffsalary = '" + getUserSalary() + "', staffposition = '"
                    + getUserPosition() + "' WHERE staffid = '"
                    + getUserID() + "'");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Screen.pause();
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
                    Screen.pause();
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

        Title.print("Profile");
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
        Screen.pause();
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
                    Screen.pause();
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
                            Screen.pause();
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
                            Screen.pause();
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
                            Screen.pause();
                            continue;
                        }
                        try {
                            setUserPhone(phone);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid phone number, please try again.");
                            Screen.pause();
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
                            Screen.pause();
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