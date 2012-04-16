package vue;

import bibliothequesTiers.Point;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Stroke;

import java.awt.image.BufferedImage;

import noyau.Chariot;
import noyau.Intersection;
import noyau.TapisRoulant;
import noyau.Toboggan;

/**Classe gérant l'affichage de l'objet Intersection.
 */
public class VueIntersection extends VueElementPonctuel {
    Intersection intersection;

    /**Constructeur de la classe VueIntersection
     * @param intersection  Intersection à afficher.
     * @param vueAeroport
     */
    public VueIntersection(Intersection intersection, VueAeroport vueAeroport){
        super(intersection.getCoordonneesAbsolues(), Double.valueOf(0), vueAeroport);
        this.intersection = intersection;
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg+"Selection";
        this.nomImgSurbrillance = nomImg;
        
        // Si l'intersection correspond à un début de tapis roulant
        if (intersection.getPointDepot() != null && (intersection.getPointDepot() instanceof TapisRoulant)){
            nomImg += "TapisRoulant";
            nomImgSelection = this.getClass().getName()+ "CheminPorteurSelection";
            nomImgSurbrillance += "TapisRoulantSurbrillance";
            this.inclinaison = Math.toRadians(180)+this.intersection.getPointDepot().getTheta();
        }
        // Si l'intersection correspond à une fin de toboggan
        else if (intersection.getPointRetrait() != null && (intersection.getPointRetrait() instanceof Toboggan)){
            nomImg += "FinToboggan";       
            nomImgSelection = this.getClass().getName()+ "CheminPorteurSelection";
            nomImgSurbrillance = nomImg;
            this.inclinaison = this.intersection.getPointRetrait().getTheta();
        }
    }
    
    /**Indique si les coordonnées en paramètre correspondent à l'élément affiché.
     * @param x Abscisse en pixel.
     * @param y Ordonnée en pixel.
     * @return true s'il y a correspondance, et false dans le cas contraire.
     */
    @Override
    public boolean EstClicke(Integer x, Integer y) {
        if (intersection.getVoiesPrec().size() == 0) {
            return super.EstClicke(x, y);
        }
        else {
            return false;
        }
    }
    
    
    public Object getElement(){
        return intersection;
    }    

    @Override
    public void actualiserInfo() {
        position = intersection.getCoordonneesAbsolues();
    }
}
