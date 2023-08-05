package java_pos_system;

import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;

public class LoginPage implements ActionListener {

    private static JFrame frame = new JFrame();
    private static JButton loginButton = new JButton("Login");
    private static JButton regButton = new JButton("Register");
    private static JTextField userIDField = new JTextField();
    private static JPasswordField userPasswordField = new JPasswordField();
    private static JLabel userIDLabel = new JLabel("Staff ID:");
    private static JLabel userPasswordLabel = new JLabel("Password:");

    public static void startSession() {
        userIDLabel.setBounds(10, 10, 75, 25);
        userPasswordLabel.setBounds(10, 40, 75, 25);

        userIDField.setBounds(85, 10, 200, 25);
        userPasswordField.setBounds(85, 40, 200, 25);

        loginButton.setBounds(50, 80, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(new LoginPage());

        regButton.setBounds(150, 80, 100, 25);
        regButton.setFocusable(false);
        regButton.addActionListener(new LoginPage());

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(regButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(310, 160);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Login - Pos System");
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regButton) {
            try {
                RegPage.register();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        if (e.getSource() == loginButton) {

            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if(userID.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(frame, "Login failed! Please fill in all the fields.", "Login",
                JOptionPane.ERROR_MESSAGE);
                return;
            }

            Database db = new Database();
            List<String> info = new LinkedList<String>();
            try {
                db.runCommand("SELECT * FROM staff WHERE staffID = '" + userID + "'");
                info = db.getString("staffPassword");
                db.closeConnection();
            } catch (Exception err) {
                System.out.println(err);
            }
            if (info.size() == 1) {
                if (info.get(0).equals(password)) {
                    frame.setVisible(false);
                    WelcomePage.startSession();
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed! Please check your password.", "Login",
                JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (info.size() == 0) {
                    JOptionPane.showMessageDialog(frame, "Login failed! No such user.", "Login",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Login failed! Multiple account with staff ID detected, please contact database administrator.",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}