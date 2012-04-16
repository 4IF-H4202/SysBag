package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import noyau.Aeroport;
import noyau.Chariot;
import noyau.ElementNoyau;
import noyau.TapisRoulant;

public class JPanelInfoTapisRoulant extends JPanelInfo {
    private JPanelBackground jPanel1;
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel labelID = new JLabel();
    private JFormattedTextField labelVitesse = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JLabel labelLongueur = new JLabel();
    private JLabel labelNbBagTransfert = new JLabel();
    private JLabel labelNbBagCharges = new JLabel();
    private JPanelBackground jPanel2;

    public JPanelInfoTapisRoulant(Rectangle rect, Image imgTR,  Image imgBag) {
        super(rect);
        try {
            jPanel1 = new JPanelBackground(imgTR);
            jPanel2 = new JPanelBackground(imgBag);
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
        jLabel2.setText("Vitesse :");
        jLabel2.setBounds(new Rectangle(10, 20, 65, 35));
        jLabel3.setText("Longueur :");
        jLabel3.setBounds(new Rectangle(10, 35, 65, 40));
        jSeparator1.setBounds(new Rectangle(10, 70, 120, 15));
        jLabel4.setText("En transfert :");
        jLabel4.setBounds(new Rectangle(10, 65, 65, 40));
        jLabel5.setText("En contrôle :");
        jLabel5.setBounds(new Rectangle(10, 85, 65, 40));
        labelID.setBounds(new Rectangle(60, 0, 65, 40));
        labelID.setHorizontalAlignment(SwingConstants.TRAILING);
        labelID.setHorizontalTextPosition(SwingConstants.LEFT);
        labelVitesse.setHorizontalAlignment(SwingConstants.TRAILING);
        labelVitesse.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    labelVitesse_keyTyped(labelVitesse, e);
                }
            });
        labelVitesse.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { setVitesse();}} );
        
        labelVitesse.setBounds(new Rectangle(90, 28, 40, 20));
        labelVitesse.setText("<wait>");
        labelLongueur.setHorizontalAlignment(SwingConstants.TRAILING);
        labelLongueur.setHorizontalTextPosition(SwingConstants.LEFT);
        labelLongueur.setBounds(new Rectangle(60, 40, 65, 30));
        labelLongueur.setText("<wait>");
        labelNbBagTransfert.setHorizontalAlignment(SwingConstants.TRAILING);
        labelNbBagTransfert.setHorizontalTextPosition(SwingConstants.LEFT);
        labelNbBagTransfert.setBounds(new Rectangle(60, 65, 65, 40));
        labelNbBagTransfert.setText("<wait>");
        labelNbBagCharges.setHorizontalAlignment(SwingConstants.TRAILING);
        labelNbBagCharges.setHorizontalTextPosition(SwingConstants.LEFT);
        labelNbBagCharges.setBounds(new Rectangle(60, 85, 65, 40));
        labelNbBagCharges.setText("<wait>");
        labelNbBagCharges.setFont(new Font("Calibri", 1, 14));
        labelNbBagCharges.setForeground(new Color(198, 0, 0));
        jPanel2.setBounds(new Rectangle(155, 75, 45, 35));
        labelNbBagTransfert.setFont(new Font("Calibri", 1, 14));
        labelNbBagTransfert.setForeground(new Color(255, 132, 0));
        labelLongueur.setFont(new Font("Calibri", 1, 14));
        labelVitesse.setFont(new Font("Calibri", 1, 14));
        labelVitesse.setForeground(new Color(0, 0, 165));
        labelID.setFont(new Font("Calibri", 1, 14));
        labelID.setForeground(new Color(0, 132, 0));
        this.add(jPanel2, null);
        this.add(labelNbBagCharges, null);
        this.add(labelNbBagTransfert, null);
        this.add(labelLongueur, null);
        this.add(labelVitesse, null);
        this.add(labelID, null);
        this.add(jLabel5, null);
        this.add(jLabel4, null);
        this.add(jSeparator1, null);
        this.add(jLabel3, null);
        this.add(jLabel2, null);
        this.add(jLabel1, null);
        this.add(jPanel1, null);
    }
    
    private void setVitesse() {
        TapisRoulant chariot = (TapisRoulant)(this.element);
        chariot.setVitesse(Double.parseDouble(labelVitesse.getText()));
        labelVitesse.setText(chariot.getVitesse().toString());
    }
    
    public void update() {
        TapisRoulant tr = (TapisRoulant)element;
        labelID.setText(tr.getId().toString());
        labelLongueur.setText(Double.toString(tr.getLongueur()));
        labelNbBagTransfert.setText(Integer.toString(tr.getBagagesPortes().size()));
        labelNbBagCharges.setText(Integer.toString(tr.getBagagesAttente().size()));

        // TO DO : Mettre à jour les label pour les nombres :

    }
    
    private void labelVitesse_keyTyped(JFormattedTextField jText, KeyEvent e) {
        String s = jText.getText();
        if (s.length() > 1) { e.consume(); }
    }
    
    public void setElement(ElementNoyau e0) {
        super.setElement(e0);
        TapisRoulant tap = (TapisRoulant)e0;
        labelVitesse.setText(tap.getVitesse().toString());
    }
}
