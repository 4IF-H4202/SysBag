package noyau;

import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CheminPorteur extends Chemin {

    /**
     * @attribute : distance entre deux bagages
     */
    protected Double distanceBagages;
    
    /**
     * @associates <{noyau.Bagage}> : liste des bagages portés par le chemin
     */
    protected LinkedList<Bagage> bagagesPortes;
    /**
     * @associates <{noyau.Bagage}> : liste des bagages en attente sur le chemin
     */   
    protected LinkedList<Bagage> bagagesAttente;

    /**
     * @attribute : vitesse
     */
    protected Double vitesse;
    
    /**
     * @attribute : nombre limite de bagages sur le chemin
     */
    protected Double limiteBagages;
    /**
     * @attribute : temps en "instant"
     */   
    protected Double tempsConsomme;

    /**
     * Constructeur de la classe
     */
    public CheminPorteur() {
        super();
        noeudDeb.setPointDepot(this);
        noeudFin.setPointRetrait(this);
        this.bagagesPortes = new LinkedList<Bagage>();
        this.bagagesAttente = new LinkedList<Bagage>();
        this.vitesse = Double.valueOf(0);
        this.distanceBagages = Double.valueOf(0);
        this.limiteBagages = Double.valueOf(0);
        this.tempsConsomme = Double.valueOf(0);
    }

    /**
     * Constructeur de la classe
     * @param pointEntree
     * @param pointSortie
     */
    CheminPorteur(Intersection pointEntree, Intersection pointSortie) {
        super(pointEntree, pointSortie);
        pointEntree.setPointDepot(this);
        pointSortie.setPointRetrait(this);
        this.bagagesPortes = new LinkedList<Bagage>();
        this.bagagesAttente = new LinkedList<Bagage>();
        this.vitesse = Double.valueOf(0);
        this.distanceBagages = Double.valueOf(0);
        this.limiteBagages = Double.valueOf(0);
        this.tempsConsomme = Double.valueOf(0);
    }
    public void Avancer(Double duree) {
    }
    
    public void AvancerBagages(Double jetonTemps) {
        Avancer(jetonTemps - tempsConsomme);
    }
    
    public void EntrerBagage(Bagage bagageEntrant) {
    }
    
    public Bagage SortirBagage(Double[] dureeSortie, Double tempsLimite) {
        return null;
    }

    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }

    public Double getVitesse() {
        return vitesse;
    }

    public void setLimiteBagages(Double limiteBagages) {
        this.limiteBagages = limiteBagages;
    }

    public Double getLimiteBagages() {
        return limiteBagages;
    }

    public Double getDistanceBagages() {
        return distanceBagages;
    }

    public void setDistanceBagages(Double distanceBagages) {
        this.distanceBagages = distanceBagages;
    }

    public Double getTempsConsomme() {
        return tempsConsomme;
    }


    /**
     * Crée l'objet à partir de noeudDOMRacine
     * @param noeudDOMRacine
     * @return
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);

        //lecture des attributs
        distanceBagages = Double.valueOf(noeudDOMRacine.getAttribute("distanceBagages"));
        vitesse = Double.valueOf(noeudDOMRacine.getAttribute("vitesse"));
        limiteBagages = Double.valueOf(noeudDOMRacine.getAttribute("limiteBagages"));
        
        return ElementNoyau.PARSE_OK;
    }
    
    
    
    /**
      * Crée le noeud xml, pour la persistance
      * @param document
      * @return
      */
     public Element CreerNoeudXML(Document document) {
         Element racine = document.createElement(this.getClass().getName());
         
         return racine;
        
     }

    public LinkedList<Bagage> getBagagesPortes() {
        return bagagesPortes;
        
    }
    
    public void setDeplace(Boolean deplace) {
        super.setDeplace(deplace);
        if(!deplace) {
            tempsConsomme = Double.valueOf(0);
        }
    }

    public void setBagagesPortes(LinkedList<Bagage> bagagesPortes) {
        this.bagagesPortes = bagagesPortes;
    }

    public LinkedList<Bagage> getBagagesAttente() {
        return bagagesAttente;
    }

    public void setNoeudFin(Intersection noeudFin) {
        super.setNoeudFin(noeudFin);
        noeudFin.setPointRetrait(this);
    }

    public void setNoeudDeb(Intersection noeudDeb) {
        super.setNoeudDeb(noeudDeb);
        noeudDeb.setPointDepot(this);
    }
}
