package vue;

import java.awt.Graphics;
import bibliothequesTiers.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

import noyau.Bagage;

/** Classe gérant l'affichage d'un bagage.
 */
public class VueBagage extends VueElementPonctuel {
    Bagage bagage;
    VueAeroport vueAeroport;
    
    /**
     * Constructeur
     * Il n'y a pas de gestion d'erreur dans le chargement de l'image, une vérification doit avoir été effectué au préalable
     * @param bagage
     * @param vueAeroport
     */
    // Contrat du constructeur : pas de gestion d'erreur dans le chargement de l'image.
    // Une vérification doit avoir été effectué au préalable.
    public VueBagage(Bagage bagage, VueAeroport vueAeroport){       
        super(bagage.getCoordonneesAbsolues(), bagage.getCheminPorteur().getTheta(), vueAeroport);

        this.bagage = bagage;
        this.vueAeroport = vueAeroport;
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg;
        this.nomImgSurbrillance= this.nomImg;
    }
    
    public VueBagage(Bagage bagage, VueAeroport vueAeroport, Point position, Double inclinaison){       
        super(position, inclinaison, vueAeroport);
        this.bagage = bagage;
        this.vueAeroport = vueAeroport;
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg;
        this.nomImgSurbrillance= this.nomImg;
    }

    /**
     * Getter pour obtenir le bagage.
     * @return
     */
    @Override
    public Object getElement() {
        return bagage;
    }

    /**
     * Actualise les informations relatives au bagage.
     */
    @Override
    public void actualiserInfo() {
        if(bagage.getCheminPorteur() != null) {
            position = bagage.getCoordonneesAbsolues();
            if (inclinaison != bagage.getCheminPorteur().getTheta()){
                inclinaison = bagage.getCheminPorteur().getTheta();            
            }
        }  
    }
}
