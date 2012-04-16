package vue;

import bibliothequesTiers.Point;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import noyau.Chemin;

/**Classe abstraite gérant l'affichage des chemins
 */
public abstract class VueChemin extends VueElementsClickable {

    /**@attribute Chemin géré par la vue.
     */
    protected Chemin chemin;


    /**Constructeur de la classe VueChemin
     * @param chemin Objet chemin géré.
     * @param inclinaison Angle au format double représentant l'inclinaison du chemin avec l'axe des abscisses.
     * @param vueAeroport L'objet vue de l'aéroport.
     */
    public VueChemin(Chemin chemin, Double inclinaison, VueAeroport vueAeroport){
        super(vueAeroport, inclinaison);
        this.chemin = chemin;
        Point p_pixel1 = vueAeroport.convertirEnPixel(chemin.getNoeudDeb().getCoordonneesAbsolues());
        Point p_pixel2 = vueAeroport.convertirEnPixel(chemin.getNoeudFin().getCoordonneesAbsolues());
    }

    /**Indique si les coordonnées en paramètre correspondent à l'élément affiché.
     * @param x Abscisse en pixel.
     * @param y Ordonnée en pixel.
     * @return true s'il y a correspondance, et false dans le cas contraire.
     */
    @Override
    public boolean EstClicke(Integer x, Integer y) {
        Point p_pixel1 = vueAeroport.convertirEnPixel(this.chemin.getNoeudDeb().getCoordonneesAbsolues());
        p_pixel1.setX(p_pixel1.getX()-vueAeroport.getX0());
        p_pixel1.setY(p_pixel1.getY()-vueAeroport.getY0());
        Point p_pixel2 = vueAeroport.convertirEnPixel(this.chemin.getNoeudFin().getCoordonneesAbsolues());
        p_pixel2.setX(p_pixel2.getX()-vueAeroport.getX0());
        p_pixel2.setY(p_pixel2.getY()-vueAeroport.getY0());
        double x1=p_pixel1.getX();
        double y1=p_pixel1.getY();
        double x2=p_pixel2.getX();
        double y2=p_pixel2.getY();
        double a=(y2-y1)/(x2-x1);
        double b=y1-a*x1;
        if(Math.abs(a*x+b-y)<20){
            if(x1!=x2){ // xi différents
                if(y1!=y2){ // yi différents
                    if (Math.min(x1,x2)<=x && Math.max(x1,x2)>=x){ // x entre les deux
                        if(Math.min(y1,y2)<=y && Math.max(y1,y2)>=y){ // y entre les deux           
                return true;
            }
        }
                } // fin yi différents
                else if (y1==y2){ // yi égaux
                    if(Math.abs(y-y1)<20){ // y pas trop loin des deux yi
                        if(Math.min(x1,x2)<=x && Math.max(x1,x2)>=x){ // x entre les deux
                                return true;
                        }
                    }
                } // fin yi égaux                
            } // fin x différents
            else if(y1!=y2){ // xi égaux, yi différents
                if(Math.abs(x-x1)<20){ // x pas trop loin des deux xi
                    if(Math.min(y1,y2)<=y && Math.max(y1,y2)>=y){ // y entre les deux
                            return true;
                    }
                }
            } // fin xi égaux, yi différents
            else if(y1==y2){ // xi égaux,yi égaux
                if(Math.abs(x-x1)<20){
                    if(Math.abs(y-y1)<20){
                            return true;
                    }
                }            
            } // fin xi égaux, yi égaux   
        }
        return false;
    }

    /**Dessine sur l'objet Graphics en paramètre l'élément.
     * @param g objet Graphics sur lequel l'élément sera dessiné.
     */
    @Override
    public void Dessiner(Graphics g) {
        Point p_pixel1 = vueAeroport.convertirEnPixel(this.chemin.getNoeudDeb().getCoordonneesAbsolues());
        p_pixel1.setX(p_pixel1.getX()-vueAeroport.getX0());
        p_pixel1.setY(p_pixel1.getY()-vueAeroport.getY0());
        Point p_pixel2 = vueAeroport.convertirEnPixel(this.chemin.getNoeudFin().getCoordonneesAbsolues());
        p_pixel2.setX(p_pixel2.getX()-vueAeroport.getX0());
        p_pixel2.setY(p_pixel2.getY()-vueAeroport.getY0());
        String nomImageADessiner = nomImg;
        if (surbrillance)
        {   nomImageADessiner = nomImgSurbrillance;}   
        BufferedImage imgADessiner = vueAeroport.getSkin().getMapImages().get(nomImageADessiner);                
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.rotate(-1.57075+inclinaison, p_pixel1.getX(), p_pixel1.getY());        
        g2d.drawImage(imgADessiner,(int)((p_pixel1.getX()-vueAeroport.getCoefReducteur()/2)), (int)((p_pixel1.getY()))
                      , (int)(vueAeroport.getCoefReducteur()), (int)(vueAeroport.convertirEnPixel(chemin.getLongueur()).intValue()),null);
        if (estSelectionnee)    // Si l'élément est sélectionné, on affiche l'image de sélection par dessus l'image de base.
        {   
            nomImageADessiner = nomImgSelection; 
            imgADessiner = vueAeroport.getSkin().getMapImages().get(nomImageADessiner);                
            g2d.drawImage(imgADessiner,(int)((p_pixel1.getX()-vueAeroport.getCoefReducteur()/2)), (int)((p_pixel1.getY()))
                          , (int)(vueAeroport.getCoefReducteur()), (int)(vueAeroport.convertirEnPixel(chemin.getLongueur()).intValue()),null);
        }
            g2d.setTransform(originalTransform);
    }

    @Override
        public Object getElement() {
        return chemin;
    }
}
