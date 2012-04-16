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

public class JPanelInfoGeneral extends JPanelInfo {
    private JPanelBackground jPanel1;
    private JPanelBackground jPanel2;
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel labelNbChariotsMarche = new JLabel();
    private JLabel labelNbChariotsGarage = new JLabel();
    private JLabel labelNbChariotsPanne = new JLabel();
    private JLabel labelNbBagCharges = new JLabel();
    private JLabel labelNbBagTransfert = new JLabel();
    private JLabel labelNbBagControle = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();

    public JPanelInfoGeneral(Rectangle rect, Image imgChar,  Image imgBag) {
        super(rect);
        try {
            jPanel1 = new JPanelBackground(imgChar);
            jPanel2 = new JPanelBackground(imgBag);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void jbInit() throws Exception {
        jPanel1.setBounds(new Rectangle(20, 10, 35, 50));
        jPanel2.setBounds(new Rectangle(10, 125, 50, 45));
        jLabel1.setText("En marche :");
        jLabel1.setBounds(new Rectangle(75, -5, 70, 45));
        jLabel2.setText("En garage :");
        jLabel2.setBounds(new Rectangle(75, 10, 115, 50));
        jPanel2.setBounds(new Rectangle(15, 85, 50, 40));
        jLabel3.setText("En panne :");
        jLabel3.setBounds(new Rectangle(75, 35, 70, 40));
        jLabel5.setText("Chargés :");
        jLabel5.setBounds(new Rectangle(75, 65, 70, 40));
        jLabel6.setText("En transfert :");
        jLabel6.setBounds(new Rectangle(75, 85, 70, 40));
        jLabel7.setText("En contrôle :");
        jLabel7.setBounds(new Rectangle(75, 105, 70, 40));
        labelNbChariotsMarche.setText("0");
        labelNbChariotsMarche.setBounds(new Rectangle(135, 0, 70, 35));
        labelNbChariotsMarche.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNbChariotsMarche.setForeground(new Color(0, 165, 0));
        labelNbBagControle.setFont(new Font("Calibri", 1, 14));
        labelNbBagControle.setForeground(new Color(198, 0, 0));
        labelNbBagCharges.setFont(new Font("Calibri", 1, 14));
        labelNbBagCharges.setForeground(new Color(0, 165, 0));
        labelNbBagTransfert.setFont(new Font("Calibri", 1, 14));
        labelNbBagTransfert.setForeground(new Color(214, 107, 0));
        labelNbChariotsGarage.setFont(new Font("Calibri", 1, 14));
        labelNbChariotsGarage.setForeground(new Color(214, 107, 0));
        labelNbChariotsPanne.setFont(new Font("Calibri", 1, 14));
        labelNbChariotsPanne.setForeground(new Color(198, 0, 0));
        labelNbChariotsMarche.setFont(new Font("Calibri", 1, 14));
        labelNbChariotsGarage.setText("0");
        labelNbChariotsGarage.setBounds(new Rectangle(135, 20, 70, 30));
        labelNbChariotsGarage.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNbChariotsPanne.setText("0");
        labelNbChariotsPanne.setBounds(new Rectangle(135, 40, 70, 30));
        labelNbChariotsPanne.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNbBagCharges.setText("0");
        labelNbBagCharges.setBounds(new Rectangle(135, 70, 70, 30));
        labelNbBagCharges.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNbBagTransfert.setText("0");
        labelNbBagTransfert.setBounds(new Rectangle(135, 90, 70, 30));
        labelNbBagTransfert.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNbBagControle.setText("0");
        labelNbBagControle.setBounds(new Rectangle(135, 110, 70, 30));
        labelNbBagControle.setHorizontalAlignment(SwingConstants.RIGHT);
        jSeparator1.setBounds(new Rectangle(10, 70, 190, 10));
        this.add(jSeparator1, null);
        this.add(labelNbBagControle, null);
        this.add(labelNbBagTransfert, null);
        this.add(labelNbBagCharges, null);
        this.add(labelNbChariotsPanne, null);
        this.add(labelNbChariotsGarage, null);
        this.add(labelNbChariotsMarche, null);
        this.add(jLabel7, null);
        this.add(jLabel6, null);
        this.add(jLabel5, null);
        this.add(jLabel3, null);
        this.add(jLabel2, null);
        this.add(jLabel1, null);
        this.add(jPanel1, null);
        this.add(jPanel2, null);
    }
    
    public void update() {
        // TO DO : Mettre à jour les label pour les nombres :
        Aeroport aeroport = (Aeroport)element;
        labelNbChariotsPanne.setText("0");
        Integer nbBagEnControle = aeroport.getNbBagagesEnControle();
        Integer nbBagCharges = aeroport.getNbBagagesCharges();
        Integer nbCharDansVG = aeroport.getNbChariotsVG();
        Integer nbBagDepl = aeroport.gettNbBagagesEnDeplacement();
        Integer nbCharTotal = aeroport.getChariots().size();
        labelNbBagControle.setText(nbBagEnControle.toString());
        labelNbChariotsMarche.setText(Integer.toString(nbCharTotal-nbCharDansVG));
        labelNbChariotsGarage.setText(nbCharDansVG.toString());
        labelNbBagTransfert.setText(nbBagDepl.toString());
        labelNbBagCharges.setText(nbBagCharges.toString());
    }
}
