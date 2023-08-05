package java_pos_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RegPage implements ActionListener {
    private static JFrame frame = new JFrame();
    private static JButton regButton = new JButton("Register");
    private static JTextField userIDField = new JTextField();
    private static JTextField staffNameField = new JTextField();
    private static JPasswordField userPasswordField = new JPasswordField();
    private static JPasswordField userConfirmPasswordField = new JPasswordField();
    private static JTextField staffContactNoField = new JTextField();
    private static JLabel userIDLabel;
    private static JLabel staffNameLabel = new JLabel("Staff Name:");
    private static JLabel userPasswordLabel = new JLabel("Password:");
    private static JLabel userConfirmPasswordLabel = new JLabel("Confirm password:");
    private static JLabel staffContactNoLabel = new JLabel("Contact No:");

    public static void resetField() {
        userIDField.setText("");
        staffNameField.setText("");
        userPasswordField.setText("");
        userConfirmPasswordField.setText("");
        staffContactNoField.setText("");
    }

    public static boolean reportFailRegistration(String err) {
        JOptionPane.showMessageDialog(frame, "Registration failed! " + err, "Registration",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static boolean checkName() {
        String name = staffNameField.getText();
        if (name.length() < 1) {
            return reportFailRegistration("Name's length must be at least 1 character!");
        }
        String[] names = name.split(" ");
        for (String n : names) {
            char first = n.charAt(0);
            if (Character.isLowerCase(first)) {
                return reportFailRegistration("Name must start with uppercase letter!");
            }
        }
        return true;
    }

    public static boolean checkPassword() {
        String password = userPasswordField.getPassword().toString();
        String confirm = userConfirmPasswordField.getPassword().toString();
        if (!password.equals(confirm)) {
            return reportFailRegistration("Password and confirm password must be the same!");
        }
        if (password.length() < 8) {
            return reportFailRegistration("Password's length must be at least 8 characters!");
        }
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            if (Character.isLowerCase(c)) {
                hasLower = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        if (!hasUpper || !hasLower || !hasDigit) {
            return reportFailRegistration("Password must contain at least 1 uppercase, 1 lowercase, and 1 digit!");
        }
        return hasUpper && hasLower && hasDigit;
    }

    public static boolean checkContactNo() {
        String contactNo = staffContactNoField.getText();
        if (contactNo.length() < 10) {
            return reportFailRegistration("Contact No's length must be at least 10 characters!");
        }
        for (int i = 0; i < contactNo.length(); i++) {
            char c = contactNo.charAt(i);
            if (!Character.isDigit(c)) {
                return reportFailRegistration(
                        "Contact No must only contain digits! (Use 01234... format instead of +60 format)");
            }
        }
        return true;
    }

    public static void register() {
        resetField();
        String id = "";
        try {
            IDGenerator idgen = new IDGenerator("staff");
            id = idgen.getID("staffID");
        } catch (Exception err) {
            System.out.println(err);
        }
        regButton.addActionListener(new RegPage());
        frame.setTitle("Register Staff ID: " + id);
        frame.setSize(350, 250);
        userIDLabel = new JLabel("Staff ID:");
        userIDLabel.setBounds(10, 10, 10, 10);
        userIDField.setText(id);
        userIDField.setEditable(false);

        userIDLabel.setBounds(10, 10, 110, 25);
        staffNameLabel.setBounds(10, 40, 110, 25);
        userPasswordLabel.setBounds(10, 70, 110, 25);
        userConfirmPasswordLabel.setBounds(10, 100, 110, 25);
        staffContactNoLabel.setBounds(10, 130, 110, 25);

        userIDField.setBounds(130, 10, 200, 25);
        staffNameField.setBounds(130, 40, 200, 25);
        userPasswordField.setBounds(130, 70, 200, 25);
        userConfirmPasswordField.setBounds(130, 100, 200, 25);
        staffContactNoField.setBounds(130, 130, 200, 25);

        regButton.setBounds(120, 165, 100, 25);

        frame.add(userIDLabel);
        frame.add(staffNameLabel);
        frame.add(userPasswordLabel);
        frame.add(userConfirmPasswordLabel);
        frame.add(staffContactNoLabel);
        frame.add(userIDField);
        frame.add(staffNameField);
        frame.add(userPasswordField);
        frame.add(userConfirmPasswordField);
        frame.add(staffContactNoField);
        frame.add(regButton);

        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regButton) {
            System.out.println("Registering...");
            String staffID = userIDField.getText();
            String staffName = staffNameField.getText();
            String staffContactNo = staffContactNoField.getText();
            String password = String.valueOf(userPasswordField.getPassword());
            if (checkContactNo() && checkName() && checkPassword()) {
                try {
                    String sql = "INSERT INTO staff (staffID, staffName, staffContactNo, staffPassword) VALUES ('"
                            + staffID
                            + "', '" + staffName + "', '" + staffContactNo + "', '" + password + "')";
                    Database db = new Database();
                    db.runCommand(sql);
                    db.closeConnection();
                    JOptionPane.showMessageDialog(frame, "Registration successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } catch (Exception err) {
                    System.out.println(err);
                }
            }
        }
    }
}