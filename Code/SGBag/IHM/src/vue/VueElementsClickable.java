package vue;

import bibliothequesTiers.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**Classe abstraite gérant les éléments clickables de l'interface d'affichage de l'aéroport. Contient les méthodes et attributs génériques des vues.
 */
public abstract class VueElementsClickable {
    /**
     * @attribute Spécifie si l'élément a été sélectionné.
     */
    protected boolean estSelectionnee = false;
    
    /**
     * @attribute Spécifie si l'élément a été sélectionné.
     */
    protected boolean surbrillance = false;

    /**
     * @attribute Objet VueAeroport gérant cet objet.
     */
    protected VueAeroport vueAeroport;
    protected int largeurImgPixel;
    protected int longueurImgPixel;
    protected Double inclinaison = Double.valueOf(0);
    protected String nomImg = null;
    protected String nomImgSelection = null;
    protected String nomImgSurbrillance = null;


    /**
     * Constructeur de l'objet VueElementsClickable
     * Contrat : lienImage, lienImageSelection et lienImageSurbrillance ne sont pas vérifiés au niveau de la cohérence. Si aucune image ne
     * correspond à la situation, ces paramètres doivent être à null.
     * @param vueAeroport
     * @param inclinaison
     */
    public VueElementsClickable(VueAeroport vueAeroport, double inclinaison) {
        this.vueAeroport = vueAeroport;
        this.inclinaison = inclinaison;        
    }    

    public abstract void Dessiner(Graphics g);
    
    public abstract boolean EstClicke(Integer x, Integer y);

    public void preciserSelection(boolean sel) {
        estSelectionnee = sel;
    }
    
    public void setSurbrillance(boolean sel) {
        surbrillance = sel;
    }
    
    public abstract Object getElement();
    
    /** Effectue une homothétie de l'image.
     * 
     * @param bi l'image.
     * @param scaleValue la valeur de l'homothétie.
     * @return une image réduite ou agrandie.
     * 
     */public static BufferedImage scale(BufferedImage bi, double scaleValue) {
            AffineTransform tx = new AffineTransform();
            tx.scale(scaleValue, scaleValue);
            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_BILINEAR);
            BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),
                    (int) (bi.getHeight() * scaleValue),
                    bi.getType());
            return op.filter(bi, biNew);
                    
    } 
}
