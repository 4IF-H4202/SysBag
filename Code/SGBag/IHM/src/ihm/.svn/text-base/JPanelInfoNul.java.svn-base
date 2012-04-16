package ihm;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import noyau.Aeroport;
import noyau.ElementNoyau;

public class JPanelInfoNul extends JPanelInfo{
    private JPanelBackground jPanel1;
    private JLabel jLabel1 = new JLabel();

    public JPanelInfoNul(Rectangle rect) {
        super(rect);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void jbInit() throws Exception {
        jPanel1 = new JPanelBackground("src\\ihm\\images\\logo_application.png");
        jPanel1.setBounds(new Rectangle(55, 10, 110, 60));
        jLabel1.setText("En attente de données");
        jLabel1.setBounds(new Rectangle(0, 65, 220, 45));
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jLabel1, null);
        this.add(jPanel1, null);
    }
    
    public void update() {
        // Rien à faire ...
    }
    
    public ElementNoyau getElement() {
        return null;
    }
}
