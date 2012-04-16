package noyau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CheminChariot extends Chemin {

    /**
     * @associates <{noyau.Chariot}> : liste des chariots présents sur le chemin
     */
    LinkedList<Chariot> chariotsPresents;

    /**
     * Constructeur de la classe
     */
    public CheminChariot() {
        super();
        noeudDeb.AjouterVoieSuiv(this);
        noeudFin.AjouterVoiePrec(this);
        chariotsPresents = new LinkedList<Chariot>();
    }

    /**
     * Constructeur de la classe
     * @param noeudDeb
     * @param noeudFin
     */
    public CheminChariot(Intersection noeudDeb, Intersection noeudFin) {
        super(noeudDeb, noeudFin);
        noeudDeb.AjouterVoieSuiv(this);
        noeudFin.AjouterVoiePrec(this);
        chariotsPresents = new LinkedList<Chariot>();
    }
    
    public void AjouterChariot(Chariot chariot) {
    }

    /**
     * Ajoute le chariot en paramètre en fin de la liste des chariotsPrésents
     * @param chariot
     */
    public void AjouterChariotQueue(Chariot chariot) {
        if(chariot != null) {
            chariotsPresents.addLast(chariot);
        }
    }

    /**
     * Ajoute le chemin en paramètre au début de la liste des chariotsPrésents
     * @param chariot
     */
    public void AjouterChariotTete(Chariot chariot) {
        if(chariot != null) {
            chariotsPresents.addFirst(chariot);
        }
    }

    public void AvancerChariot(Double jetonTemps, Double distanceSecurite, Chariot chariot, ListIterator<Chariot> itChariot, Boolean auto) {
    }

    public void AvancerChariots(Double jetonTemps, Double distanceSecurite, Boolean auto) {
    }

    /**
     * Stoppe le chariot en paramètre (attribu estArrete)
     * @param chariot
     */
    public void ArreterChariot(Chariot chariot) {
        ArrayList<CheminChariot> voiesSuiv = noeudFin.getVoiesSuiv();
        if(voiesSuiv.size() == 1) {
            if(noeudFin.getPointRetrait() != null) {
                // Il y a un point de retrait (tapis)
                if(chariot.getBagagePorte() == null) {
                    // Le chariot n'a pas de bagage
                    chariot.setEstArrete(true);
                } else {
                    // Le chariot a déjà un bagage
                    if(noeudFin.getPointDepot() != null) {
                        // Il y a un point de dépôt (toboggan)
                        chariot.setEstArrete(true);
                    } else {
                        // Il n'y a pas de point de dépôt
                        chariot.AjouterChemin(voiesSuiv.get(0));
                    }
                }
            } else {
                // Il n'y a pas de point de retrait
                if(noeudFin.getPointDepot() != null) {
                    // Il y a un point de dépôt
                    if(chariot.getBagagePorte() != null) {
                        // Le chariot a un bagage
                        chariot.setEstArrete(true);
                    } else {
                        // Le chariot n'a pas de bagage
                        chariot.AjouterChemin(voiesSuiv.get(0));
                    }
                } else {
                    // Il n'y a pas de point de dépôt
                    chariot.AjouterChemin(voiesSuiv.get(0));
                }
            }
        } else {
            // Il y a plusieurs chemin
            chariot.setEstArrete(true);
        }
    }

    public Double GetDistanceProchainChariot(Double position, Double distanceLimite, Double distanceSecurite,
                                             Integer indice, Double[] nouvelleVitesse) {
        return distanceLimite;
    }
    
    public Double GetDistanceDernierChariot(Double position, Double distanceLimite, Integer indice) {
        return distanceLimite;
    }

    /**
     * Enlève et retourne le dernier chariot de la liste des chariotsPrésents
     * @return
     */
    public Chariot RetirerChariotQueue() {
        if(!chariotsPresents.isEmpty()) {
            return chariotsPresents.removeLast();
        } else {
            return null;
        }
    }

    /**
     * Enlève et retourne le premier chariot de la liste des chariotsPrésents
     * @return
     */
    public Chariot RetirerChariotTete() {
        if(!chariotsPresents.isEmpty()) {
            return chariotsPresents.removeFirst();
        } else {
            return null;
        }
    }
    
    public int getNombreChariots() {
        return chariotsPresents.size();
    }
    
    public ListIterator<Chariot> getIteratorFin() {
        return chariotsPresents.listIterator(chariotsPresents.size());
    }    
    

    /**
     * Crée l'objet à partir du noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);
        return ElementNoyau.PARSE_OK;
    }
    
    
    
    /**
      * Crée le noeud xml, pour la persistance
      * @param document
      * @return
      */
     public Element CreerNoeudXML(Document document) {
         Element racine = document.createElement(this.getClass().getName());
         
         Attr idAttribut = document.createAttribute("id");
         racine.setAttributeNode(idAttribut);
         idAttribut.setValue(Integer.toString(id));
         
         Integer idDepart = 0;
         if( noeudDeb != null){
            idDepart = noeudDeb.getId();
         }
         
         Attr idDepartAttribut = document.createAttribute("idDepart");
         racine.setAttributeNode(idDepartAttribut);
         idDepartAttribut.setValue(Integer.toString(idDepart));
         
         Integer idArrivee = 0;
         if( noeudFin != null){
            idArrivee = noeudFin.getId();
         }
         
         Attr idArriveeAttribut = document.createAttribute("idArrivee");
         racine.setAttributeNode(idArriveeAttribut);
         idArriveeAttribut.setValue(Integer.toString(idArrivee));
         
         return racine;
        
     }

    private void avancerChariots(Integer position, Integer distanceSecu, Object chariot, Object chariotSuiv) {
    }

    public Chariot GetChariotQueue() {
        return null;
    }

    public void AvancerChariots(Integer jetonsTemps) {
    }

    public String getNom() {
        return this.id.toString();
    }
    
    public Double getTheta(){
        return super.getTheta();
    }

    public void setNoeudFin(Intersection noeudFin) {
        super.setNoeudFin(noeudFin);
        noeudFin.AjouterVoiePrec(this);
    }

    public void setNoeudDeb(Intersection noeudDeb) {
        super.setNoeudDeb(noeudDeb);
        noeudDeb.AjouterVoieSuiv(this);
    }
}
