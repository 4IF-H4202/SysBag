package noyau;

import bibliothequesTiers.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Chariot extends ElementNoyau {

    /**
     * @param vitesseReelle
     */
    public void setVitesseReelle(Double vitesseReelle) {
        this.vitesseReelle = vitesseReelle;
    }

    /**
     * @return
     */
    public Double getVitesseReelle() {
        return vitesseReelle;
    }

    /**
     * D�finit les type d'actions pouvant �tre r�alis�es par un chariot
     */
    public enum Action {
            deposerBagage("deposerBagage"), retirerBagage("retirerBagage"), 
            garerChariot("garerChariot"), retenirPosition("retenirPosition"),
            aucune("aucune");
            private String action;

            Action(String actionLabel) {
                this.action = actionLabel;
            }

            public String getAction() {
                return this.action;
            }
    }
    
    /**
     * @attribute : action du chariot � effectuer
     */
    protected Action action;

    /**
     * @attribute : position du chariot sur le rail, en m�tres
     */
    private Double positionRail;

    /**
     * @attribute : vitesse th�orique (ou maximale) du chariot "par instant" (en m�tres/seconde)
     */
    private Double vitesse;
    /**
     * @attribute : vitesse r�elle du chariot (en m�tres/seconde), �valu�e apr�s un ralentissement
     */  
    protected Double vitesseReelle;
    /**
     * @attribute : vitesse du tapis (en m�tres/seconde)
     */
    private Double vitesseTapis;
    /**
     * @attribute : indique si le chariot a �t� d�plac� pour le top horloge courant
     */   
    protected Boolean deplace;
    /**
     * @attribute : bagage port� par le chariot
     */    
    Bagage bagagePorte;

    /**
     * @associates <{noyau.CheminChariot}> : trajet du chariot : liste de chemins
     */
    LinkedList<CheminChariot> trajet;
    /**
     * @attribute : chemin sur lequel est le chariot
     */       
    CheminChariot cheminActuel;
    /**
     * @attribute : temps (en secondes) d�j� consomm� au cours du top horloge courant
     */          
    protected Double tempsConsomme;
    /**
     * @attribute : indique si le chariot est arr�t� � une intersection ou non (mode manuel seulement)
     */          
    protected boolean estArrete=false;

    /**
     * Constructeur de la classe
     */
    public Chariot() {
        super();
        this.action = Action.aucune;
        this.positionRail = Double.valueOf(0);
        this.vitesse = Double.valueOf(0);
        this.vitesseReelle = this.vitesse;
        this.vitesseTapis = Double.valueOf(0);
        this.deplace = false;
        bagagePorte = null;
        this.trajet = new LinkedList<CheminChariot>();
        cheminActuel = null;
        tempsConsomme = Double.valueOf(0);
    }

    /**
     * Constructeur de la classe
     * @param positionRail  La position (en m�tres) du chariot sur le rail
     * @param vitesse       La vitesse (en m�tres/seconde) th�orique/maximale du chariot
     * @param vitesseTapis  La vitesse (en m�tres/seconde) du tapis situ� sur le chariot
     * @param trajet        Le trajet � parcourir par le chariot
     */
    public Chariot(Double positionRail, Double vitesse, Double vitesseTapis, LinkedList<CheminChariot> trajet) {
        this();
        this.positionRail = positionRail;
        this.vitesse = vitesse;
        this.vitesseReelle = this.vitesse;
        this.vitesseTapis = vitesseTapis;
        
        if(!trajet.isEmpty()) {
            for(CheminChariot chemin : trajet) {
                this.trajet.addLast(chemin);
            }
            
            cheminActuel = this.trajet.removeFirst();
            cheminActuel.AjouterChariot(this);
        }
    }

    /**
     * Retourne le point correspondant aux coordonn�es absolues du chariot, dans le rep�re r�el
     * @return  Le point correspondant aux coordonn�es absolues (en m�tres) du chariot
     */
    public Point getCoordonneesAbsolues(){
        double deltaX = Double.valueOf(0), deltaY = Double.valueOf(0);
        deltaX = positionRail*Math.cos(cheminActuel.getTheta());//+ Math.sin(cheminActuel.getTheta());
        deltaY = positionRail*Math.sin(cheminActuel.getTheta());//- Math.cos(cheminActuel.getTheta());
        return new Point(cheminActuel.getNoeudDeb().getX()+ deltaX, cheminActuel.getNoeudDeb().getY() + deltaY);        
    }

    /**
     * Modifie l'action � r�aliser par le chariot
     * @param action    La nouvelle action � r�aliser par le chariot
     */
    public void setAction(Chariot.Action action) {
        this.action = action;
    }

    /**
     * R�cup�re l'action � r�aliser par le chariot
     * @return  L'action � r�aliser par le chariot
     */
    public Action getAction() {
        return action;
    }

    /**
     * Modifie la position du chariot sur le rail sur lequel il se trouve
     * @param positionRail  La nouvelle position (en m�tres) du chariot sur le rail
     */
    public void setPositionRail(Double positionRail) {
        this.positionRail = positionRail;
    }

    /**
     * R�cup�re la position du chariot sur un rail
     * @return  La position (en m�tres) du chariot sur le rail
     */
    public Double getPositionRail() {
        return positionRail;
    }

    /**
     * @param vitesse
     */
    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
        this.vitesseReelle = this.vitesse;
    }

    /**
     * @return
     */
    public Double getVitesse() {
        return vitesse;
    }

    /**
     * @param vitesseTapis
     */
    public void setVitesseTapis(Double vitesseTapis) {
        this.vitesseTapis = vitesseTapis;
    }

    /**
     * @return
     */
    public Double getVitesseTapis() {
        return vitesseTapis;
    }

    /**
     * @param deplace
     */
    public void setDeplace(Boolean deplace) {
        this.deplace = deplace;
        
        if(!deplace) {
            tempsConsomme = Double.valueOf(0);
        }
    }

    /**
     * @return
     */
    public Boolean isDeplace() {
        return this.deplace;
    }

    /**
     * @param bagagePorte
     */
    public void setBagagePorte(Bagage bagagePorte) {
        this.bagagePorte = bagagePorte;
    }

    /**
     * @return
     */
    public Bagage getBagagePorte() {
        return bagagePorte;
    }


    /**
     * @param trajet
     */
    public void setTrajet(LinkedList<CheminChariot> trajet) {
        this.trajet = new LinkedList<CheminChariot>();
        if(!trajet.isEmpty()) {
            for(CheminChariot chemin :  trajet) {
                this.trajet.addLast(chemin);
            }
        }
    }

    /**
     * @return
     */
    public LinkedList<CheminChariot> getTrajet() {
        return trajet;
    }

    /**
     * @param cheminActuel
     */
    public void setCheminActuel(CheminChariot cheminActuel) {
        this.cheminActuel = cheminActuel;
        cheminActuel.AjouterChariot(this);
    }

    /**
     * @return
     */
    public CheminChariot getCheminActuel() {
        return cheminActuel;
    }

    /**
     * @param tempsConsomme
     */
    public void setTempsConsomme(Double tempsConsomme) {
        this.tempsConsomme = tempsConsomme;
    }

    /**
     * @return
     */
    public Double getTempsConsomme() {
        return this.tempsConsomme;
    }

    /**
     * @return
     */
    CheminChariot getCheminSuivant() {
        if(!trajet.isEmpty()) {
            return trajet.getFirst();
        } else {
            return null;
        }
    }

    /**
     * @param jetonTemps
     * @return
     */
    Double getProgression(Double jetonTemps) {
        return jetonTemps*vitesse;
    }

    /**
     * @param jetonTemps
     * @return
     */
    Double getProgressionReelle(Double jetonTemps) {
        return jetonTemps*vitesseReelle;
    }

    /**
     * M�thode qui ajoute le chemin au trajet de  l'objet chariot
     * @param chemin
     */
    public void AjouterChemin(CheminChariot chemin) {
        if(chemin != null) {
            trajet.addLast(chemin);
        }
    }

    /**
     * M�thode qui fait passer le chariot sur le chemin pr�c�dent de son trajet
     * @param chemin
     */
    void AllerCheminPrecedent(CheminChariot chemin) {
        if(chemin != null) {
            trajet.addFirst(cheminActuel);
            positionRail = positionRail + chemin.getLongueur();
            cheminActuel = chemin;
        }
    }


    /**
     * M�thode qui fait passer le chariot sur le chemin suivant de son trajet
     */
    void AllerCheminSuivant() {
        if(!trajet.isEmpty()) {
            CheminChariot cheminSuivant = trajet.removeFirst();
            if(cheminSuivant != null) {
                positionRail = positionRail - cheminActuel.getLongueur() ;
                cheminActuel = cheminSuivant;
            }
        }
    }

    /**
     * @param duree
     */
    void Attendre(Double duree) {
        tempsConsomme += duree;
    }

    /**
     * M�thode qui avance la position du chariot, en fonction de la progression
     * @param progression
     * @return
     */
    Double Avancer(Double progression) {
        positionRail += progression;
        double dureeDeplacement = progression*vitesse;
        tempsConsomme += dureeDeplacement;
        
        return dureeDeplacement;
    }

    /**
     * M�thode qui avance la position du chariot, en fonction de la progression et du jetonTemps
     * @param progression
     * @param jetonTemps
     */
    void Avancer(Double progression, Double jetonTemps) {
        positionRail += progression;
        tempsConsomme += jetonTemps;
    }

    /**
     * M�thode qui calcule le trajet du chariot en fonction de l'action qu'il doit ex�cuter
     */
    public void CalculerTrajet() {
        if (action == Action.deposerBagage) { // But : trouver le trajet menant au toboggan.
            ArrayList<LinkedList<CheminChariot> > trajetsPossibles = calculerTrajet(cheminActuel,
                                                                                    bagagePorte.getVol().getId(),
                                                                                    new LinkedList<CheminChariot>());
            if(trajetsPossibles.isEmpty()) {
                Intersection noeudFin = cheminActuel.getNoeudFin();
                ArrayList<CheminChariot> voiesSuiv = noeudFin.getVoiesSuiv();
                if(!voiesSuiv.isEmpty()) {
                    trajet.add(voiesSuiv.get(0));
                }
            } else {
                trajet = evaluerTrajets(trajetsPossibles);
            }
        }
        if (action == Action.garerChariot) { // But : trouver le trajet menant � la voie de garage la plus proche et
                                             // ayant le moins de chariots
            ArrayList<LinkedList<CheminChariot> > trajetsPossibles = calculerTrajetVG(cheminActuel,
                                                                                      new LinkedList<CheminChariot>());
            if(trajetsPossibles.isEmpty()) {
                Intersection noeudFin = cheminActuel.getNoeudFin();
                ArrayList<CheminChariot> voiesSuiv = noeudFin.getVoiesSuiv();
                if(!voiesSuiv.isEmpty()) {
                    trajet.add(voiesSuiv.get(0));
                }
            } else {
                trajet = evaluerTrajetsVG(trajetsPossibles);
            }
        }
        if(action == Action.retirerBagage) {
            ArrayList<LinkedList<CheminChariot> > trajetsPossibles = calculerTrajetTR(cheminActuel,
                                                                                      new LinkedList<CheminChariot>());
            if(trajetsPossibles.isEmpty()) {
                Intersection noeudFin = cheminActuel.getNoeudFin();
                ArrayList<CheminChariot> voiesSuiv = noeudFin.getVoiesSuiv();
                if(!voiesSuiv.isEmpty()) {
                    trajet.add(voiesSuiv.get(0));
                }
            } else {
                trajet = evaluerTrajets(trajetsPossibles);
            }
        }
    }

    /**
     * M�thode qui d�pose le bagage du chariot sur le pointDepot
     * @param dureeDepot
     * @param tempsLimite
     * @param pointDepot
     * @return
     */
    Bagage DeposerBagage(Double[] dureeDepot, Double tempsLimite, Double pointDepot) {
        Bagage bagageDepose = bagagePorte;
        dureeDepot[0] = tempsLimite;
                
        if(bagagePorte !=null) {
            double progression = tempsLimite*vitesseTapis;
            
            if(progression > pointDepot) {
                dureeDepot[0] = pointDepot/vitesseTapis;
            }
            
            bagagePorte.Avancer(progression, pointDepot);
            
            if(bagagePorte.getPosition() >= pointDepot) {
                bagagePorte = null;
            } else {
                bagageDepose = null;
            }
        }
        
        tempsConsomme += dureeDepot[0];
        return bagageDepose;
    }

    /**
     * M�thode qui ajoute le bagage en param�tre au chariot
     * @param bagage
     * @param dureeRetrait
     */
    void RetirerBagage(Bagage bagage, Double dureeRetrait) {
        tempsConsomme += dureeRetrait;
                
        if(bagage != null) {
            this.bagagePorte = bagage;
            bagagePorte.setPosition(Double.valueOf(0));
            bagagePorte.setCheminPorteur(null);
        }
    }

    /**
     * D�termine tous les chemins potentiels vers le toboggan correspondant � l'idVolCherche
     * @param cheminDepart
     * @param idVolCherche
     * @param trajetCourant
     * @return
     */
    private ArrayList<LinkedList<CheminChariot> > calculerTrajet(CheminChariot cheminDepart, Integer idVolCherche,
                                                                 LinkedList<CheminChariot> trajetCourant) {
        ArrayList<LinkedList<CheminChariot> > resultat = new ArrayList<LinkedList<CheminChariot> >();
        
        Intersection interSuiv = cheminDepart.getNoeudFin();
        
        Toboggan tob = (Toboggan)interSuiv.getPointDepot();
        if (tob != null) { // Cette intersection d�bouche sur un toboggan :
            if (tob.getVol().getId() == idVolCherche) { // Si ce toboggan est associ� au vol recherch� :
                // On a atteint le bout du trajet :
                LinkedList<CheminChariot> trajetPossible = new LinkedList<CheminChariot>();
                for(CheminChariot chemin : trajetCourant) {
                    trajetPossible.addLast(chemin);
                }                
                
                resultat.add(trajetPossible);
                // De plus, rien ne sert de parcourir ce chemin plus loin : les chemins obtenus seront alors forc�ment
                // plus long :
                return resultat;
            }
        }
        
        // Sinon, on recherche la suite du chemin pour atteindre le toboggan voulu :
        ArrayList<CheminChariot> cheminsSuivants = interSuiv.getVoiesSuiv(); // Rails possibles � la prochaine
                                                                             // intersection.
        boolean formeBoucle;
        for (CheminChariot cheminSuivant : cheminsSuivants) { // Pour chaque rail possible :
            // On v�rifie si ce chemin ne nous ram�ne pas sur nos pas (traitement des boucles) :
            formeBoucle = false;
            for (CheminChariot cheminParcouru : trajetCourant){
                if ((cheminSuivant.getNoeudFin().getId() == cheminParcouru.getNoeudFin().getId()) ||
                    (cheminSuivant.getNoeudFin().getId() == cheminParcouru.getNoeudDeb().getId())) {
                    // Cette voie formeune boucle avec le trajet d�ja effectu� :
                    formeBoucle = true;
                    break;
                }
            }
            if (!formeBoucle) {
                // On test les chemins vers lequel il am�ne :
                 trajetCourant.addLast(cheminSuivant);
                 
                ArrayList<LinkedList<CheminChariot> > sousResultat;
                sousResultat = calculerTrajet(cheminSuivant, idVolCherche,trajetCourant); // Appel r�cursif.
                
                for (LinkedList<CheminChariot> trajetPossible : sousResultat) { // Si des trajets possibles ont �t�
                                                                                // trouv�s, on les ajoute aux autres :
                    resultat.add(trajetPossible);
                }
                
                // On enl�ve finalement le rail cheminSuivant au trajet afin de tester les autres rails possibles :
                trajetCourant.removeLast();                 
            }
        }
        
        return resultat;
    }
    
    
    /**
     * D�termine tous les chemins potentiels vers les voie de garages de la carte
     * @param cheminDepart
     * @param trajetCourant
     * @return
     */
    private ArrayList<LinkedList<CheminChariot> > calculerTrajetVG(CheminChariot cheminDepart,
                                                                   LinkedList<CheminChariot> trajetCourant) {
        ArrayList<LinkedList<CheminChariot> > resultat = new ArrayList<LinkedList<CheminChariot> >();
        
        Intersection interSuiv = cheminDepart.getNoeudFin();
        
        for(CheminChariot chemin : interSuiv.getVoiesSuiv()){
            if(chemin instanceof VoieGarage){
                // on a trouv� une voie de garage potentielle pour notre chariot
                LinkedList<CheminChariot> trajetPossible = new LinkedList<CheminChariot>(); 
                for(CheminChariot cheminChariot : trajetCourant) {
                    trajetPossible.addLast(cheminChariot);
                }
                trajetPossible.addLast(chemin);
                resultat.add(trajetPossible);
            } // end of if
        } // fin for
        
        if(resultat.size() > 0)
                   return resultat;
        
        // Sinon, on recherche la suite du chemin pour atteindre une voie de garage
        ArrayList<CheminChariot> cheminsSuivants = interSuiv.getVoiesSuiv(); // Rails possibles � la prochaine
                                                                             // intersection.
        boolean formeBoucle;
        for (CheminChariot cheminSuivant : cheminsSuivants) { // Pour chaque rail possible :
            // On v�rifie si ce chemin ne nous ram�ne pas sur nos pas (traitement des boucles) :
            formeBoucle = false;
            for (CheminChariot cheminParcouru : trajetCourant){
                if ((cheminSuivant.getNoeudFin().getId() == cheminParcouru.getNoeudFin().getId()) ||
                    (cheminSuivant.getNoeudFin().getId() == cheminParcouru.getNoeudDeb().getId())) {
                    // Cette voie formeune boucle avec le trajet d�ja effectu� :
                    formeBoucle = true;
                    break;
                } // end of if
            } // end of for
            if (!formeBoucle) {
                // On test les chemins vers lequel il am�ne :
                if(!(cheminSuivant instanceof VoieGarage)) {
                    trajetCourant.addLast(cheminSuivant);
                     
                    ArrayList<LinkedList<CheminChariot> > sousResultat;
                    sousResultat = calculerTrajetVG(cheminSuivant, trajetCourant); // Appel r�cursif.
                    
                    for (LinkedList<CheminChariot> trajetPossible : sousResultat) { // Si des trajets possibles ont �t�
                                                                                    // trouv�s, on les ajoute aux
                                                                                    // autres :
                        resultat.add(trajetPossible);
                    } // end of for
                    
                    // On enl�ve finalement le rail cheminSuivant au trajet afin de tester les autres rails possibles :
                    trajetCourant.removeLast();
                } // end of if
                
            } // end of if
            
        } // end of for
        
       
        return resultat;
    } // end of calculerTrajetVG

    /**
     * D�termine tous les chemins potentiels vers les tapisRoulant de la carte
     * @param cheminDepart
     * @param trajetCourant
     * @return
     */
    private ArrayList<LinkedList<CheminChariot> > calculerTrajetTR(CheminChariot cheminDepart,
                                                                   LinkedList<CheminChariot> trajetCourant) {
        ArrayList<LinkedList<CheminChariot> > resultat = new ArrayList<LinkedList<CheminChariot> >();
        
        Intersection interSuiv = cheminDepart.getNoeudFin();
        
        CheminPorteur cheminPorteur = interSuiv.getPointRetrait();
        if (cheminPorteur != null) { // Cette intersection d�bouche sur un tapis roulant :
            // On a atteint le bout du trajet :
            LinkedList<CheminChariot> trajetPossible = new LinkedList<CheminChariot>();
            for(CheminChariot chemin : trajetCourant) {
                trajetPossible.addLast(chemin);
            }                
            
            resultat.add(trajetPossible);
            // De plus, rien ne sert de parcourir ce chemin plus loin : les chemins obtenus seront alors forc�ment plus
            // long :
            return resultat;
        }
        
        // Sinon, on recherche la suite du chemin pour atteindre un tapis roulant:
        ArrayList<CheminChariot> cheminsSuivants = interSuiv.getVoiesSuiv(); // Rails possibles � la prochaine
                                                                             // intersection.
        boolean formeBoucle;
        for (CheminChariot cheminSuivant : cheminsSuivants) { // Pour chaque rail possible :
            // On v�rifie si ce chemin ne nous ram�ne pas sur nos pas (traitement des boucles) :
            formeBoucle = false;
            for (CheminChariot cheminParcouru : trajetCourant){
                if ((cheminSuivant.getNoeudFin().getId() == cheminParcouru.getNoeudFin().getId()) ||
                    (cheminSuivant.getNoeudFin().getId() == cheminParcouru.getNoeudDeb().getId())) {
                    // Cette voie formeune boucle avec le trajet d�ja effectu� :
                    formeBoucle = true;
                    break;
                }
            }
            if (!formeBoucle) {
                // On test les chemins vers lequel il am�ne :
                 trajetCourant.addLast(cheminSuivant);
                 
                ArrayList<LinkedList<CheminChariot> > sousResultat;
                sousResultat = calculerTrajetTR(cheminSuivant,trajetCourant); // Appel r�cursif.
                
                for (LinkedList<CheminChariot> trajetPossible : sousResultat) { // Si des trajets possibles ont �t�
                                                                                // trouv�s, on les ajoute aux autres :
                    resultat.add(trajetPossible);
                }
                
                // On enl�ve finalement le rail cheminSuivant au trajet afin de tester les autres rails possibles :
                trajetCourant.removeLast();                 
            }
        }
        
        return resultat;
    }

    /**
     * Retourne le trajet le plus court parmi la liste des trajetsPossibles
     * @param trajetsPossibles
     * @return
     */
    private LinkedList<CheminChariot> evaluerTrajets(ArrayList<LinkedList<CheminChariot> > trajetsPossibles) {
        LinkedList<CheminChariot> trajetPlusCourt = new LinkedList<CheminChariot>();
        Double longueurTrajetMin = Double.MAX_VALUE; 
        Double longueurTrajet;
        for (LinkedList<CheminChariot> trajetActu : trajetsPossibles) {
            longueurTrajet = Double.valueOf(0);
            for (CheminChariot chemin : trajetActu) {
                longueurTrajet += chemin.getLongueur();
                if(longueurTrajet >= longueurTrajetMin) {
                    break;
                }
            }
            if (longueurTrajet < longueurTrajetMin) {
                trajetPlusCourt = trajetActu;
                longueurTrajetMin = longueurTrajet;
            }
        }
           
        return trajetPlusCourt;
    }
    
    /**
     * Evalue les diff�rents trajets d'une collection et renvoie le plus court. Si deux trajets ont m�me longueur,
     * celui conduisant � la voie de garage ayant le moins de chariots sera choisi.
     * @param trajetsPossibles
     * @return
     */
    private LinkedList<CheminChariot> evaluerTrajetsVG(ArrayList<LinkedList<CheminChariot> > trajetsPossibles) {
        LinkedList<CheminChariot> trajetPlusCourt = new LinkedList<CheminChariot>();
        Double longueurTrajetMin = Double.MAX_VALUE; 
        Double longueurTrajet;
        int nbChariotsMin = Integer.MAX_VALUE;
        int nbChariotsTrajet;
        
        for (LinkedList<CheminChariot> trajetActu : trajetsPossibles) {
            longueurTrajet = Double.valueOf(0);
            nbChariotsTrajet = 0;
            for (CheminChariot chemin : trajetActu) {
                if(chemin instanceof VoieGarage){
                    // R�cup�re le nombre de chariots de la voie de garage en bout du parcours
                    nbChariotsTrajet = chemin.getNombreChariots();
                } else {
                    longueurTrajet += chemin.getLongueur();
                    if(longueurTrajet > longueurTrajetMin)
                        break;
                } // end of if-else
            }
            if (longueurTrajet < longueurTrajetMin) {
                trajetPlusCourt = trajetActu;
                longueurTrajetMin = longueurTrajet;
                nbChariotsMin = nbChariotsTrajet;
            } // end of if
            
            else if (longueurTrajet == longueurTrajetMin) {
                // les deux trajets se valent en longueur, il faut privil�gier la voie de garage ayant le
                // moins de chariots
                if(nbChariotsTrajet < nbChariotsMin){
                    trajetPlusCourt = trajetActu;
                    nbChariotsMin = nbChariotsTrajet;
                } // end of if
                
            } // end of else-if
        } // end of for
           
        return trajetPlusCourt;
    }
    
    // Partie utile pour la g�n�ration � partir du XML : 
    /**
     * Cr�er l'objet � partir de noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);

        //lecture des attributs ++++++
        String actionLabel = noeudDOMRacine.getAttribute("action");
        if(!actionLabel.isEmpty()) {
            switch(Enum.valueOf(Action.class, actionLabel)) {
                case deposerBagage:
                    this.action = Action.deposerBagage;
                    break;
            
                case retirerBagage:
                    this.action = Action.retirerBagage;
                    break;
            
                case garerChariot:
                    this.action = Action.garerChariot;
                    break;
            
                default:
                    break;
            }
        }
        
        positionRail = Double.valueOf(noeudDOMRacine.getAttribute("positionRail"));
        vitesseTapis = Double.valueOf(noeudDOMRacine.getAttribute("vitesseTapis"));
        vitesse = Double.valueOf(noeudDOMRacine.getAttribute("vitesse"));
        //CheminChariot cheminActuel;
        

        return ElementNoyau.PARSE_OK;
    }


    /**
      * Cr�e le noeud xml pour la persistance
      * @param document
      * @return
      */
     public Element CreerNoeudXML(Document document) {
         Element racine = document.createElement(this.getClass().getName());
         
         Attr idAttribut = document.createAttribute("id");
         racine.setAttributeNode(idAttribut);
         idAttribut.setValue(Integer.toString(id));

         Attr positionRailAttribut = document.createAttribute("positionRail");
         racine.setAttributeNode(positionRailAttribut);
         positionRailAttribut.setValue(Double.toString(positionRail));
     
         Attr vitesseTapisAttribut = document.createAttribute("vitesseTapis");
         racine.setAttributeNode(vitesseTapisAttribut);
         vitesseTapisAttribut.setValue(Double.toString(vitesseTapis));
         
         Attr vitesseAttribut = document.createAttribute("vitesse");
         racine.setAttributeNode(vitesseAttribut);
         vitesseAttribut.setValue(Double.toString(vitesse));
         
         Attr actionAttribut = document.createAttribute("action");
         racine.setAttributeNode(actionAttribut);
         actionAttribut.setValue(this.action.getAction());
         
         Integer idChemin = 0;
         if( cheminActuel!= null){
             idChemin = cheminActuel.getId();
         }
         
         Attr idCheminAttribut = document.createAttribute("idChemin");
         racine.setAttributeNode(idCheminAttribut);
         idCheminAttribut.setValue(Integer.toString(idChemin));
         
         
         if( bagagePorte != null){
                 Element interElement = bagagePorte.CreerNoeudXML(document);
                 if (interElement != null) {
                     racine.appendChild(interElement);
                 } else {
                     return null;
                 }
         }
         
         return racine;
        
     }

    /**
     * @param estArrete
     */
    public void setEstArrete(boolean estArrete) {
        this.estArrete = estArrete;
}

    /**
     * @return
     */
    public boolean getEstArrete() {
        return this.estArrete;
    }

    /**
     * Retourne le chariot de la list en param�tre correspondant � l'id en param�tre
     * @param list
     * @param id
     * @return
     */
    public static Chariot getChariotById(LinkedList<Chariot> list, Integer id) {
        for (Chariot etr : list) {
            if(etr.getId().intValue() == id.intValue()){
                return etr;
            }
        }
        return null;
    }

}
