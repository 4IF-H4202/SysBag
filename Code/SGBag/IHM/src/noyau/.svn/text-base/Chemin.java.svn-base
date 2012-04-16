package noyau;

import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Chemin extends ElementNoyau {
    /**
     * @attribute : longueur du chemin, en mètres
     */
    protected double longueur;
    /**
     * @attribute : 
     */   
    protected Boolean deplace;
    /**
     * @attribute : intersection de fin du chemin
     */ 
    Intersection noeudFin;
    /**
     * @attribute : intersection de début du chemin
     */ 
    Intersection noeudDeb;

    /**
     * Constructeur de la classe
     */
    public Chemin() {
        super();
        this.noeudDeb = new Intersection();
        this.noeudFin = new Intersection();
        this.longueur = Double.valueOf(0);
    }

    /**
     * Constructeur de la classe
     * @param noeudDeb
     * @param noeudFin
     */
    public Chemin(Intersection noeudDeb, Intersection noeudFin) {
        super();
        this.noeudDeb = noeudDeb;
        this.noeudFin = noeudFin;
    }

    /**
     * @param noeudFin
     */
    public void setNoeudFin(Intersection noeudFin) {
        this.noeudFin = noeudFin;
    }

    /**
     * @return
     */
    public Intersection getNoeudFin() {
        return noeudFin;
    }

    /**
     * @param noeudDeb
     */
    public void setNoeudDeb(Intersection noeudDeb) {
        this.noeudDeb = noeudDeb;
    }

    /**
     * @return
     */
    public Intersection getNoeudDeb() {
        return noeudDeb;
    }

    /**
     * @param longueur
     */
    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }

    /**
     * @return
     */
    public double getLongueur() {
        return longueur;
    }

    /**
     * Méthode qui calcule la longueur du chemin
     */
    private void calculerLongueur() {
        Double xCarre = Math.pow((noeudFin.getX() - noeudDeb.getX()), 2);
        Double yCarre = Math.pow((noeudFin.getY() - noeudDeb.getY()), 2);
        this.longueur = Math.sqrt(xCarre + yCarre);
    }

    /**
     * Calcule et retourne le coefficient directeur de l'équation de la droite comportant le chemin
     * @return
     */
    public Double getCoeffDirecteur(){
        return (noeudFin.getY() - noeudDeb.getY())/(noeudFin.getX()-noeudDeb.getX());
    }

    /**
     * Calcule et retourne l'ordonnée à l'origine de l'équation de la droite comportant le chemin
     * @return
     */
    public Double getOrdAOrigine(){
        return noeudFin.getY() - getCoeffDirecteur()*noeudFin.getX();   
    }

    /**
     * Calcule et retourne l'angle d'orientation de la droite comportant le chemin
     * @return
     */
    public Double getTheta(){

/*         double cosTheta = (noeudFin.getX()-noeudDeb.getX())/getLongueur();
        
        if ((noeudFin.getY()-noeudDeb.getY()) > 0) {
            return Math.acos(getCoeffDirecteur());
        }
        else {
            return -Math.acos(getCoeffDirecteur());
        } */
        
        if((noeudFin.getX()-noeudDeb.getX()) == 0) {
            if((noeudFin.getY()-noeudDeb.getY()) > 0) {
                return Math.PI/2;
            }
            else {
                return -Math.PI/2;
            }      
        }
        if ((noeudFin.getX()-noeudDeb.getX()) > 0) {
        double coef = getCoeffDirecteur();
            return Math.atan(coef);
        }
        else {
            return Math.PI+Math.atan(getCoeffDirecteur());
        }
/*         if (Math.abs(noeudDeb.getX() - noeudFin.getX()) < 2){   // Si le chemin est vertical
            return Double.valueOf(Math.toRadians(-90)); // Correspond au cas d'un chemin vertical (ordonnée à l'origine indéfini)
        }else if (Math.abs(noeudDeb.getY() - noeudFin.getY()) < 2){ // Si le chemin est horizontal
            return Double.valueOf(0); // Correspond au cas d'un chemin horizontal
        }
        else{
            return Math.tan(getCoeffDirecteur());
        } */
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

         Attr longueurAttribut = document.createAttribute("longueur");
         racine.setAttributeNode(longueurAttribut);
         longueurAttribut.setValue(Double.toString(longueur));
         
         return racine;
        
     }

    /**
     * @param deplace
     */
    public void setDeplace(Boolean deplace) {
        this.deplace = deplace;
    }

    /**
     * @return
     */
    public Boolean isDeplace() {
        return deplace;
    }
}
