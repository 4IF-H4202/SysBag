package noyau;

import java.util.ArrayList;
import java.util.LinkedList;

import noyau.Chariot.Action;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TapisRoulant extends CheminPorteur {

    /**
     * Constructeur de la classe
     */
    public TapisRoulant() {
        super();
    }

    /**
     * Constructeur de la classe
     * @param pointEntree
     * @param pointSortie
     */
    public TapisRoulant(Intersection pointEntree, Intersection pointSortie) {
        super(pointEntree, pointSortie);
    }

    /**
     * Fait avancer la position des bagages présents
     * @param duree
     */
    public void Avancer(Double duree) {
        super.Avancer(duree);
        avancerBagages(duree, limiteBagages);
        tempsConsomme += duree;
    }

    /**
     * Ajoute le bagage en paramètre à la fin de la liste des bagages portés
     * @param bagage
     */
    public void EntrerBagage(Bagage bagage) {
        super.EntrerBagage(bagage);
        bagage.setPosition(Double.valueOf(0));
        
        if(!bagagesPortes.isEmpty()) {
            Bagage dernierBagage = bagagesPortes.getLast();
            if(dernierBagage.getPosition() < distanceBagages) {
                bagagesAttente.addLast(bagage);
            } else {
                bagagesPortes.addLast(bagage);
                bagage.setCheminPorteur(this);
            }
        } else {
            bagagesPortes.addLast(bagage);
            bagage.setCheminPorteur(this);
        }
    }

    /**
     * Enlève et retourne le premier bagage de la liste des bagages portés
     * @param dureeSortie
     * @param tempsLimite
     * @return
     */
    public Bagage SortirBagage(Double[] dureeSortie, Double tempsLimite) {
        super.SortirBagage(dureeSortie, tempsLimite);
        double distance = avancerBagages(tempsLimite, longueur);
        
        if(distance < tempsLimite*vitesse) {
            dureeSortie[0] = distance/vitesse;
        } else {
            dureeSortie[0] = tempsLimite;
        }
        
        if(!bagagesPortes.isEmpty()) {
            Bagage bagageSortant = bagagesPortes.getFirst();
        
            if(bagageSortant.getPosition() >= longueur) {
                bagagesPortes.removeFirst();
                    return bagageSortant;
            }
        }
            
        return null;
    }

    /**
     * Fait avancer la position des bagages, en fonction du temps limite et de la position limite
     * @param tempsLimite
     * @param positionLimite
     * @return
     */
    private Double avancerBagages(Double tempsLimite, Double positionLimite) {
        double distance = tempsLimite*vitesse;
                
        if(bagagesPortes.isEmpty()) {
            if(!bagagesAttente.isEmpty()) {
                Bagage bagage = bagagesAttente.removeFirst();
                bagagesPortes.addLast(bagage);
                bagage.setCheminPorteur(this);
            }
        }
                
        for(Bagage bagage : bagagesPortes) {
            distance = bagage.Avancer(distance, positionLimite);
        }
        
        double positionBagage = distance;
        
        if(!bagagesPortes.isEmpty()) {
            Bagage dernierBagage = bagagesPortes.getLast();
            double positionDernier = dernierBagage.getPosition();
            
            if((positionDernier - distance) < distanceBagages)
                positionBagage = positionDernier - distanceBagages;
        }
        
        while(positionBagage >= 0) {
            if(bagagesAttente.isEmpty()) {
                break;
            }
            
            Bagage nouveauBagage = bagagesAttente.removeFirst();    
            nouveauBagage.Avancer(positionBagage, positionLimite);
            bagagesPortes.addLast(nouveauBagage);
            nouveauBagage.setCheminPorteur(this);
            
            positionBagage -= distanceBagages;
        }
        
        return distance;
    }
    
    /**
     * Calcule le trajet à l'envers depuis un tapis roulant vers la voie de garage la plus proche
     */
    public LinkedList<CheminChariot> calculerTrajet() {
        ArrayList<LinkedList<CheminChariot> > trajetsPossibles = new ArrayList<LinkedList<CheminChariot> >();
        trajetsPossibles = calculerTrajet(new LinkedList<CheminChariot>());
        
        // LinkedList<CheminChariot> trajet = new LinkedList<CheminChariot>();
        LinkedList<CheminChariot> trajet = evaluerTrajets(trajetsPossibles);
        
        return trajet;
    } // end of 

    /**
     * Méthode qui évalue le meilleur trajet parmis une collection selon sa longueur et qui privilègie la voie de
     * garage ayant le plus de chariots en cas d'égalité
     * @param trajetsPossibles
     * @return
     */
    private LinkedList<CheminChariot> evaluerTrajets(ArrayList<LinkedList<CheminChariot> > trajetsPossibles) {
        LinkedList<CheminChariot> trajetPlusCourt = new LinkedList<CheminChariot>();
        Double longueurTrajetMin = Double.MAX_VALUE; 
        Double longueurTrajet;
        int nbChariotTrajetMin = 0;
        int nbChariotTrajet;
        
        for (LinkedList<CheminChariot> trajetActu : trajetsPossibles) {
            longueurTrajet = Double.valueOf(0);
            nbChariotTrajet = 0;
            for (CheminChariot chemin : trajetActu) {
                if(chemin instanceof VoieGarage){
                    // Récupère le nombre de chariots de la voie de garage en bout du parcours
                    nbChariotTrajet = chemin.getNombreChariots();
                } else {
                    longueurTrajet += chemin.getLongueur();
                    if(longueurTrajet > longueurTrajetMin)
                        break;
                } // end of if-else
               
            } // end of for
            
            if (longueurTrajet < longueurTrajetMin) {
                trajetPlusCourt = trajetActu;
                longueurTrajetMin = longueurTrajet;
                nbChariotTrajetMin = nbChariotTrajet;
            } // end of if
            
            else if (longueurTrajet == longueurTrajetMin) {
                // les deux trajets se valent en longueur, il faut privilégier la voie de garage ayant le
                // plus de chariots
                if(nbChariotTrajet > nbChariotTrajetMin){
                    trajetPlusCourt = trajetActu;
                    nbChariotTrajetMin = nbChariotTrajet;
                } // end of if
                
            } // end of else-if
            
        } // end of for
        
        return trajetPlusCourt;
        
    } // end of evaluerTrajets
   
    /**
     * Méthode qui retourne tous les chemins à l'envers vers les voies de garages depuis le tapis roulant
     * @param trajetCourant
     * @return
     */
    private ArrayList<LinkedList<CheminChariot> > calculerTrajet(LinkedList<CheminChariot> trajetCourant) {

        ArrayList<LinkedList<CheminChariot> > resultat = new ArrayList<LinkedList<CheminChariot> >();
        
        Intersection interPrec = this.getNoeudFin();
        
        for(CheminChariot chemin : interPrec.getVoiesPrec()){
            if(chemin instanceof VoieGarage){
                // on a trouvé une voie de garage potentielle pour notre tapis
                if(chemin.getNombreChariots()>0){
                    // la voie a des chariots en stock
                    
                    LinkedList<CheminChariot> trajetPossible = new LinkedList<CheminChariot>();
                    for(CheminChariot cheminChariot : trajetCourant) {
                        trajetPossible.addLast(cheminChariot);
                    }  
                    trajetPossible.addFirst(chemin);
                    
                    resultat.add(trajetPossible);
                } // end of if
            } // end of if
        } // fin for
        
        if(resultat.size() > 0)
                   return resultat;
        
        // Sinon, on recherche la suite du chemin pour atteindre une voie de garage
        ArrayList<CheminChariot> cheminsSuivants = interPrec.getVoiesPrec(); // Rails possibles à la prochaine intersection (précédente).

        for (CheminChariot cheminSuivant : cheminsSuivants) { // Pour chaque rail possible :
            // On teste les chemins vers lequel il amène :
            
            if(!(cheminSuivant instanceof VoieGarage)) {
                trajetCourant.addFirst(cheminSuivant);
                 
                ArrayList<LinkedList<CheminChariot> > sousResultat;
                sousResultat = calculerTrajetSuivIt(cheminSuivant,trajetCourant); // Appel récursif.
                
                for (LinkedList<CheminChariot> trajetPossible : sousResultat) { // Si des trajets possibles ont été trouvés, on les ajoute aux autres :
                    resultat.add(trajetPossible);
                } // end of for
                
                // On enlève finalement le rail cheminSuivant au trajet afin de tester les autres rails possibles :
                trajetCourant.removeFirst();  
                
            } // end of if
            
        } // end of for
        
        return resultat;
    } // calculerTrajet
    
    
    
    /**
     * Méthode qui retourne tous les chemins à l'envers vers les voies de garages de la carte
     * @param cheminDepart
     * @param trajetCourant
     * @return
     */
    private ArrayList<LinkedList<CheminChariot> > calculerTrajetSuivIt(Chemin cheminDepart, LinkedList<CheminChariot> trajetCourant) {

        ArrayList<LinkedList<CheminChariot> > resultat = new ArrayList<LinkedList<CheminChariot> >();
        
        Intersection interPrec = cheminDepart.getNoeudDeb();
        
        for(CheminChariot chemin : interPrec.getVoiesPrec()){
            if(chemin instanceof VoieGarage){
                // on a trouvé une voie de garage potentielle pour notre tapis
                if(chemin.getNombreChariots()>0){
                    // la voie a des chariots en stock
                    
                    // On récupère la voie de garage dans le trajet
                    LinkedList<CheminChariot> trajetPossible = new LinkedList<CheminChariot>();
                    
                    for(CheminChariot cheminParcours : trajetCourant){
                        trajetPossible.add(cheminParcours);
                    }
                    
                    trajetPossible.addFirst(chemin);
                    
                    resultat.add(trajetPossible);
                } // end of if
                
            } // end of if
            
        } // fin for
        
        if(resultat.size() > 0)
                   return resultat;
        
        // Sinon, on recherche la suite du chemin pour atteindre une voie de garage
        ArrayList<CheminChariot> cheminsSuivants = interPrec.getVoiesPrec(); // Rails possibles à la prochaine intersection (précédente).
        boolean formeBoucle;
        for (CheminChariot cheminSuivant : cheminsSuivants) { // Pour chaque rail possible :
            // On vérifie si ce chemin ne nous ramène pas sur nos pas (traitement des boucles) :
            
            if(!(cheminSuivant instanceof VoieGarage)) {
                formeBoucle = false;
                for (CheminChariot cheminParcouru : trajetCourant){
                    if ((cheminSuivant.getNoeudDeb().getId() == cheminParcouru.getNoeudFin().getId()) || (cheminSuivant.getNoeudDeb().getId() == cheminParcouru.getNoeudDeb().getId())) {
                        // Cette voie formeune boucle avec le trajet déja effectué :
                        formeBoucle = true;
                        break;
                    } // end of if
                    
                } // end of for
                
                if (!formeBoucle) {
                    // On teste les chemins vers lequel il amène :
                    trajetCourant.addFirst(cheminSuivant);
                     
                    ArrayList<LinkedList<CheminChariot> > sousResultat;
                    sousResultat = calculerTrajetSuivIt(cheminSuivant,trajetCourant); // Appel récursif.
                    
                    for (LinkedList<CheminChariot> trajetPossible : sousResultat) { // Si des trajets possibles ont été trouvés, on les ajoute aux autres :
                        resultat.add(trajetPossible);
                    } // end of for
                    
                    // On enlève finalement le rail cheminSuivant au trajet afin de tester les autres rails possibles :
                    trajetCourant.removeFirst();                 
                } // end of if
                
            } // end of if
            
        } // end of for
        
        return resultat;
    } // end of calculerTrajetSuivIt

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

         Attr distanceBagagesAttribut = document.createAttribute("distanceBagages");
         racine.setAttributeNode(distanceBagagesAttribut);
         distanceBagagesAttribut.setValue(Double.toString(distanceBagages));
         
         Attr vitesseAttribut = document.createAttribute("vitesse");
         racine.setAttributeNode(vitesseAttribut);
         vitesseAttribut.setValue(Double.toString(vitesse));
         
         Attr limiteBagagesAttribut = document.createAttribute("limiteBagages");
         racine.setAttributeNode(limiteBagagesAttribut);
         limiteBagagesAttribut.setValue(Double.toString(limiteBagages));
         
         Attr tempsConsommeAttribut = document.createAttribute("tempsConsomme");
         racine.setAttributeNode(tempsConsommeAttribut);
         tempsConsommeAttribut.setValue(Double.toString(tempsConsomme));
         
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
         
         for(Bagage bag : bagagesPortes){
             if( bag != null){
                     Element interElement = bag.CreerNoeudXML(document);
                     if (interElement != null) {
                         String statut = "porte";
                         Attr statutAttribut = document.createAttribute("status");
                         interElement.setAttributeNode(statutAttribut);
                         statutAttribut.setValue(statut);
                         racine.appendChild(interElement);
                     } else {
                         return null;
                     }
             }
         }
         for(Bagage bag : bagagesAttente){
             if( bag != null){
                     Element interElement = bag.CreerNoeudXML(document);
                     if (interElement != null) {
                         String statut = "attente";
                         Attr statutAttribut = document.createAttribute("status");
                         interElement.setAttributeNode(statutAttribut);
                         statutAttribut.setValue(statut);
                         racine.appendChild(interElement);
                     } else {
                         return null;
                     }
             }
         }
         
         return racine;
        
     }

    // TODO :  A REVOIR !!!

    /**
     * Retourne le tapis roulant de la list en paramètre correspondant à l'id en paramètre
     * @param list
     * @param id
     * @return
     */
    public static TapisRoulant getTapisRoulantById(LinkedList<TapisRoulant> list, Integer id) {
        for (TapisRoulant etr : list) {
            if(etr.getId().intValue() == id.intValue()){
                return etr;
            }
        }
        return null;
    }
}
