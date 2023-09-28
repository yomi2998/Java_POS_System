package java_pos_system;

import java.util.Scanner;

public abstract class Register {
    private String userID;
    private String userPassword;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                setName(name);
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
        setAddress(address);
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
                setPhoneNumber(phone);
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
                setEmail(email);
                return true;
            } catch (Exception e) {
                System.out.println("Incorrect e-mail format! Please try again.");
            }
    }
}

class RegisterMember extends Register {
    private static double balance = 0.0;

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        RegisterMember.balance = balance;
    }

    public void printFullMemberInfo() {
        Screen.cls();

        System.out.println("Registration details");
        System.out.println("--------------------");
        System.out.println("ID: " + getUserID());
        System.out.println("1. Password: " + getUserPassword());
        System.out.println("2. Name: " + getName());
        System.out.println("3. Address: " + getAddress());
        System.out.println("4. Phone Number: " + getPhoneNumber());
        System.out.println("5. E-mail: " + getEmail());
    }

    public boolean submitMemberRegistration() {
        try {
            Database db = new Database();
            db.runCommand("INSERT INTO member VALUES ('" + getUserID() + "', '" + getUserPassword() + "', '"
                    + getName() + "', '" + getAddress() + "', '" + getPhoneNumber() + "', '" + getEmail() + "', "
                    + getBalance() + ")");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean performRegisterOperation() {
        Title.print("Register - member");
        Scanner sc = new Scanner(System.in);
        IDGenerator idgen = new IDGenerator("member");
        try {
            setUserID(idgen.getID("memberID"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Your ID is: " + getUserID());
        if (!askPassword() || !askName() || !askAddress() || !askPhone() || !askEmail()) {
            return false;
        }
        while (true) {
            printFullMemberInfo();
            System.out.print("Confirm registration? (Y/N): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                return submitMemberRegistration();
            }
            else if (!confirm.equalsIgnoreCase("N")) {
                System.out.println("Invalid choice, please try again.");
                Screen.pause();
                continue;
            }
                System.out.print("Which information do you want to change? (1-5, -1 to cancel, -2 to abort): ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case -1:
                        break;
                    case -2:
                        return false;
                    case 1:
                        askPassword();
                        break;
                    case 2:
                        askName();
                        break;
                    case 3:
                        askAddress();
                        break;
                    case 4:
                        askPhone();
                        break;
                    case 5:
                        askEmail();
                        break;
                }
            }
        }
    }


class RegisterStaff extends Register {
    private double salary;
    private String position;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void printFullStaffInfo() {
        Screen.cls();

        System.out.println("Registration details");
        System.out.println("--------------------");
        System.out.println("ID: " + getUserID());
        System.out.println("1. Password: " + getUserPassword());
        System.out.println("2. Name: " + getName());
        System.out.println("3. Address: " + getAddress());
        System.out.println("4. Phone Number: " + getPhoneNumber());
        System.out.println("5. E-mail: " + getEmail());
        System.out.println("6. Salary: " + getSalary());
        System.out.println("7. Position: " + getPosition());
    }

    public boolean submitStaffRegistration() {
        try {
            Database db = new Database();
            db.runCommand("INSERT INTO staff VALUES ('" + getUserID() + "', '" + getUserPassword() + "', '"
                    + getName() + "', '" + getAddress() + "', '" + getPhoneNumber() + "', '" + getEmail() + "', "
                    + getSalary() + ", '" + getPosition() + "')");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean askSalary() {
        Scanner sc = new Scanner(System.in);
        while (true)
            try {
                System.out.print("Enter Salary (-1 to cancel): ");
                double salary = Double.parseDouble(sc.nextLine());
                if (salary == -1) {
                    return false;
                }
                if (salary <= 0) {
                    throw new Exception();
                }
                setSalary(salary);
                return true;
            } catch (Exception e) {
                System.out.println("Salary must be a number, and must not less than 0.");
            }
    }

    public boolean askPosition() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Position (-1 to cancel): ");
        String position = sc.nextLine();
        if (position.equals("-1")) {
            return false;
        }
        setPosition(position);
        return true;
    }

    public boolean performRegisterOperation() {
        Screen.cls();
        Title.print("Register - staff");
        Scanner sc = new Scanner(System.in);
        IDGenerator idgen = new IDGenerator("staff");
        try {
            setUserID(idgen.getID("staffID"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Your ID is: " + getUserID());
        if (!askPassword() || !askName() || !askAddress() || !askPhone() || !askEmail() || !askSalary()
                || !askPosition()) {
            return false;
        }
        while (true) {
            printFullStaffInfo();
            System.out.print("Confirm registration? (Y/N): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                Screen.cls();
                return submitStaffRegistration();
            } else if(!confirm.equalsIgnoreCase("N")) {
                System.out.println("Invalid choice, please try again.");
                Screen.pause();
                continue;
            }
                System.out.print("Which information do you want to change? (1-7, -1 to cancel, -2 to abort): ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case -1:
                        break;
                    case -2:
                        return false;
                    case 1:
                        askPassword();
                        break;
                    case 2:
                        askName();
                        break;
                    case 3:
                        askAddress();
                        break;
                    case 4:
                        askPhone();
                        break;
                    case 5:
                        askEmail();
                        break;
                    case 6:
                        askSalary();
                        break;
                    case 7:
                        askPosition();
                        break;
                }
            }
        }
    }
