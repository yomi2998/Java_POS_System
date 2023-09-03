/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_pos_system;
import javax.swing.*;
/**
 *
 * @author TAR UMT
 */
public class GUI {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("ZZZTOJ Accessories POS System"); // JFrame title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit application
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(420,420); // set x-dimension and y-dimension of frame
        frame.setVisible(true); //make frame visible
        
        ImageIcon image = new ImageIcon("logo.png"); // create image icon
        frame.setIconImage(image.getImage()); // change icon of frame
    }
}
