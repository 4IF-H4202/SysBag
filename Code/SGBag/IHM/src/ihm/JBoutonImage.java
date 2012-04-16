package ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JComponent;

public class JBoutonImage extends JComponent {

    @SuppressWarnings("compatibility:8881531414041718325")
    private static final long serialVersionUID = 4172300076669553052L;


    public enum Etat {clicked, hover, unhover, inactive};
    
    protected Etat etat;
    protected boolean blocked;
    protected transient Image imageBackgroundActive;
    protected transient Image imageBackgroundHover;
    protected transient Image imageBackgroundClicked;
    protected transient Image imageBackgroundInactive;
    
    public JBoutonImage(String linkActive, String linkHover, String linkClicked, String linkInactive, boolean b) {
        super();
        this.blocked = b;
        try {
            imageBackgroundActive = ImageIO.read(new File(linkActive));
        } catch (IOException e) {
        }
        try {
            imageBackgroundHover = ImageIO.read(new File(linkHover));
        } catch (IOException e) {
        }
        try {
            imageBackgroundClicked = ImageIO.read(new File(linkClicked));
        } catch (IOException e) {
        }
        try {
             imageBackgroundInactive = ImageIO.read(new File(linkInactive));
        } catch (IOException e) {
        }
    }
    
    public void setEtat(JBoutonImage.Etat etat) {
        if (!this.blocked) {
            this.etat = etat;
            Graphics g = this.getGraphics();
            update(g);
        }
    }

    public JBoutonImage.Etat getEtat() {
        return etat;
    }
    
    public void setStatut() {
        this.blocked = !this.blocked ;
    }

    public boolean isBlocked() {
        return blocked;
    }
    
    
    public void paintComponent(Graphics g)
    {
        // Draw the previously loaded image to Component.
        Dimension dimPanel = this.getSize();
        BufferedImage buf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        /* On dessine sur le Graphics de l'image bufferisée. */
        Graphics2D g2D = buf.createGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (etat == Etat.unhover) { g2D.drawImage(imageBackgroundActive, 0, 0, this.getWidth(), this.getHeight(), null); }
        else if (etat == Etat.hover) { g2D.drawImage(imageBackgroundHover, 0, 0, this.getWidth(), this.getHeight(), null); }
        else if (etat == Etat.clicked) { g2D.drawImage(imageBackgroundClicked, 0, 0, this.getWidth(), this.getHeight(), null); }
        else { g2D.drawImage(imageBackgroundInactive, 0, 0, this.getWidth(), this.getHeight(), null); }
        
        g2D.dispose();

        g.drawImage(buf, 0, 0, null) ;
    }

    
}
