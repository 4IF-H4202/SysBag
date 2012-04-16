package vue;

import bibliothequesTiers.Point;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**Classe abstraite gérant l'affichage des éléments ponctuels (à l'opposé des chemins).
 */
public abstract class VueElementPonctuel extends VueElementsClickable {
    /**
     * @attribute Coordonnées du centre de l'élément en mètres.
     */
    protected Point position;

    /**Constructeur de la classe VueElementPonctuel
     * @param position Coordonnées du centre de l'élément en mètres.
     * @param inclinaison Angle au format double représentant l'inclinaison du chemin avec l'axe des abscisses.
     * @param vueAeroport L'objet vue de l'aéroport.
     */
    public VueElementPonctuel(Point position, Double inclinaison, VueAeroport vueAeroport) {
        super(vueAeroport, inclinaison);
        this.position = position;
    }

    /**Indique si les coordonnées en paramètre correspondent à l'élément affiché.
     * @param x Abscisse en pixel.
     * @param y Ordonnée en pixel.
     * @return true s'il y a correspondance, et false dans le cas contraire.
     */
    @Override
    public boolean EstClicke(Integer x, Integer y) {
    Point p_pixel = vueAeroport.convertirEnPixel(position);  
    p_pixel.setX(p_pixel.getX()-vueAeroport.getX0());
    p_pixel.setY(p_pixel.getY()-vueAeroport.getY0());
    double dimensionMax = (longueurImgPixel <= largeurImgPixel)? largeurImgPixel : longueurImgPixel;
    return (Math.pow(p_pixel.getX()-x,2)+Math.pow(p_pixel.getY()-y,2) <= Math.pow(dimensionMax/2,2) ); // TODO : modiifer calcul rayon
    }

    public void actualiserInfo(){        
    }

    /**Dessine sur l'objet Graphics en paramètre l'élément.
     * @param g objet Graphics sur lequel l'élément sera dessiné.
     */
    @Override
    public void Dessiner(Graphics g) {
        actualiserInfo();
        Point p_pixel = vueAeroport.convertirEnPixel(position);
        p_pixel.setX(p_pixel.getX()-vueAeroport.getX0());
        p_pixel.setY(p_pixel.getY()-vueAeroport.getY0());
        String nomImageADessiner = nomImg;
        if (surbrillance){
            nomImageADessiner = nomImgSurbrillance;
        }     
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();        
        g2d.rotate(-1.57075+inclinaison, p_pixel.getX(), p_pixel.getY());
        
        BufferedImage imageADessiner = vueAeroport.getSkin().getMapImages().get(nomImageADessiner);
        BufferedImage imageADessinerScaled = scale(imageADessiner, vueAeroport.getCoefReducteur()/imageADessiner.getWidth());
        this.longueurImgPixel = imageADessinerScaled.getWidth();
        this.largeurImgPixel = imageADessinerScaled.getHeight();
        g.drawImage(imageADessiner, (int) ((p_pixel.getX()- longueurImgPixel/2)), (int)( (p_pixel.getY()- largeurImgPixel/2))
                    , (int)(longueurImgPixel), (int)(largeurImgPixel), null) ;
        if (estSelectionnee){
            nomImageADessiner = nomImgSelection;
            imageADessiner = vueAeroport.getSkin().getMapImages().get(nomImageADessiner);
            imageADessinerScaled = scale(imageADessiner, vueAeroport.getCoefReducteur()/imageADessiner.getWidth());
            this.longueurImgPixel = imageADessinerScaled.getWidth();
            this.largeurImgPixel = imageADessinerScaled.getHeight();
            g.drawImage(imageADessiner, (int) ((p_pixel.getX()- longueurImgPixel/2)), (int)( (p_pixel.getY()- largeurImgPixel/2))
                        , (int)(longueurImgPixel), (int)(largeurImgPixel), null) ;            
        }
        g2d.setTransform(originalTransform);
    }    
}
