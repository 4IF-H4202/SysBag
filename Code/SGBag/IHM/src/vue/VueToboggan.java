package vue;

import bibliothequesTiers.Point;

import java.awt.Graphics;

import noyau.Bagage;
import noyau.CheminPorteur;
import noyau.Toboggan;

 /**Classe gérant l'affichage des tapis roulants
  */
public class VueToboggan extends VueCheminPorteur {
    /**Constructeur de la classe VueTapisRoulant
     * @param chemin Objet Toboggan géré.
     * @param vueAeroport L'objet vue de l'aéroport.
     */
    public VueToboggan(Toboggan chemin, VueAeroport vueAeroport) {
        super(chemin, vueAeroport);
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg;
        this.nomImgSurbrillance= this.nomImg;
    }
    
    /**Dessine sur l'objet Graphics en paramètre l'élément.
     * @param g objet Graphics sur lequel l'élément sera dessiné.
     */
    public void Dessiner(Graphics g) {
        super.Dessiner(g);
        
        for(Bagage bagage : ((CheminPorteur)chemin).getBagagesPortes()) {
            VueBagage vueBagage = new VueBagage(bagage, vueAeroport);
            //vueBagage.actualiserInfo();
            vueBagage.Dessiner(g);
        }
    }
}
