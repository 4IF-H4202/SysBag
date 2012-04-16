package noyau;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class VoieGarage extends CheminChariot {
    LinkedList<Chariot> chariotsDebloques;

    /**
     * Constructeur de la classe
     */
    public VoieGarage() {
        super();
        chariotsDebloques = new LinkedList<Chariot>();
    }

    /**
     * Constructeur de la classe
     * @param noeudFin
     * @param noeudDeb
     * @param nbChariots
     */
    public VoieGarage(Intersection noeudFin, Intersection noeudDeb, int nbChariots) {
        super(noeudDeb, noeudFin);
        chariotsDebloques = new LinkedList<Chariot>();
    }
    
    

    /**
     * Fait sortir le dernier chariot de la liste des chariots présents de la voie garage
     * @param parcours
     */
    public void debloquerChariot(LinkedList <CheminChariot> parcours) {
        if(!chariotsPresents.isEmpty()) {
            Chariot chariot = chariotsPresents.removeLast();
            chariot.setTrajet(parcours);
            chariot.setPositionRail(longueur);
            chariotsDebloques.add(chariot);
        }
    }

    /**
     * En mode manuel, permet de sortir un chariot de la voieGarage 
     * et le positionner sur l'intersection de fin de la voieGarage
     */
    public void debloquerChariotManuel(){
        debloquerChariot(new LinkedList<CheminChariot>());
    }

    /**
     * Retourne la voie garage de la list en paramètre correspondant à l'id en paramètre
     * @param list
     * @param id
     * @return
     */
    public static VoieGarage getVoieGarageById(LinkedList<VoieGarage> list, Integer id) {
        for (VoieGarage el : list) {
            if(el.getId().intValue() == id.intValue()){
                return el;
            }
        }
        return null;
    }

    /**
     * Ajoute le chariot en paramètre à la liste des chariots présents
     * @param chariot
     */
    public void AjouterChariot(Chariot chariot) {
        chariotsPresents.add(chariot);
        chariot.setAction(Chariot.Action.garerChariot);
    }
    
    public void AvancerChariots(Double jetonTemps, Double distanceSecurite, Boolean auto) {
        ListIterator<Chariot> itChariot = chariotsDebloques.listIterator();
        while(itChariot.hasNext()) {
            Chariot chariot = itChariot.next();
            Double[] nouvelleVitesse = new Double[1];
            double distanceChariot = noeudFin.GetDistanceProchainChariot(distanceSecurite, distanceSecurite, this, nouvelleVitesse);
            
            if(distanceChariot >= distanceSecurite) {
                if(!auto) {
                    ArreterChariot(chariot);
                }
                if(chariot.getBagagePorte() != null) {
                    chariot.setAction(Chariot.Action.deposerBagage);
                } else {
                    chariot.setAction(Chariot.Action.retirerBagage);
                }
                noeudFin.setChariotPresent(chariot);
                
                itChariot.remove();
            } else {
                break;
            }
        }
    }

    public void AjouterChariotQueue(Chariot chariot) {
        super.AjouterChariotQueue(chariot);
        chariot.setAction(Chariot.Action.garerChariot);
    }
}
