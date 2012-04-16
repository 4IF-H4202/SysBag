package noyau;

import java.util.LinkedList;
import java.util.ListIterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Toboggan extends CheminPorteur {

    /**
     * @atrribute : vol associé au toboggan
     */
    Vol vol;

    /**
     * Constructeur de la classe
     */
    public Toboggan() {
        super();
        this.vol = null;
    }

    /**
     * Constructeur de la classe
     * @param pointEntree
     * @param pointSortie
     */
    public Toboggan(Intersection pointEntree, Intersection pointSortie) {
        super(pointEntree, pointSortie);
        this.vol = null;
    }

    /**
     * Fait avancer la position des bagages en fonction de la duree
     * @param duree
     */
    public void Avancer(Double duree) {
        super.Avancer(duree);
        double progression = duree*vitesse;
        
        ListIterator<Bagage> itBagage = bagagesPortes.listIterator();
        
        while(itBagage.hasNext()) {
            Bagage bagage = itBagage.next();
            bagage.Avancer(progression);
            if(bagage.getPosition() >= longueur) {
                itBagage.remove();
                bagagesAttente.addLast(bagage);
                bagage.setCheminPorteur(null);
            }
        }
        
        tempsConsomme += duree;
    }

    /**
     * Ajoute le bagageEntrant à la liste des bagages portés ou en attente
     * @param bagageEntrant
     */
    public void EntrerBagage(Bagage bagageEntrant) {
        super.EntrerBagage(bagageEntrant);
                
        if(bagageEntrant != null) {
            Bagage bagage = bagageEntrant;
            bagage.setCheminPorteur(this);
            
            ListIterator<Bagage> itBagage = bagagesPortes.listIterator(bagagesPortes.size());
            
            while(itBagage.hasPrevious()) {
                Bagage bagageSuivant = itBagage.previous();
                
                double position = bagage.getPosition();
                double positionSuivant = bagageSuivant.getPosition();
                
                if(positionSuivant - position >= distanceBagages) {
                    break;
                }
                
                bagageSuivant.Avancer((position + distanceBagages) - positionSuivant);
                bagage = bagageSuivant;
            }
            
            bagagesPortes.addLast(bagageEntrant);
            
            itBagage = bagagesPortes.listIterator();
            
            while(itBagage.hasNext()) {
                Bagage bagageCourant = itBagage.next();
                
                if(bagageCourant.getPosition() < longueur) {
                    break;
                }
                
                itBagage.remove();
                bagagesAttente.addLast(bagageCourant);
                bagageCourant.setCheminPorteur(null);
            }
        }
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public Vol getVol() {
        return vol;
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
         
         Integer nbBagages = 0;
         if(bagagesPortes != null){
            nbBagages = bagagesPortes.size();
         }
         
         Attr nbBagagesAttribut = document.createAttribute("nbBagages");
         racine.setAttributeNode(nbBagagesAttribut);
         nbBagagesAttribut.setValue(Integer.toString(nbBagages));
         
         Integer idVol = 0;
         if(vol != null){
            idVol = vol.getId();
         }
         
         Attr idVolAttribut = document.createAttribute("idVol");
         racine.setAttributeNode(idVolAttribut);
         idVolAttribut.setValue(Integer.toString(idVol));
         
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
    
    /**
     * Retourne le toboggan de la list en paramètre correspondant à l'id en paramètre
     * @param list
     * @param id
     * @return
     */
    public static Toboggan getTobogganById(LinkedList<Toboggan> list, Integer id) {
        for (Toboggan etr : list) {
            if(etr.getId().intValue() == id.intValue()){
                return etr;
            }
        }
        return null;
    }
}
