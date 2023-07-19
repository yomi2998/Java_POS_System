/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author khshu
 */
public class GUI {
    

    public static void main(String args[]){
        //Creating the Frame
        JFrame frame = new JFrame("Welcome to ZZZTOJ Accessories");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);

        //Add the background
        Color color=new Color(109, 255 ,240);
        frame.getContentPane().setBackground(color);
        
//        // create a panel to hold the label
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//
//        // create the label and add it to the panel
//        ImageIcon imageIcon = new ImageIcon("path/to/image.jpg");
//        JLabel label = new JLabel(imageIcon);
//        panel.add(label, BorderLayout.CENTER);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Home");
        JMenu m2 = new JMenu("Phone Case");
        JMenu m3 = new JMenu("Power & Charger");
        JMenu m4 = new JMenu("Earphone");
        JMenu m5 = new JMenu("Adapter");
        JMenu m6 = new JMenu("Watch Bands");
        JMenu m7 = new JMenu("Screen Protectors");
        JMenu m8 = new JMenu("Holder");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Vivo");
        JMenuItem m12 = new JMenuItem("Oppo");
        JMenuItem m13 = new JMenuItem("Huawei");
        JMenuItem m14 = new JMenuItem("XiaoMi");
        JMenuItem m15 = new JMenuItem("Samsung");
        JMenuItem m16 = new JMenuItem("IPhone");
        m2.add(m11);
        m2.add(m12);
        m2.add(m13);
        m2.add(m14);
        m2.add(m15);
        m2.add(m16);
        mb.add(m3);
        JMenuItem m21 = new JMenuItem("Vivo");
        JMenuItem m22 = new JMenuItem("Oppo");
        JMenuItem m23 = new JMenuItem("Huawei");
        JMenuItem m24 = new JMenuItem("XiaoMi");
        JMenuItem m25 = new JMenuItem("Samsung");
        JMenuItem m26 = new JMenuItem("IPhone");
        m3.add(m21);
        m3.add(m22);
        m3.add(m23);
        m3.add(m24);
        m3.add(m25);
        m3.add(m26);
        mb.add(m4);
        JMenuItem m31 = new JMenuItem("Vivo");
        JMenuItem m32 = new JMenuItem("Oppo");
        JMenuItem m33 = new JMenuItem("Huawei");
        JMenuItem m34 = new JMenuItem("XiaoMi");
        JMenuItem m35 = new JMenuItem("Samsung");
        JMenuItem m36 = new JMenuItem("IPhone");
        m4.add(m31);
        m4.add(m32);
        m4.add(m33);
        m4.add(m34);
        m4.add(m35);
        m4.add(m36);
        mb.add(m5);
        JMenuItem m41 = new JMenuItem("Vivo");
        JMenuItem m42 = new JMenuItem("Oppo");
        JMenuItem m43 = new JMenuItem("Huawei");
        JMenuItem m44 = new JMenuItem("XiaoMi");
        JMenuItem m45 = new JMenuItem("Samsung");
        JMenuItem m46 = new JMenuItem("IPhone");
        m5.add(m41);
        m5.add(m42);
        m5.add(m43);
        m5.add(m44);
        m5.add(m45);
        m5.add(m46);
        mb.add(m6);
        JMenuItem m51 = new JMenuItem("Vivo");
        JMenuItem m52 = new JMenuItem("Oppo");
        JMenuItem m53 = new JMenuItem("Huawei");
        JMenuItem m54 = new JMenuItem("XiaoMi");
        JMenuItem m55 = new JMenuItem("Samsung");
        JMenuItem m56 = new JMenuItem("IWatch");
        m6.add(m51);
        m6.add(m52);
        m6.add(m53);
        m6.add(m54);
        m6.add(m55);
        m6.add(m56);
        mb.add(m7);
        JMenuItem m61 = new JMenuItem("Vivo");
        JMenuItem m62 = new JMenuItem("Oppo");
        JMenuItem m63 = new JMenuItem("Huawei");
        JMenuItem m64 = new JMenuItem("XiaoMi");
        JMenuItem m65 = new JMenuItem("Samsung");
        JMenuItem m66 = new JMenuItem("IWatch");
        m7.add(m61);
        m7.add(m62);
        m7.add(m63);
        m7.add(m64);
        m7.add(m65);
        m7.add(m66);
        mb.add(m8);
        
        //adding the menu bar to the frame
        frame.setJMenuBar(mb);
        
        //making the frame visible
        frame.setVisible(true);
    }


}
