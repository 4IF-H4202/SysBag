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
     * @associates <{noyau.Etincelle}> : liste des �tincelles
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
     * Fait avancer la position du chariot en param�tre
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
                    // Le chariot s'est d�plac� jusqu'� la fin du rail
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
     * Fait avancer la position du chariot en param�tre
     * @param jetonTemps
     * @param chariot
     * @param itChariot
     * @param progression
     * @param distanceSecurite
     */
    public void AvancerChariot(Double jetonTemps, Chariot chariot, ListIterator<Chariot> itChariot, Double progression,
                               Double distanceSecurite) {
        double positionActuelle = chariot.getPositionRail();
        
        // On regarde � quelle distance se situe le chariot suivant le plus proche, pour d�terminer jusqu'� quel
        // point le chariot peut s'avancer sur le rail courant
        double progressionMax = progression;
        Double[] nouvelleVitesse = new Double[1];
        nouvelleVitesse[0] = chariot.getVitesseReelle();
        double distanceChariot = GetDistanceProchainChariot(positionActuelle,
                                                            distanceSecurite + progression,
                                                            distanceSecurite, itChariot.previousIndex(),
                                                            nouvelleVitesse);
        
        if((distanceChariot - progression) < distanceSecurite) {
            // Le chariot ne peut pas aller � la position correspondant � sa vitesse max

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
     * Fait avancer la position des chariots pr�sents sur le rail
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
     * Fait avancer la progression des �tincelles du rail
     * @param jetonTemps    La dur�e (en secondes) pendant laquelle faire avancer les �tincelles
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
     * Calcule et retourne la distance entre la position en param�tre et celle du prochain chariot
     * @param position          La position (en m�tres) sur le rail � partir de laquelle on veut recherche le prochain
     *                          chariot le plus proche
     * @param distanceLimite    La distance (en m�tres) que l'on doit parcourir au maximum pour trouver un chariot
     * @param distanceSecurite  La distance de s�curit� (en m�tres) entre deux chariots
     * @param indice            L'indice du chariot sur le rail � partir duquel on veut effectuer la recherche
     * @param nouvelleVitesse   La vitesse du prochain chariot le plus proche trouv�
     * @return                  La distance (en m�tres) du prochain chariot le plus proche trouv� (par rapport �
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
                // Le chariot suivant se situe � l'int�rieur de la zone limite
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
     * Calcule et retourne la distance entre la position en param�tre et celle du dernier chariot du rail
     * @param position          La position (en m�tres) sur le rail � partir de laquelle on veut recherche le dernier 
     *                          chariot le plus proche
     * @param distanceLimite    La distance (en m�tres) que l'on doit parcourir au maximum pour trouver un chariot
     * @param indice            L'indice du chariot sur le rail � partir duquel on veut effectuer la recherche
     * @return                  La distance (en m�tres) du dernier chariot le plus proche trouv� (par rapport �
     *                          <code>position</code>)
     */
    public Double GetDistanceDernierChariot(Double position, Double distanceLimite, Integer indice) {
        ListIterator<Chariot> itChariotSuivant = chariotsPresents.listIterator(indice);
        Chariot dernierChariot = null;
        
        if(itChariotSuivant.hasNext()) {
            dernierChariot = itChariotSuivant.next();
            double distanceChariot = position - dernierChariot.getPositionRail();
            
            if(distanceChariot < distanceLimite) {
                // Le chariot pr�c�dent se situe � l'int�rieur de la zone limite
                return distanceChariot;
            } else {
                // Le chariot pr�c�dent est dehors de la zone limite
                return distanceLimite;
            }
        }
        
        if(dernierChariot == null) { // Aucun chariot pr�c�dent sur ce rail
            if(position < distanceLimite) {
                // La zone de recherche n'est pas encore atteinte, il faut analyser l'intersection pr�c�dente
                return position + noeudDeb.GetDistanceDernierChariot(distanceLimite - position, this);
            }
        }
        
        return distanceLimite;
    }

    /**
     * Ajoute le chariot en param�tre � la liste des chariots pr�sents
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
     * Cr�e l'objet � partir de noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){
        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);
        
        return ElementNoyau.PARSE_OK;
    }
    
    /**
      * Cr�e le noeud xml, pour la persistance
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
     * Retourne le rail de la list en param�tre correspondant � l'id en param�tre
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
     * Ajoute un �tincelle au rail
     * @param etincelle     L'�tincelle � ajouter
     */
    public void AjouterEtincelle(Etincelle etincelle) {
        etincelles.add(etincelle);
        etincelle.setRail(this);
    }

    /**
     * R�cup�re l'ensemble des �tincelles encore pr�sentes sur le rail
     * @return  La liste des �tincelles
     */
    public LinkedList<Etincelle> getEtincelles() {
        return etincelles;
    }
}
