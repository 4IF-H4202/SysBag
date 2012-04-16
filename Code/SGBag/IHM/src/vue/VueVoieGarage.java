package vue;

import bibliothequesTiers.Point;

import noyau.CheminChariot;
import noyau.VoieGarage;

 /**Classe gérant l'affichage des voies garages.
  */
public class VueVoieGarage extends VueCheminChariot {
    
    /**Constructeur de la classe VueTapisRoulant
     * @param voieGarage Objet VoieGarage géré.
     * @param vueAeroport L'objet vue de l'aéroport.
     */
    public VueVoieGarage(VoieGarage voieGarage, VueAeroport vueAeroport) {
        super(voieGarage, vueAeroport);    
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg+"Selection";
        this.nomImgSurbrillance= this.nomImg;
    }
    
    
}
