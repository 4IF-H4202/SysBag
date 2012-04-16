package noyau;

import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementNoyau {

    /**
     * @attribute : code d'erreur
     */
    static public final int PARSE_ERROR = -1;
    /**
     * @attribute : code de non erreur
     */
    static public final int PARSE_OK = 1;
    /**
     * @attribute : id incr�ment� � chaque cr�ation d'�l�ment noyau
     */
    static protected Integer idCount = 0;
    /**
     * @attribute : id de l'objet
     */
    Integer id ;

    /**
     * Constructeur de la classe
     */
    public ElementNoyau() {
        id = idCount++;
    }

    /**
     * Cr�e l'objet � partir du noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){
        
        id = Integer.parseInt(noeudDOMRacine.getAttribute("id"));
        
        return PARSE_OK;
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

        return racine;    
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    /*public ElementNoyau getElementById(LinkedList<ElementNoyau> list, Integer id) {
        for (ElementNoyau el : list) {
            if(el.getId() == id){
                return el;
            }
        }
    }
*/
}
