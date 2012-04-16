package vue;

import bibliothequesTiers.Point;

import java.awt.Graphics;

import noyau.CheminChariot;
import noyau.Etincelle;
import noyau.Rail;

 /**Classe g�rant l'affichage d'un rail.
  */
public class VueRail extends VueCheminChariot {

    /**Constructeur de la classe VueRail
     * @param rail Objet Rail � afficher.
     * @param vueAeroport Vue g�rant l'affichage de cet objet.
     */
    public VueRail(Rail rail, VueAeroport vueAeroport) {
        super(rail, vueAeroport);
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg;
        this.nomImgSurbrillance= this.nomImg;
    }
    
    /**Indique si les coordonn�es en param�tre correspondent � l'�l�ment affich�.
     * @param x Abscisse en pixel.
     * @param y Ordonn�e en pixel.
     * @return true s'il y a correspondance, et false dans le cas contraire.
     */
    @Override
    public boolean EstClicke(Integer x, Integer y) {
        return false;
    }
    
    /**Dessine sur l'objet Graphics en param�tre l'�l�ment.
     * @param g objet Graphics sur lequel l'�l�ment sera dessin�.
     */
    @Override
    public void Dessiner(Graphics g) {
        super.Dessiner(g);
        for(Etincelle etincelle : ((Rail)chemin).getEtincelles()) {
            VueEtincelle vueEtincelle = new VueEtincelleGauche(etincelle, vueAeroport);
            vueEtincelle.Dessiner(g);
            vueEtincelle = new VueEtincelleDroite(etincelle, vueAeroport);
            vueEtincelle.Dessiner(g);
        }
    }
}
