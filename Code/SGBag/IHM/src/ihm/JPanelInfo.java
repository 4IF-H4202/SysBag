package ihm;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Rectangle;

import javax.swing.JPanel;

import noyau.ElementNoyau;

public abstract class JPanelInfo extends JPanel {
    
    protected ElementNoyau element;
    
    public JPanelInfo(Rectangle rect) {
        this.setLayout( null );
        this.setSize(new Dimension(220, 200));
        this.setBounds(rect);
        this.setBackground(Color.white);
    }

    /**
     * Génère l'affichage du panel et de ses composants (Swing)
     * @throws Exception
     */
    protected abstract void jbInit() throws Exception;

    /**
     * Met à jour l'affichage du panel d'info avec les informations de l'objet donné.
     * @param elementSelectionne Elément selectionné dont les informations doivent être affichés
     */
    public abstract void update();
    
    public void setElement(ElementNoyau e0) {
        element = e0;
        update();
    }

    public ElementNoyau getElement() {
        return element;
    }
}
