package java_pos_system;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class WelcomePage {
    
    private static JFrame frame = new JFrame();
    private static JLabel welcomeLabel = new JLabel("Hello!");

    public static void startSession() {
        welcomeLabel.setBounds(0,0,200,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,25));

        frame.add(welcomeLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
