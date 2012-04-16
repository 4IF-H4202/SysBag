package vue;

import bibliothequesTiers.Point;

import java.awt.Graphics;


import noyau.Bagage;
import noyau.Chariot;
import noyau.CheminPorteur;
import noyau.VoieGarage;

/**Classe gérant l'affichage d'un chariot.
 */
public class VueChariot extends VueElementPonctuel {
    private boolean surbrillance = false;
    private Chariot chariot;

    /**Constructeur de VueChariot
     * @param chariot Le chariot géré
     * @param vueAeroport L'objet vue de l'aéroport.
     */
    public VueChariot(Chariot chariot, VueAeroport vueAeroport){
        super(chariot.getCoordonneesAbsolues(),chariot.getCheminActuel().getTheta(), vueAeroport);
        this.chariot = chariot;
        this.position = chariot.getCoordonneesAbsolues();
        this.nomImg = this.getClass().getName();
        this.nomImgSelection = this.nomImg+"Selection";
        this.nomImgSurbrillance= this.nomImg+"Surbrillance";
    }

    /**Retourne le chariot géré.
     * @return
     */
    public Object getElement() {
        return chariot;
    }

    /**
     * Actualise les informations relatives au chariot.
     */
    @Override
    public void actualiserInfo() {
        position = chariot.getCoordonneesAbsolues();
        if (inclinaison != chariot.getCheminActuel().getTheta()){
            inclinaison = chariot.getCheminActuel().getTheta();            
        }
    }


    /**Dessine le chariot sur le graphique donné en paramètre.
     * @param g
     */
    public void Dessiner(Graphics g) {
        if((chariot.getCheminActuel()!=null) && !((chariot.getAction() == Chariot.Action.garerChariot) && (chariot.getCheminActuel() instanceof VoieGarage))){
            super.Dessiner(g);
            Bagage bagage = chariot.getBagagePorte();
            
            if(bagage != null) {
                VueBagage vueBagage;
                if(bagage.getPosition() > 0) {
                    CheminPorteur cheminPorteur = chariot.getCheminActuel().getNoeudFin().getPointDepot();
                    Point positionBagage = bagage.getCoordonneesAbsolues(cheminPorteur);
                    Double inclinaisonBagage = cheminPorteur.getTheta();
                    vueBagage = new VueBagage(bagage, vueAeroport, positionBagage, inclinaisonBagage);
                } else {
                    Double inclinaisonBagage = inclinaison + Math.toRadians(90);
                    vueBagage = new VueBagage(bagage, vueAeroport, position, inclinaisonBagage);
                }
                vueBagage.Dessiner(g);
            }
        }
    }

}
