package noyau;

import java.util.ArrayList;
import java.util.Collection;

import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Vol extends ElementNoyau {
    /**
     * @attribute : nom du vol
     */
    protected String nom;

    /**
     * @attribute : nombre de bagages maximum portés par le vol
     */
    protected Integer nbBagagesMax;

    /**
     * @associates <{noyau.Bagage}> : liste des bagages chargés sur le vol
     */
    ArrayList<Bagage> bagages;

    /**
     * Constructeur de la classe
     */
    public Vol() {
        super();
        nom = "";
        nbBagagesMax = 0;
        bagages = new ArrayList<Bagage>();
    }

    /**
     * Constructeur de la classe
     * @param nom
     * @param nbBagagesMax
     */
    public Vol(String nom, Integer nbBagagesMax) {
        super();
        this.nom = nom;
        this.nbBagagesMax = nbBagagesMax;
        bagages = new ArrayList<Bagage>();
    }
    

    /**
     * Crée l'objet à partir du noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);

        //lecture des attributs
        nom = noeudDOMRacine.getAttribute("nom");
        nbBagagesMax = Integer.parseInt(noeudDOMRacine.getAttribute("nbBagagesMax"));

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
        
         Attr nomAttribut = document.createAttribute("nom");
         racine.setAttributeNode(nomAttribut);
         nomAttribut.setValue(nom);
         
         Attr nbBagagesMaxAttribut = document.createAttribute("nbBagagesMax");
         racine.setAttributeNode(nbBagagesMaxAttribut);
         nbBagagesMaxAttribut.setValue(Integer.toString(nbBagagesMax));
         
         return racine;
        
     }


    /**
     * Retourne le vol de la list en paramètre correspondant à l'id en paramètre
     * @param list
     * @param id
     * @return
     */
    public static Vol getVolById(LinkedList<Vol> list, Integer id) {
        for (Vol el : list) {
            if(el.getId().intValue() == id.intValue()){
                return el;
            }
        }
        return null;
    }
    
    public String toString(){
        return this.nom;
    }

    public String getNom() {
        return this.nom;
    }
}
