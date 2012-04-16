package noyau;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementDynamique {
    /**
     * @attribute : 
     */
    protected Integer jetonsTempsDepenses;

    /**
     * @attribute : vitesse
     */
    protected Integer vitesse;

    /**
     * Constructeur de la classe
     * @param jetonsTempsDepenses
     * @param vitesse
     */
    public ElementDynamique(Integer jetonsTempsDepenses, Integer vitesse) {
        this.jetonsTempsDepenses = jetonsTempsDepenses;
        this.vitesse = vitesse;
    }

    Integer NouvelEtat(Integer jetonsTempsMin, Integer jetonsTempsMax) {
        return null;
    }

    public void setJetonsTempsDepenses(Integer jetonsTempsDepenses) {
        this.jetonsTempsDepenses = jetonsTempsDepenses;
    }

    public Integer getJetonsTempsDepenses() {
        return jetonsTempsDepenses;
    }

    public void setVitesse(Integer vitesse) {
        this.vitesse = vitesse;
    }

    public Integer getVitesse() {
        return vitesse;
    }
    
    // Partie utile pour la génération à partir du XML : 
    /**
     * Crée l'objet à partir du noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        //TODO : gerer les erreurs

        //lecture des attributs
        jetonsTempsDepenses = Integer.parseInt(noeudDOMRacine.getAttribute("jetons"));
        vitesse = Integer.parseInt(noeudDOMRacine.getAttribute("vitesse"));

        return ElementNoyau.PARSE_OK;
    }
    
    
    /**
      * Crée le noeud xml, pour la persistance
      * @param document
      * @return
      */
     public Element CreerNoeudXML(Document document) {
         Element racine = document.createElement(this.getClass().getName());

         Attr jetonsAttribut = document.createAttribute("jetonsTempsDepenses");
         racine.setAttributeNode(jetonsAttribut);
         jetonsAttribut.setValue(Integer.toString(jetonsTempsDepenses));
         
         Attr vitesseAttribut = document.createAttribute("vitesse");
         racine.setAttributeNode(vitesseAttribut);
         vitesseAttribut.setValue(Integer.toString(jetonsTempsDepenses));
         
         return racine;
        
     }
}
