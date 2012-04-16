package noyau;

import bibliothequesTiers.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import noyau.Chariot.Action;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Intersection extends ElementNoyau {
    /**
     * @attribute : coordonnées de l'intersection 
     */
    private Point point;
    /**
     * @attribute : 
     */    
    protected Boolean deplace;
    /**
     * @attribute : chariot porté par l'intersection
     */       
    protected Chariot chariotPresent;
    /**
     * @associates <{noyau.Chemin}> : liste des chemins suivants
     */
    ArrayList<CheminChariot> voiesSuiv;
    /**
     * @associates <{noyau.Chemin}> : liste des chemins précédents
     */
    ArrayList<CheminChariot> voiesPrec;
    /**
     * @attribute : chemin de retrait des bagages
     */    
    CheminPorteur pointRetrait;    
    /**
     * @attribute : chemin de dépot des bagages
     */    
    CheminPorteur pointDepot;
    /**
     * @attribute : cheminp orteur de l'intersection
     */    
    CheminPorteur cheminPorteur;

    /**
     * Constructeur de la classe
     */
    public Intersection() {
        super();
        this.voiesPrec = new ArrayList<CheminChariot>();
        this.voiesSuiv = new ArrayList<CheminChariot>();
        this.point = new Point(Double.valueOf(0), Double.valueOf(0));
        this.pointDepot = null;
        this.pointRetrait = null;
    }

    /**
     * Constructeur de la classe
     * @param x
     * @param y
     */
    public Intersection(Double x, Double y) {
        this();
        this.point = new Point(x,y);
    }

    public void setY(Double y) {
        this.point.setLocation(point.getX(),y);
    }

    public Double getY() {
        return point.getY();
    }

    public void setX(Double x) {
        this.point.setLocation(x,point.getY());
    }

    public Double getX() {
        return point.getX();
    }

    public void setVoiesSuiv(ArrayList<CheminChariot> voiesSuiv) {
        for(CheminChariot chemin : voiesSuiv) {
            this.voiesSuiv.add(chemin);
        }
    }

    public ArrayList<CheminChariot> getVoiesSuiv() {
        return voiesSuiv;
    }

    public void setVoiesPrec(ArrayList<CheminChariot> voiesPrec) {
        for(CheminChariot chemin : voiesPrec) {
            this.voiesPrec.add(chemin);
        }
    }

    public ArrayList<CheminChariot> getVoiesPrec() {
        return voiesPrec;
    }

    public void setPointRetrait(CheminPorteur pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    public CheminPorteur getPointRetrait() {
        return this.pointRetrait;
    }
    
    public void setPointDepot(CheminPorteur pointDepot) {
        this.pointDepot = pointDepot;
    }

    public CheminPorteur getPointDepot() {
        return this.pointDepot;
    }

    /**
     * Arrête le chariot présent sur l'intersection
     */
    public void ArreterChariot() {
        if(voiesSuiv.size() == 1) {
            if(pointRetrait != null) {
                // Il y a un point de retrait (tapis)
                if(chariotPresent.getBagagePorte() == null) {
                    // Le chariot n'a pas de bagage
                    chariotPresent.setEstArrete(true);
                } else {
                    // Le chariot a déjà un bagage
                    if(pointDepot != null) {
                        // Il y a un point de dépôt (toboggan)
                        chariotPresent.setEstArrete(true);
                    } else {
                        // Il n'y a pas de point de dépôt
                        chariotPresent.AjouterChemin(voiesSuiv.get(0));
                    }
                }
            } else {
                // Il n'y a pas de point de retrait
                if(pointDepot != null) {
                    // Il y a un point de dépôt
                    if(chariotPresent.getBagagePorte() != null) {
                        // Le chariot a un bagage
                        chariotPresent.setEstArrete(true);
                    } else {
                        // Le chariot n'a pas de bagage
                        chariotPresent.AjouterChemin(voiesSuiv.get(0));
                    }
                } else {
                    // Il n'y a pas de point de dépôt
                    chariotPresent.AjouterChemin(voiesSuiv.get(0));
                }
            }
        } else {
            // Il y a plusieurs chemin
            chariotPresent.setEstArrete(true);
        }
    }

    /**
     * Fait avancer le chariot présent sur l'intersection vers le chemin suivant de son parcours, ou lui fait
     * déposer/retirer un bagage selon son type d'action
     * @param jetonTemps        Temps horloge restant (en secondes)
     * @param distanceSecurite  La distance de sécurité entre deux chariots (ne mètres)
     * @param auto              Désignation du mode de la simulation : <code>true</code>=auto, <code>false</code>=manu
     */
    public void AvancerChariot(Double jetonTemps, Double distanceSecurite, Boolean auto) {        
        if(chariotPresent != null && !chariotPresent.isDeplace()) {
            // Un chariot est en transition sur l'intersection
            
            if(auto && chariotPresent.getEstArrete()) {
                chariotPresent.setEstArrete(false);
            }
            if(!chariotPresent.getEstArrete()) {
                // Le chariot souhaite poursuivre sa course
            
                CheminChariot cheminSuivant = chariotPresent.getCheminSuivant();
                
                if(cheminSuivant != null) {
                    // Le chariot se rend sur le rail suivant
                    chariotPresent.AllerCheminSuivant();
                    cheminSuivant.AjouterChariotQueue(chariotPresent);
                    cheminSuivant.AvancerChariot(jetonTemps, distanceSecurite, chariotPresent,
                                                 cheminSuivant.getIteratorFin(), auto);
                    
                    chariotPresent = null;  
                } else {
                    // Le chariot est arrivé à destination
                    if(jetonTemps > 0) {                   
                        switch(chariotPresent.getAction())
                        {                    
                            case retirerBagage:
                                // Le chariot essaye de retirer un bagage
                                if(chariotPresent.getBagagePorte() == null) {
                                    jetonTemps = retirerBagage(jetonTemps);
                                }
                            
                                // Un bagage a pu être retiré : son prochain objectif est de déposer le bagage
                                if(chariotPresent.getBagagePorte() != null) {
                                    chariotPresent.setAction(Action.deposerBagage);
                                    if(!auto) {
                                        // En mode manu : arrêt du chariot si plusieurs possibilités
                                        ArreterChariot(); 
                                    }
                                }
                        
                                break;
                            
                            case deposerBagage:
                                // Le chariot essaye de déposer son bagage
                                if(chariotPresent.getBagagePorte() != null) {
                                    jetonTemps = deposerBagage(jetonTemps, auto);
                                }
                            
                                // Le bagage a pu être déposé : son prochain objectif est de se garer
                                if(chariotPresent.getBagagePorte() == null) {
                                    chariotPresent.setAction(Action.garerChariot);
                                    if(!auto) {
                                        // En mode manu : arrêt du chariot si plusieurs possibilités
                                        ArreterChariot();
                                    }
                                }
                                break;
                                    
                            case garerChariot:
                                if(!auto) {
                                    // En mode manu : arrêt du chariot si plusieurs possibilités
                                    ArreterChariot();
                                }
                                break;
                                    
                            case aucune:
                                // Le chariot est égaré : son prochain objectif est de se garer
                                chariotPresent.setAction(Action.garerChariot);
                                if(!auto) {
                                    // En mode manu : arrêt du chariot si plusieurs possibilités
                                    ArreterChariot();
                                }
                                break;
                                    
                            default:
                                break;
                        }
                        
                        // Calcul du nouveau trajet du chariot (mode auto seulement)
                        if(auto) {
                            chariotPresent.CalculerTrajet(); 
                        }
                                  
                        cheminSuivant = chariotPresent.getCheminSuivant();
                        
                        if(cheminSuivant != null) {
                            // Le chariot avance sur le rail suivant
                            chariotPresent.AllerCheminSuivant();
                            cheminSuivant.AjouterChariotQueue(chariotPresent);
                            cheminSuivant.AvancerChariot(jetonTemps, distanceSecurite, chariotPresent,
                                                         cheminSuivant.getIteratorFin(), auto);
                            
                            chariotPresent = null;
                        } else {
                            // Le chariot s'arrête à l'intersection
                            if(chariotPresent.getVitesseReelle() > Double.valueOf(0)) {
                                Etincelle etincelle =  new Etincelle(chariotPresent.getVitesseReelle(), chariotPresent.getPositionRail());
                                CheminChariot cheminActuel = chariotPresent.getCheminActuel();
                                if(cheminActuel != null && cheminActuel instanceof Rail) {
                                    ((Rail)cheminActuel).AjouterEtincelle(etincelle);
                                }
    
                                chariotPresent.setVitesseReelle(Double.valueOf(0));
                            }
                            
                            // Le chariot attends pendant le temps horloge restant
                            chariotPresent.Attendre(jetonTemps);
                        }
                    }
                }
            } else {
                // Le chariot s'arrête à l'intersection
                if(chariotPresent.getVitesseReelle() > Double.valueOf(0)) {
                    Etincelle etincelle =  new Etincelle(chariotPresent.getVitesseReelle(), chariotPresent.getPositionRail());
                    CheminChariot cheminActuel = chariotPresent.getCheminActuel();
                    if(cheminActuel != null && cheminActuel instanceof Rail) {
                        ((Rail)cheminActuel).AjouterEtincelle(etincelle);
                    }
    
                    chariotPresent.setVitesseReelle(Double.valueOf(0));
                }                            
                // Le chariot attends pendant le temps horloge restant
                chariotPresent.Attendre(jetonTemps);
            }
        }
    }
    
    public void AjouterVoieSuiv(CheminChariot cheminSuiv) {
        voiesSuiv.add(cheminSuiv);
    }
    
    public void AjouterVoiePrec(CheminChariot cheminPrec) {
        voiesPrec.add(cheminPrec);
    }

    /**
     * Calcule et retourne la distance entre le chariot présent et le prochain chariot sur le chemin passé en paramètre
     * @param distanceLimite
     * @param distanceSecurite
     * @param chemin
     * @param nouvelleVitesse
     * @return
     */
    public Double GetDistanceProchainChariot(Double distanceLimite, Double distanceSecurite, CheminChariot chemin,
                                             Double[] nouvelleVitesse) {
        double distanceChariot = distanceSecurite;
        
        if(chariotPresent != null) {
            nouvelleVitesse[0] = chariotPresent.getVitesseReelle();
            return Double.valueOf(0);
        }
        
        for(CheminChariot cheminPrecedent : voiesPrec) {
            if(cheminPrecedent != chemin && !(cheminPrecedent instanceof VoieGarage)) {
                distanceChariot = cheminPrecedent.GetDistanceDernierChariot(cheminPrecedent.getLongueur(),
                                                                            distanceChariot, 0);
            }
        }
        
        if(distanceChariot < distanceSecurite) {
            nouvelleVitesse[0] = Double.valueOf(0);
            return Double.valueOf(0);
        }
        
        distanceChariot = distanceLimite;
        for(CheminChariot cheminSuivant : voiesSuiv) {
            if(!(cheminSuivant instanceof VoieGarage)) {
                distanceChariot = cheminSuivant.GetDistanceProchainChariot(Double.valueOf(0), distanceChariot,
                                                                       distanceSecurite,
                                                                       cheminSuivant.getNombreChariots(),
                                                                       nouvelleVitesse);
            }
        }
                
        return distanceChariot;
    }


    /**
     * Calcule et retourne la distance entre le chariot présent et le dernier chariot sur le chemin en paramètre
     * @param distanceLimite
     * @param chemin
     * @return
     */
    public Double GetDistanceDernierChariot(Double distanceLimite, CheminChariot chemin) {
        double distanceChariot = distanceLimite;
        
        if(chariotPresent != null) {
            return Double.valueOf(0);
        }
        
        for(CheminChariot cheminPrecedent : voiesPrec) {
            if(cheminPrecedent != chemin && !(cheminPrecedent instanceof VoieGarage)) {
                distanceChariot = cheminPrecedent.GetDistanceDernierChariot(cheminPrecedent.getLongueur(),
                                                                            distanceChariot, 0);
            }
        }
                
        if(distanceChariot < distanceLimite)
            distanceChariot = 0;
        
        return distanceChariot;
    }
    
    
    // Partie utile pour la g?n?ration ? partir du XML : 
    /**
     * Crée l'objet à partir de 
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        //TODO : gerer les erreurs
        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);
        //lecture des attributs
        point = new Point(Double.valueOf(noeudDOMRacine.getAttribute("x")), Double.valueOf(noeudDOMRacine.getAttribute("y")));
        
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

         Attr xAttribut = document.createAttribute("x");
         racine.setAttributeNode(xAttribut);
         xAttribut.setValue(Double.toString(point.getX()));
         
         Attr yAttribut = document.createAttribute("y");
         racine.setAttributeNode(yAttribut);
         yAttribut.setValue(Double.toString(point.getY()));
         
         return racine;
        
     }


    /**
     * Retourne l'intersection de la list en paramètre correspondant à l'id en paramètre
     * @param list
     * @param id
     * @return
     */
    public static Intersection getIntersectionById(LinkedList<Intersection> list, Integer id) {
        for (Intersection el : list) {
            if(el.getId().intValue() == id.intValue()){
                return el;
            }
        }
        return null;
    }

    public void setDeplace(Boolean deplace) {
        this.deplace = deplace;
    }

    public Boolean isDeplace() {
        return deplace;
    }

    public Point getCoordonneesAbsolues() {
        return point;
    }

    public void setChariotPresent(Chariot chariotPresent) {
        this.chariotPresent = chariotPresent;
    }

    public Chariot getChariotPresent() {
        return chariotPresent;
    }
    
    private void avancerChariot(Double jetonTemps, Double distanceSecurite, Chariot chariotFantome) {                               
        
    }

    /**
     * Pose le bagage du chariot présent sur le toboggan à proximité
     * @param jetonTemps
     * @return
     */
    private Double deposerBagage(Double jetonTemps, boolean auto) {
        // Le chariot est arrivé à son point de destination pour déposer un bagage                   
        Bagage bagagePorte = chariotPresent.getBagagePorte();
        if(pointDepot != null && bagagePorte != null && pointDepot instanceof Toboggan &&
           (!auto || ((Toboggan)pointDepot).getVol() == bagagePorte.getVol()) ) {
            
            // Le chariot dépose le bagage : il fait rouler son tapis jusqu'à ce que le bagage ait atteint le début du
            // toboggan
            Double dureeDepot[] = new Double[1];
            Bagage bagage = chariotPresent.DeposerBagage(dureeDepot, jetonTemps, pointDepot.getLimiteBagages());
            
            // Le toboggan fait avancer ses bagages pendant que le chariot avance jusqu'au point de dépôt puis y dépose
            // son bagage
            pointDepot.AvancerBagages(chariotPresent.getTempsConsomme());
            pointDepot.EntrerBagage(bagage);
    
            return jetonTemps - dureeDepot[0];
        } else {
            return jetonTemps;
        }
    }

    /**
     * Récupère le bagage du tapisroulant à proximité et l'ajoute au chariot présent
     * @param jetonTemps
     * @return
     */
    private Double retirerBagage(Double jetonTemps) {
        // Le chariot est arrivé à son point de destination pour récupérer un bagage      
        
        if(pointRetrait != null) {            
            // On fait rouler le tapis pendant que le chariot avance jusqu'au point de retrait
            pointRetrait.AvancerBagages(chariotPresent.getTempsConsomme());
            
            // On fait rouler le tapis (au maximum pendant le temps horloge restant) jusqu'à ce qu'un bagage ait pu être
            // déposé dans le chariot
            Double[] dureeRetrait = new Double[1];
            Bagage bagage = pointRetrait.SortirBagage(dureeRetrait, jetonTemps);
    
            // Le chariot récupère le bagage : il attend que le bagage lui soit déposé
            chariotPresent.RetirerBagage(bagage, dureeRetrait[0]);
            return jetonTemps - dureeRetrait[0];
        } else {
            return jetonTemps;
        }
    }
}
