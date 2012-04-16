
package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import noyau.Aeroport;
import noyau.ElementNoyau;
import noyau.TapisRoulant;
import noyau.VoieGarage;

public class JPanelInfoVoieGarage extends JPanelInfo {
    private JPanelBackground jPanel1;
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel labelID = new JLabel();
    private JLabel labelNbChariots = new JLabel();

    public JPanelInfoVoieGarage(Rectangle rect, Image img) {
        super(rect);
        try {
            jPanel1 = new JPanelBackground(img);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void jbInit() throws Exception {       
        jPanel1.setBounds(new Rectangle(140, 10, 70, 55));
        jLabel1.setText("ID :");
        jLabel1.setBounds(new Rectangle(10, 0, 65, 40));
        jLabel2.setText("Chariots :");
        jLabel2.setBounds(new Rectangle(10, 20, 65, 35));
 
        labelID.setBounds(new Rectangle(60, 0, 65, 40));
        labelID.setHorizontalAlignment(SwingConstants.TRAILING);
        labelID.setHorizontalTextPosition(SwingConstants.LEFT);
        labelNbChariots.setHorizontalAlignment(SwingConstants.TRAILING);
        labelNbChariots.setHorizontalTextPosition(SwingConstants.LEFT);
        labelNbChariots.setBounds(new Rectangle(60, 20, 65, 35));
        labelNbChariots.setText("<wait>");
        labelNbChariots.setFont(new Font("Calibri", 1, 14));
        labelNbChariots.setForeground(new Color(0, 0, 165));
        labelID.setFont(new Font("Calibri", 1, 14));
        labelID.setForeground(new Color(0, 132, 0));
        this.add(labelNbChariots, null);
        this.add(labelID, null);
        this.add(jLabel2, null);
        this.add(jLabel1, null);
        this.add(jPanel1, null);
    }
    
    public void update() {
        VoieGarage vg = (VoieGarage)element;
        labelID.setText(vg.getId().toString());
        labelNbChariots.setText(Integer.toString(vg.getNombreChariots()));

        // TO DO : Mettre à jour les label pour les nombres :

    }
}
