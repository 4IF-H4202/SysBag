package vue;

import bibliothequesTiers.Point;

import java.awt.AlphaComposite;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import noyau.Chariot;
import noyau.CheminChariot;
import noyau.Intersection;

 /**Classe gérant l'affichage du menu de choix de direction
  */
public class VueMenuChoixDirection extends VueElementPonctuel {

    /**Constructeur de la classe VueMenuChoixDirection
     * @param intersection
     * @param vueAeroport
     * @param chariot
     */
    public VueMenuChoixDirection(Intersection intersection, VueAeroport vueAeroport,Chariot chariot) {
        super(intersection.getCoordonneesAbsolues(), Double.valueOf(0), vueAeroport);
        this.intersection = intersection;
        this.nomImg = this.getClass().getName();
        vueAeroport.ajouterVueMenu(this);
        // création des vueChoixDirection pour les rails
        for (CheminChariot cheminCourant : intersection.getVoiesSuiv()){
            VueChoixDirection newVueChoixDirection = new VueChoixDirection(chariot,
                                                                           this, cheminCourant,
                                                                           vueAeroport,"rail");
            listeVuesChoix.add(newVueChoixDirection);
            vueAeroport.ajouterVueMenu(newVueChoixDirection);
        }
        // création de la vueChoixDirection pour un tapisroulant
        if(intersection.getPointRetrait()!=null && intersection.getPointRetrait().getBagagesPortes().size()!=0 && intersection.getChariotPresent().getBagagePorte() == null){
            VueChoixDirection newVueChoixDirection = new VueChoixDirection(chariot,
                                                                           this,intersection.getPointRetrait(),
                                                                           vueAeroport,"tapisRoulant");  
            listeVuesChoix.add(newVueChoixDirection);
            vueAeroport.ajouterVueMenu(newVueChoixDirection);
        }
        // création de la vueChoixDirection pour un toboggan
        if(intersection.getPointDepot()!=null && intersection.getChariotPresent().getBagagePorte() != null){
            VueChoixDirection newVueChoixDirection = new VueChoixDirection(chariot,
                                                                           this,intersection.getPointDepot(),
                                                                           vueAeroport,"toboggan");
            listeVuesChoix.add(newVueChoixDirection);
            vueAeroport.ajouterVueMenu(newVueChoixDirection);
        }               
        
    }

    /**
     * @associates <{vue.VueChoixDirection}> Liste composé des choix de direction possible pour l'intersection géré.
     */
    LinkedList<VueChoixDirection> listeVuesChoix = new LinkedList<VueChoixDirection>();
    /**
     * @attribute Intersection à traiter. 
    */
    Intersection intersection;
    

    @Override
    public boolean EstClicke(Integer x, Integer y) {
        return false;
    }

    @Override
    public Object getElement() {
        return intersection;
    }

    public LinkedList<VueChoixDirection> getListeVuesChoix() {
        return listeVuesChoix;
    }
    public void Dessiner(Graphics g) {
        actualiserInfo();
        Point p_pixel = vueAeroport.convertirEnPixel(position);
        p_pixel.setX(p_pixel.getX()-vueAeroport.getX0());
        p_pixel.setY(p_pixel.getY()-vueAeroport.getY0());
        String nomImageADessiner = nomImg;     
        Graphics2D g2d = (Graphics2D)g;            
        BufferedImage imageADessiner = vueAeroport.getSkin().getMapImages().get(nomImageADessiner);
        BufferedImage imageADessinerScaled = scale(imageADessiner, 2*vueAeroport.getCoefReducteur()/imageADessiner.getWidth());
        this.longueurImgPixel = imageADessinerScaled.getWidth();
        this.largeurImgPixel = imageADessinerScaled.getHeight();
        g.drawImage(imageADessiner, (int) ((p_pixel.getX()- longueurImgPixel/2)), (int)( (p_pixel.getY()- largeurImgPixel/2))
                    , (int)(longueurImgPixel), (int)(largeurImgPixel), null) ;      
    }
    
}
