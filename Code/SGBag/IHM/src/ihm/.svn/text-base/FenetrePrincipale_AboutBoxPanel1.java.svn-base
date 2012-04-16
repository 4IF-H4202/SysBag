package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

public class FenetrePrincipale_AboutBoxPanel1 extends JPanelBackground {
    private JLabel labelAuthor = new JLabel();
    private JLabel labelCopyright = new JLabel();
    private JLabel labelCompany = new JLabel();
    private Border border = BorderFactory.createEtchedBorder();
    private XYLayout xYLayout1 = new XYLayout();
    private JPanelBackground logo = new JPanelBackground("src\\ihm\\images\\logo_application.png");

    public FenetrePrincipale_AboutBoxPanel1() {
        super("src\\ihm\\images\\background.jpg");
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(xYLayout1);
        this.setBorder( border );
        this.setSize(new Dimension(454, 329));
        labelAuthor.setText("Hexanôme 4202");
        labelAuthor.setFont(new Font("Calibri", 1, 15));
        labelAuthor.setForeground(new Color(0, 0, 132));
        labelCopyright.setText("(c) INSA IF");
        labelCopyright.setFont(new Font("Calibri", 2, 12));
        labelCompany.setText("BIM Creators");
        labelCompany.setFont(new Font("Calibri", 3, 12));
        labelCompany.setForeground(new Color(0, 82, 255));
        this.add(logo, new XYConstraints(3, 3, 194, 91));
        this.add( labelAuthor, new XYConstraints(198, 23, 115, 20));
        this.add( labelCopyright, new XYConstraints(198, 43, 85, 15));
        this.add( labelCompany, new XYConstraints(198, 58, 95, 15));
    }
}
