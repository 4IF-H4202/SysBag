package vue;

import java.awt.Graphics;
import bibliothequesTiers.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import noyau.Bagage;
import noyau.Etincelle;

/**Classe gérant l'affichage d'étincelle.
 */
public class VueEtincelle {
    /**
     * @attribute Objet Etincelle géré par la vue.
     */
    protected Etincelle etincelle;
    
    /**
     * @attribute Vue qui gère cet objet.
     */
    protected VueAeroport vueAeroport;
    
    /**
     * @attribute Liste d'étincelle à afficher.
     */
    protected List<BufferedImage> listImgEtincelle;
    protected int longueurImgPixel;
    protected int largeurImgPixel;
        
        
    protected Point position;
    protected Double inclinaison;


    /**Constructeur de la classe VueEtincelle.
     * @param etincelle
     * @param vueAeroport
     */
    public VueEtincelle(Etincelle etincelle, VueAeroport vueAeroport) {       
        this.etincelle = etincelle;
        this.vueAeroport = vueAeroport;
        listImgEtincelle = new ArrayList<BufferedImage>();
        longueurImgPixel = 0;
        largeurImgPixel = 0;
    }

    /**Met à jour la position de l'étincelle.
     */
    public void actualiserInfo() {
        position = etincelle.getCoordonneesAbsolues();
        if (inclinaison != etincelle.getRail().getTheta()){
            inclinaison = etincelle.getRail().getTheta();            
        }
    }
    
    /**Dessine sur l'objet Graphics en paramètre l'élément.
     * @param g objet Graphics sur lequel l'élément sera dessiné.
     */
    public void Dessiner(Graphics g) {
        actualiserInfo();
        Point p_pixel = vueAeroport.convertirEnPixel(position);
        p_pixel.setX(p_pixel.getX()-vueAeroport.getX0());
        p_pixel.setY(p_pixel.getY()-vueAeroport.getY0());
        int indice = (int)Math.round(etincelle.getProgression()*(listImgEtincelle.size()-1));        
        if(indice > listImgEtincelle.size() - 1) {
            System.out.println("error");
        }
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.rotate(-1.57075+inclinaison+Math.PI, p_pixel.getX(), p_pixel.getY());
        BufferedImage imageADessiner = listImgEtincelle.get(indice);
        BufferedImage imageADessinerScaled = VueElementsClickable.scale(imageADessiner, vueAeroport.getCoefReducteur()/imageADessiner.getWidth());
        this.longueurImgPixel = imageADessinerScaled.getWidth();
        this.largeurImgPixel = imageADessinerScaled.getHeight();
        g.drawImage(imageADessiner, (int) (p_pixel.getX() - longueurImgPixel/2), (int)( p_pixel.getY() - largeurImgPixel/2)
                    , (int)(longueurImgPixel), (int)(largeurImgPixel), null) ;            
        g2d.setTransform(originalTransform);
    }
}
