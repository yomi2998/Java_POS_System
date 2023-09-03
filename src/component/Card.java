package component;

import model.Model_Card;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

public class Card extends javax.swing.JPanel {

    public Color getColor1(){
        return color1;
    }
    
    public void setColor1(Color color1){
        this.color1 = color1;
    }
    
    public Color getColor2(){
        return color2;
    }
    
    public void setColor2(Color color2){
        this.color2 = color2;
    }
    
    private Color color1;
    private Color color2;
    public Card() {
        initComponents();
        setOpaque(false);
        color1=Color.BLACK;
        color2=Color.WHITE;
    }

    public void setData(Model_Card data){
        lbIcon.setIcon(data.getIcon());
        lbTitle.setText(data.getTitle());
        lbValues.setText(data.getValues());
        lbDescription.setText(data.getDescription());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        lbDescription = new javax.swing.JLabel();
        lbValues = new javax.swing.JLabel();

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Values");

        setBackground(new java.awt.Color(204, 204, 204));

        lbIcon.setBackground(new java.awt.Color(204, 204, 204));
        lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/stock.png"))); // NOI18N

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle.setText("Title");

        lbDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbDescription.setForeground(new java.awt.Color(255, 255, 255));
        lbDescription.setText("Description");

        lbValues.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbValues.setForeground(new java.awt.Color(255, 255, 255));
        lbValues.setText("Values");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbValues)
                    .addComponent(lbDescription)
                    .addComponent(lbTitle)
                    .addComponent(lbIcon))
                .addContainerGap(296, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbTitle)
                .addGap(18, 18, 18)
                .addComponent(lbValues)
                .addGap(18, 18, 18)
                .addComponent(lbDescription)
                .addContainerGap(66, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs){
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g=new GradientPaint(0,0, color1,0,getHeight(), color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.setColor(new Color(255,255,255,50));
        g2.fillOval(getWidth() - (getHeight() / 2), 10, getHeight(), getHeight());
        g2.fillOval(getWidth() - (getHeight() / 2) - 20, getHeight() / 2 + 20, getHeight(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbDescription;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbValues;
    // End of variables declaration//GEN-END:variables
}
