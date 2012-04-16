package noyau;

import bibliothequesTiers.Point;

import java.util.ArrayList;
import java.util.LinkedList;

import java.util.ListIterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Rail extends CheminChariot {

    /**
     * @associates <{noyau.Etincelle}> : liste des étincelles
     */
    protected LinkedList<Etincelle> etincelles;

    /**
     * Constructeur de la classe
     */
    public Rail() {
        super();
        etincelles = new LinkedList<Etincelle>();
    }

    /**
     * Constructeur de la classe
     * @param id
     * @param noeudDeb
     * @param noeudFin
     */
    public Rail(Integer id, Intersection noeudDeb, Intersection noeudFin) {
        super(noeudDeb, noeudFin);
        this.id = id;
        etincelles = new LinkedList<Etincelle>();
    }

    /**
     * Fait avancer la position du chariot en paramètre
     * @param jetonTemps
     * @param distanceSecurite
     * @param chariot
     * @param itChariot
     * @param auto
     */
    public void AvancerChariot(Double jetonTemps, Double distanceSecurite, Chariot chariot, ListIterator<Chariot> itChariot, Boolean auto) {
        super.AvancerChariot(jetonTemps, distanceSecurite, chariot, itChariot, auto);
        
        double positionActuelle = chariot.getPositionRail();
        double progression = chariot.getProgression(jetonTemps);
        double positionSuivante = positionActuelle + progression;
            
        if(jetonTemps > 0) {
            if(positionSuivante < longueur) {
                // Le chariot veut rester sur le rail courant
                AvancerChariot(jetonTemps, chariot, itChariot, progression, distanceSecurite);
                
            } else {
                // Le chariot veut aller sur le rail suivant
                double progressionFinRail = longueur - positionActuelle;
                double dureeDeplacementFinRail = progressionFinRail/chariot.getVitesse();            
                AvancerChariot(jetonTemps, chariot, itChariot, progressionFinRail, distanceSecurite);
                
                jetonTemps -= dureeDeplacementFinRail;
                               
                if(chariot.getPositionRail() >= longueur) {
                    // Le chariot s'est déplacé jusqu'à la fin du rail
                    if(!auto) {
                        ArreterChariot(chariot);
                    }
                    noeudFin.setChariotPresent(chariot);
                    
                    itChariot.remove();
                    noeudFin.AvancerChariot(jetonTemps, distanceSecurite, auto);
                    
                }
            }
        }
    }

    /**
     * Fait avancer la position du chariot en paramètre
     * @param jetonTemps
     * @param chariot
     * @param itChariot
     * @param progression
     * @param distanceSecurite
     */
    public void AvancerChariot(Double jetonTemps, Chariot chariot, ListIterator<Chariot> itChariot, Double progression,
                               Double distanceSecurite) {
        double positionActuelle = chariot.getPositionRail();
        
        // On regarde à quelle distance se situe le chariot suivant le plus proche, pour déterminer jusqu'à quel
        // point le chariot peut s'avancer sur le rail courant
        double progressionMax = progression;
        Double[] nouvelleVitesse = new Double[1];
        nouvelleVitesse[0] = chariot.getVitesseReelle();
        double distanceChariot = GetDistanceProchainChariot(positionActuelle,
                                                            distanceSecurite + progression,
                                                            distanceSecurite, itChariot.previousIndex(),
                                                            nouvelleVitesse);
        
        if((distanceChariot - progression) < distanceSecurite) {
            // Le chariot ne peut pas aller à la position correspondant à sa vitesse max

            progressionMax = distanceChariot - 1.01*distanceSecurite;
            progressionMax = (progressionMax < 0) ? 0 : progressionMax;
                    
            if(nouvelleVitesse[0] < chariot.getVitesseReelle()) {
                Etincelle etincelle = new Etincelle((chariot.getVitesseReelle()-nouvelleVitesse[0]), positionActuelle);
                etincelles.add(etincelle);
                etincelle.setRail(this);
            }
            // Le chariot adapte sa vitesse
            chariot.setVitesseReelle(nouvelleVitesse[0]);
        } else {            
            // Le chariot reprend sa vitesse max
            chariot.setVitesseReelle(chariot.getVitesse());
        }
        
        chariot.Avancer(progressionMax, jetonTemps);
    }

    /**
     * Fait avancer la position des chariots présents sur le rail
     * @param jetonTemps
     * @param distanceSecurite
     * @param auto
     */
    public void AvancerChariots(Double jetonTemps, Double distanceSecurite, Boolean auto) {
        super.AvancerChariots(jetonTemps, distanceSecurite, auto);
        
        ListIterator<Chariot> itChariot = chariotsPresents.listIterator();
        while(itChariot.hasNext()) {
            Chariot chariot = itChariot.next();
            if(!chariot.isDeplace() && (chariot.getAction() != Chariot.Action.retenirPosition)) {
                AvancerChariot(jetonTemps, distanceSecurite, chariot, itChariot, auto);
                chariot.setDeplace(true);
            }
        }
    }

    /**
     * Fait avancer la progression des étincelles du rail
     * @param jetonTemps    La durée (en secondes) pendant laquelle faire avancer les étincelles
     */
    public void AvancerEtincelles(Double jetonTemps) {        
        ListIterator<Etincelle> itEtincelle = etincelles.listIterator();
        while(itEtincelle.hasNext()) {
            Etincelle etincelle = itEtincelle.next();
            etincelle.Avancer(jetonTemps);
            if(etincelle.getProgression() > Double.valueOf(1)) {
                itEtincelle.remove();
            }
        }
    }

    /**
     * Calcule et retourne la distance entre la position en paramètre et celle du prochain chariot
     * @param position          La position (en mètres) sur le rail à partir de laquelle on veut recherche le prochain
     *                          chariot le plus proche
     * @param distanceLimite    La distance (en mètres) que l'on doit parcourir au maximum pour trouver un chariot
     * @param distanceSecurite  La distance de sécurité (en mètres) entre deux chariots
     * @param indice            L'indice du chariot sur le rail à partir duquel on veut effectuer la recherche
     * @param nouvelleVitesse   La vitesse du prochain chariot le plus proche trouvé
     * @return                  La distance (en mètres) du prochain chariot le plus proche trouvé (par rapport à
     *                          <code>position</code>)
     */
    public Double GetDistanceProchainChariot(Double position, Double distanceLimite, Double distanceSecurite,
                                             Integer indice, Double[] nouvelleVitesse) {
        
        ListIterator<Chariot> itChariotSuivant = chariotsPresents.listIterator(indice);
        Chariot prochainChariot = null;
        
        if(itChariotSuivant.hasPrevious()) {
            prochainChariot = itChariotSuivant.previous();
            double distanceChariot = prochainChariot.getPositionRail() - position;
            
            if(distanceChariot < distanceLimite) {
                // Le chariot suivant se situe à l'intérieur de la zone limite
                nouvelleVitesse[0] = prochainChariot.getVitesseReelle();
                return distanceChariot;
            } else {
                // Le chariot suivant est dehors de la zone limite
                return distanceLimite;
            }
        }
        
        if(prochainChariot == null) { // Aucun chariot suivant sur ce rail
            double distanceFin = longueur - position;
            if(distanceFin < distanceLimite) {
                // La zone de recherche n'est pas encore atteinte, il faut analyser l'intersection suivante
                return distanceFin + noeudFin.GetDistanceProchainChariot(distanceLimite - distanceFin, distanceSecurite,
                                                                         this, nouvelleVitesse);
            }
        }
        
        return distanceLimite;
    }

    /**
     * Calcule et retourne la distance entre la position en paramètre et celle du dernier chariot du rail
     * @param position          La position (en mètres) sur le rail à partir de laquelle on veut recherche le dernier 
     *                          chariot le plus proche
     * @param distanceLimite    La distance (en mètres) que l'on doit parcourir au maximum pour trouver un chariot
     * @param indice            L'indice du chariot sur le rail à partir duquel on veut effectuer la recherche
     * @return                  La distance (en mètres) du dernier chariot le plus proche trouvé (par rapport à
     *                          <code>position</code>)
     */
    public Double GetDistanceDernierChariot(Double position, Double distanceLimite, Integer indice) {
        ListIterator<Chariot> itChariotSuivant = chariotsPresents.listIterator(indice);
        Chariot dernierChariot = null;
        
        if(itChariotSuivant.hasNext()) {
            dernierChariot = itChariotSuivant.next();
            double distanceChariot = position - dernierChariot.getPositionRail();
            
            if(distanceChariot < distanceLimite) {
                // Le chariot précédent se situe à l'intérieur de la zone limite
                return distanceChariot;
            } else {
                // Le chariot précédent est dehors de la zone limite
                return distanceLimite;
            }
        }
        
        if(dernierChariot == null) { // Aucun chariot précédent sur ce rail
            if(position < distanceLimite) {
                // La zone de recherche n'est pas encore atteinte, il faut analyser l'intersection précédente
                return position + noeudDeb.GetDistanceDernierChariot(distanceLimite - position, this);
            }
        }
        
        return distanceLimite;
    }

    /**
     * Ajoute le chariot en paramètre à la liste des chariots présents
     * @param chariot
     */
    public void AjouterChariot(Chariot chariot) {
        Chariot chariotSuivant = null;
        Double position = chariot.getPositionRail();
        ListIterator<Chariot> itChariot = chariotsPresents.listIterator();
        
        while(itChariot.hasNext()) {
            chariotSuivant = itChariot.next();
            if(chariotSuivant.getPositionRail() > position) {
                itChariot.previous();
                itChariot.add(chariot);
                break;
            }
        }
        
        if(chariotSuivant == null) {
            itChariot.add(chariot);
        }
    }


    /**
     * Crée l'objet à partir de noeudDOMRacine
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


    /**
     * Retourne le rail de la list en paramètre correspondant à l'id en paramètre
     * @param list
     * @param id
     * @return
     */
    public static Rail getRailById(LinkedList<Rail> list, Integer id) {
        for (Rail el : list) {
            if(el.getId() == id){
                return el;
            }
        }
        return null;
    }


    public String getNom() {
        
        return "R#"+super.getNom();
    }

    /**
     * Ajoute un étincelle au rail
     * @param etincelle     L'étincelle à ajouter
     */
    public void AjouterEtincelle(Etincelle etincelle) {
        etincelles.add(etincelle);
        etincelle.setRail(this);
    }

    /**
     * Récupère l'ensemble des étincelles encore présentes sur le rail
     * @return  La liste des étincelles
     */
    public LinkedList<Etincelle> getEtincelles() {
        return etincelles;
    }
}
