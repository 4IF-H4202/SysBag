package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import noyau.Chariot;

import noyau.Chariot.Action;

import noyau.CheminChariot;
import noyau.ElementNoyau;
import noyau.VoieGarage;

public class JPanelInfoChariot extends JPanelInfo {
    private JPanelBackground jPanel1;
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel labelID = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel labelVol = new JLabel();
    private JLabel labelPosition = new JLabel();
    private JLabel labelPositionAbsolue = new JLabel();
    private JLabel labelBagage = new JLabel();
    private JLabel labelDirection = new JLabel();
    private JFormattedTextField labelVitesse = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JFormattedTextField labelVitesse1 = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JLabel jLabel9 = new JLabel();

    public JPanelInfoChariot(Rectangle rect, Image imgChar) {
        super(rect);
        try {
            jPanel1 = new JPanelBackground(imgChar);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void jbInit() throws Exception {  
        jPanel1.setBounds(new Rectangle(140, 10, 70, 110));
        jLabel1.setText("ID :");
        jLabel1.setBounds(new Rectangle(10, 0, 65, 40));
        jLabel2.setText("Position :");
        jLabel2.setBounds(new Rectangle(10, 20, 65, 35));
        jLabel3.setText("Vitesse :");
        jLabel3.setBounds(new Rectangle(10, 55, 65, 40));
        jSeparator1.setBounds(new Rectangle(10, 112, 120, 15));
        jLabel4.setText("Bagage :");
        jLabel4.setBounds(new Rectangle(10, 105, 65, 40));
        jLabel8.setText("Vol :");
        jLabel5.setText("Direction :");
        jLabel8.setBounds(new Rectangle(10, 125, 65, 40));
        jLabel5.setBounds(new Rectangle(10, 145, 65, 40));
        labelID.setBounds(new Rectangle(60, 0, 65, 40));
        labelID.setHorizontalAlignment(SwingConstants.TRAILING);
        labelID.setHorizontalTextPosition(SwingConstants.LEFT);
        labelPosition.setHorizontalAlignment(SwingConstants.TRAILING);
        labelPosition.setHorizontalTextPosition(SwingConstants.LEFT);
        labelPosition.setBounds(new Rectangle(50, 20, 75, 35));
        labelPosition.setText("<wait>");
        labelPositionAbsolue.setHorizontalAlignment(SwingConstants.TRAILING);
        labelPositionAbsolue.setHorizontalTextPosition(SwingConstants.LEFT);
        labelPositionAbsolue.setBounds(new Rectangle(50, 30, 75, 35));
        labelPositionAbsolue.setFont(new Font("Calibri", 2, 10));
        labelPositionAbsolue.setForeground(Color.BLACK);
        labelPositionAbsolue.setText("<wait>");
        labelVitesse.setBounds(new Rectangle(60, 60, 40, 20));
        labelVitesse.setBackground(FenetrePrincipale.COLOR_BLEUPALE);
        labelVitesse.setText("00");
        labelBagage.setHorizontalAlignment(SwingConstants.TRAILING);
        labelBagage.setHorizontalTextPosition(SwingConstants.LEFT);
        labelBagage.setBounds(new Rectangle(60, 105, 65, 40));
        labelBagage.setText("<wait>");
        labelDirection.setHorizontalAlignment(SwingConstants.TRAILING);
        labelDirection.setHorizontalTextPosition(SwingConstants.LEFT);
        labelDirection.setBounds(new Rectangle(60, 145, 65, 40));
        labelDirection.setText("<wait>");
        labelDirection.setFont(new Font("Calibri", 1, 14));
        labelVol.setHorizontalAlignment(SwingConstants.TRAILING);
        labelVol.setHorizontalTextPosition(SwingConstants.LEFT);
        labelVol.setBounds(new Rectangle(60, 125, 65, 40));
        labelVol.setText("<wait>");
        labelVol.setFont(new Font("Calibri", 1, 14));
        labelBagage.setFont(new Font("Calibri", 1, 12));
        labelBagage.setForeground(new Color(0, 0, 165));
        labelVitesse.setFont(new Font("Calibri", 1, 13));
        labelVitesse.setHorizontalAlignment(JTextField.CENTER);
        jLabel6.setText("km/h");
        jLabel6.setBounds(new Rectangle(105, 60, 65, 30));
        jLabel6.setFont(new Font("Calibri", 0, 10));
        jLabel7.setText("Tapis :");
        jLabel7.setBounds(new Rectangle(10, 75, 65, 40));
        labelVitesse1.setBounds(new Rectangle(60, 85, 40, 20));
        labelVitesse1.setBackground(FenetrePrincipale.COLOR_BLEUPALE);
        labelVitesse1.setText("00");
        labelVitesse1.setFont(new Font("Calibri", 1, 13));
        labelVitesse1.setHorizontalAlignment(JTextField.CENTER);
        labelVitesse1.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    labelVitesse_keyTyped(labelVitesse1, e);
                }
            });
        labelVitesse1.addActionListener(new ActionListener() { public void actionPerformed( ActionEvent ae ) { setVitesse();}});
        jLabel9.setText("km/h");
        jLabel9.setBounds(new Rectangle(105, 80, 65, 30));
        jLabel9.setFont(new Font("Calibri", 0, 10));
        labelPosition.setFont(new Font("Calibri", 1, 12));
        labelPosition.setForeground(new Color(0, 0, 165));
        labelID.setFont(new Font("Calibri", 1, 14));
        labelID.setForeground(new Color(0, 132, 0));
        labelVitesse.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    labelVitesse_keyTyped(labelVitesse, e);
                }
            });
        labelVitesse1.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    labelVitesse_keyTyped(labelVitesse1, e);
                }
            });
        labelVitesse.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { setVitesse();}} );
        labelVitesse1.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { setVitesseTapis();}} );
        this.add(jLabel9, null);
        this.add(labelVitesse1, null);
        this.add(jLabel7, null);
        this.add(jLabel6, null);
        this.add(labelDirection, null);
        this.add(labelBagage, null);
        this.add(labelVitesse, null);
        this.add(labelPosition, null);
        this.add(labelID, null);
        this.add(jLabel5, null);
        this.add(jLabel4, null);
        this.add(jSeparator1, null);
        this.add(jLabel3, null);
        this.add(jLabel2, null);
        this.add(jLabel1, null);
        this.add(jPanel1, null);
        this.add(jLabel8, null);
        this.add(labelVol, null);
        this.add(jPanel1, null);
        this.add(labelPositionAbsolue, null);
    }
    
    public Double arrondi2(Double val) {
        return (double)Math.round(val*100)/100;
    }
  
    public void update() {
        // TO DO : Mettre à jour les label pour les nombres :
        Chariot chariot = (Chariot)(this.element);
        labelID.setText(chariot.getId().toString());
        labelPosition.setText(chariot.getCheminActuel().getNom());
        labelPositionAbsolue.setText("(" + arrondi2(chariot.getCoordonneesAbsolues().getX()) + ","+arrondi2(chariot.getCoordonneesAbsolues().getX())+")");
        
        if (chariot.getBagagePorte() != null) {
            labelVol.setText(chariot.getBagagePorte().getVol().getNom());
            labelBagage.setText("#"+chariot.getBagagePorte().getId().toString()+"(" + chariot.getBagagePorte().getPoids() + "kg");
            if (chariot.getAction() == Chariot.Action.deposerBagage) { // Le chariot se dirige vers un toboggan :
                if (chariot.getTrajet().size() == 0) { // Il est juste sur le rail avant :
                    try{
                        labelDirection.setText("Tob#"+chariot.getCheminActuel().getNoeudFin().getPointDepot().getId());
                    }catch (Exception e){
                        labelDirection.setText("Tob#");
                    }
                }
                else { // Il est plus loin :
                    try{
                        labelDirection.setText("Tob#"+chariot.getTrajet().getLast().getNoeudFin().getPointDepot().getId());  
                    } catch (Exception e){
                        labelDirection.setText("Tob#");
                    }
                }
            }   
            else
            { 
                labelDirection.setText("Manu"); 
            }
        }
        else {
            labelBagage.setText("Aucun");
            labelVol.setText("Aucun");
            if (chariot.getAction() == Chariot.Action.retirerBagage) { // Le chariot se dirige vers une voie de garage :
                if (chariot.getTrajet().size() == 0) { // Il est juste sur le rail avant :
                    try {
                        labelDirection.setText("Tap#"+chariot.getCheminActuel().getNoeudFin().getPointRetrait().getId());
                    } catch (Exception e) {
                        labelDirection.setText("Tap");
                    }
                }
                else { // Il est plus loin :
                    try {
                        labelDirection.setText("Tap#"+chariot.getTrajet().getLast().getNoeudFin().getPointRetrait().getId());
                    } catch (Exception e) {
                        labelDirection.setText("Tap");
                    }                    
                }
            }
            else if (chariot.getAction() == Chariot.Action.garerChariot) { // Le chariot se dirige vers une voie de garage :
                if (chariot.getTrajet().size() == 0) { // Il est juste sur le rail avant :
                    labelDirection.setText("Gar#"+chariot.getCheminActuel().getId());                    }
                else { // Il est plus loin :
                    labelDirection.setText("Gar#"+chariot.getTrajet().getLast().getId());
                }


/*                 if (chariot.getTrajet() == null) { // Il est juste sur le rail avant :
                    for (CheminChariot chemin : chariot.getCheminActuel().getNoeudFin().getVoiesSuiv()) {
                        if(chemin instanceof VoieGarage){
                            // Récupère le nombre de chariots de la voie de garage en bout du parcours
                            labelDirection.setText("Gar#"+chemin.getId());
                        }
                    }
                }
                else { // Il est plus loin :
                    for (CheminChariot chemin : chariot.getTrajet().getLast().getNoeudFin().getVoiesSuiv()) {
                        if(chemin instanceof VoieGarage){
                            // Récupère le nombre de chariots de la voie de garage en bout du parcours
                            labelDirection.setText("Gar#"+chemin.getId());
                        }
                    }
                } */
            }
            else {
                labelDirection.setText("Manu");
            }
        }
    }
    
    private void labelVitesse_keyTyped(JFormattedTextField jText, KeyEvent e) {
        String s = jText.getText();
        if (s.length() > 1) { e.consume(); }
    }

    private void setVitesse() {
        Chariot chariot = (Chariot)(this.element);
        chariot.setVitesse(Double.parseDouble(labelVitesse.getText()));
        labelVitesse.setText(chariot.getVitesse().toString());
    }
    
    private void setVitesseTapis() {
        Chariot chariot = (Chariot)(this.element);
        chariot.setVitesseTapis(Double.parseDouble(labelVitesse1.getText()));
        labelVitesse1.setText(chariot.getVitesseTapis().toString());
    }

    public void setElement(ElementNoyau e0) {
        super.setElement(e0);
        Chariot chariot = (Chariot)e0;
        labelVitesse.setText(chariot.getVitesse().toString());
        labelVitesse1.setText(chariot.getVitesseTapis().toString());
    }


    private void setVitesse(ActionEvent e) {
    }

    private void labelVitesse_keyTyped(KeyEvent e) {
    }
}
