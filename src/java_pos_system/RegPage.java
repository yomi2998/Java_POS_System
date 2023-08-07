package java_pos_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegPage implements ActionListener {
    private static JFrame frame = new JFrame();
    private static JButton regButton = new JButton("Register");
    private static JTextField staffIDField = new JTextField();
    private static JTextField staffNameField = new JTextField();
    private static JPasswordField staffPasswordField = new JPasswordField();
    private static JPasswordField staffConfirmPasswordField = new JPasswordField();
    private static JTextField staffContactNoField = new JTextField();
    private static JLabel staffIDLabel = new JLabel("Staff ID:");
    private static JLabel staffNameLabel = new JLabel("Staff Name:");
    private static JLabel staffPasswordLabel = new JLabel("Password:");
    private static JLabel staffConfirmPasswordLabel = new JLabel("Confirm password:");
    private static JLabel staffContactNoLabel = new JLabel("Contact No:");

    public static void resetField() {
        staffIDField.setText("");
        staffNameField.setText("");
        staffPasswordField.setText("");
        staffConfirmPasswordField.setText("");
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
        String password = new String(staffPasswordField.getPassword());
        String confirm = new String(staffConfirmPasswordField.getPassword());
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
        if (contactNo.length() < 10 || contactNo.length() > 11) {
            return reportFailRegistration("Contact No's length must be at least 10 and not more than 11 characters!");
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
        frame.setTitle("Register Staff ID: " + id);
        frame.setSize(350, 250);
        staffIDLabel.setBounds(10, 10, 10, 10);
        staffIDField.setText(id);
        staffIDField.setEditable(false);

        staffIDLabel.setBounds(10, 10, 110, 25);
        staffNameLabel.setBounds(10, 40, 110, 25);
        staffPasswordLabel.setBounds(10, 70, 110, 25);
        staffConfirmPasswordLabel.setBounds(10, 100, 110, 25);
        staffContactNoLabel.setBounds(10, 130, 110, 25);

        staffIDField.setBounds(130, 10, 200, 25);
        staffNameField.setBounds(130, 40, 200, 25);
        staffPasswordField.setBounds(130, 70, 200, 25);
        staffConfirmPasswordField.setBounds(130, 100, 200, 25);
        staffContactNoField.setBounds(130, 130, 200, 25);

        regButton.setBounds(120, 165, 100, 25);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        regButton.addActionListener(new RegPage());

        frame.add(staffIDLabel);
        frame.add(staffNameLabel);
        frame.add(staffPasswordLabel);
        frame.add(staffConfirmPasswordLabel);
        frame.add(staffContactNoLabel);
        frame.add(staffIDField);
        frame.add(staffNameField);
        frame.add(staffPasswordField);
        frame.add(staffConfirmPasswordField);
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
            String staffID = staffIDField.getText();
            String staffName = staffNameField.getText();
            String staffContactNo = staffContactNoField.getText();
            String password = String.valueOf(staffPasswordField.getPassword());
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