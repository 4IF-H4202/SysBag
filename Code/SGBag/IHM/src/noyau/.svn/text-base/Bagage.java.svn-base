package noyau;

import bibliothequesTiers.Point;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Bagage extends ElementNoyau {
    /**
     * @attribute : poids du bagage, en kilogrammes
     */
    private Double poids;
    /**
     * @attribute : vol associé au bagage
     */
    Vol vol;
    /**
     * @attribute : position du bagage sur le chemin porteur ou le chariot (en mètres)
     */
    protected Double position;
    /**
     * @attribute : chemin sur lequel le bagage se déplace
     */    
    protected CheminPorteur cheminPorteur;

    /**
     * Constructeur de la classe
     */
    public Bagage(){
        super();
        this.poids = Double.valueOf(0);
        this.vol = null;
        this.position = Double.valueOf(0);
        this.cheminPorteur=null;
    }

    /**
     * Constructeur de la classe
     * @param poids     Le poids du bagage en kilogrammes
     * @param vol       Le vol associé au bagage
     */
    public Bagage(double poids, Vol vol) {
        super();
        this.poids = poids;
        this.vol = vol;
        this.position = Double.valueOf(0);
        this.cheminPorteur=null;
    }

    /**
     * Méthode qui modifie la position du bagage, en fonction de la progression et de la positionLimite
     * @param progression       La progression (en mètres) que doit réaliser le bagage
     * @param positionLimite    La position limite (en mètres) jusqu'à laquelle le bagage peut se déplacer
     * @return                  La progression (en mètres) que le bagage a réellement pu réaliser
     */
    Double Avancer(Double progression, Double positionLimite) {
        double progressionMax = progression;
        if((position + progression) > positionLimite) {
                progressionMax = positionLimite - position;
                progressionMax = (progressionMax < 0) ? 0 : progressionMax;
        }
        
        position += progressionMax;
        
        return progressionMax;
    }

    /**
     * Méthode qui modifie la position du bagage, en fonction de la progression
     * @param progression   La progression (en mètres) que doit effectuer le bagage
     */
    void Avancer(Double progression) {        
        position += progression;
    }


    /**
     * Modifie le poids du bagage
     * @param poids     Le nouveau poids du bagage (en kilogrammes)
     */
    public void setPoids(Double poids) {
        this.poids = poids;
    }

    /**
     * Récupère le poids du bagage
     * @return  Le poids du bagage (en kilogrammes)
     */
    public Double getPoids() {
        return poids;
    }

    /**
     * Modifie le vol affecté au bagage
     * @param vol   Le nouveau vol à affecter au bagage
     */
    public void setVol(Vol vol) {
        this.vol = vol;
    }

    /**
     * Récupère le vol affecté au bagage
     * @return  Le vol affecté au bagage
     */
    public Vol getVol() {
        return vol;
    }
    
    /**
     * Crée l'objet à partir du noeudDOMRacine
     * @param noeudDOMRacine    Le noeud racine à partir duquel créer l'objet
     * @return                  <code>ElementNoyau.PARSE_OK</code> si tout s'est bien passé,
     *                          <code>PARSE_ERROR</code> sinon
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){

        super.ConstruireAPartirDeDOMXML(noeudDOMRacine);
        //lecture des attributs
        poids = Double.valueOf(noeudDOMRacine.getAttribute("poids"));
        position = Double.valueOf(noeudDOMRacine.getAttribute("position"));

        return ElementNoyau.PARSE_OK;
}
    
    /**
      * Crée le noeudXML pour la persistance
      * @param document     Le document XML dans lequel créer le noeud
      * @return             Le nouveau noeud XML créé
      */
     public Element CreerNoeudXML(Document document) {
         Element racine = document.createElement(this.getClass().getName());

         Attr idAttribut = document.createAttribute("id");
         racine.setAttributeNode(idAttribut);
         idAttribut.setValue(Integer.toString(id));

         Attr poidsAttribut = document.createAttribute("poids");
         racine.setAttributeNode(poidsAttribut);
         poidsAttribut.setValue(Double.toString(poids));
         
         Attr positionAttribut = document.createAttribute("position");
         racine.setAttributeNode(positionAttribut);
         positionAttribut.setValue(Double.toString(position));
     
         Integer idVol = 0;
         if( vol != null){
             idVol = vol.getId();
         }
        
         Attr idVolAttribut = document.createAttribute("idVol");
         racine.setAttributeNode(idVolAttribut);
         idVolAttribut.setValue(Integer.toString(idVol));

         return racine;
        
     }

    /**
     * Modifie la position du bagage sur un chemin porteur ou sur un chariot
     * @param position  La nouvelle position du bagage (en mètres)
     */
    public void setPosition(Double position) {
        this.position = position;
    }

    /**
     * Récupère la position du bagage
     * @return  La position du bagage (en mètres)
     */
    public Double getPosition() {
        return position;
    }

    /**
     * Méthode qui retourne le point correspondant aux coordonnées absolues de l'objet sur le repère réel
     * @param chemin    Le chemin porteur à partir duquel calculer la position absolue du bagage
     * @return          Le point correspondant aux coordonnées absolues (en mètres) du bagage
     */
    private Point calculerCoordonneesAbsolues(CheminPorteur chemin) {
        double deltaX = Double.valueOf(0), deltaY = Double.valueOf(0);
        double dirX;
        // Calcul de la différence en abscisse et en ordonnée du centre du bagage par rapport au noeud d'origine du chemin    
        
        // Calcul du sens de mouvement du bagage
        if (chemin.getTheta() == Math.toRadians(-90)){    // Si le chemin n'est pas vertical
            if (chemin.getNoeudDeb().getY() - chemin.getNoeudFin().getY() < 0)
            {   dirX = Double.valueOf(1); }  // Déplacement vers le bas
            else
            {   dirX = Double.valueOf(-1); } // Déplacement vers le haut        
        }else{
            if (chemin.getNoeudDeb().getX() - chemin.getNoeudFin().getX() < 0)
            {   dirX = Double.valueOf(1); }  // Déplacement vers la droite
            else
            {   dirX = Double.valueOf(-1); } // Déplacement vers la gauche        
        }
        deltaX = position*Math.cos(chemin.getTheta());//+ Math.sin(chemin.getTheta());
        deltaY = position*Math.sin(chemin.getTheta());//- Math.cos(chemin.getTheta());
        return new Point(chemin.getNoeudDeb().getX()+ deltaX, chemin.getNoeudDeb().getY() + deltaY);      
    }

    /**
     * Récupère les coordonnées absolues du bagage dans le repère réel
     * @param chemin    Le chemin porteur à partir duquel calculer la position absolue du bagage
     * @return          Le point correspondant aux coordonnées absolues (en mètre) du bagage
     */
    public Point getCoordonneesAbsolues(CheminPorteur chemin){
        return calculerCoordonneesAbsolues(chemin);      
    }

    /**
     * Récupère les coordonnées absolues du bagage dans le repère réel
     * @return  Le point correspondant aux coordonnées absolues (en mètre) du bagage
     */
    public Point getCoordonneesAbsolues() {
        return calculerCoordonneesAbsolues(cheminPorteur);      
    }

    /**
     * Récupère le chemin porteur sur lequel le bagage se trouve
     * @return  Le chemin porteur sur lequel se trouve le bagage, <code>null</code> si le bagage ne se trouve pas sur un
     *          chemin porteur
     */
    public CheminPorteur getCheminPorteur(){
        return cheminPorteur;
    }
    public void setCheminPorteur(CheminPorteur cheminPorteur){
        this.cheminPorteur = cheminPorteur;
    }

    /**
     * Méthode qui retourne le bagage de la liste en paramètre correspondant à l'id en paramètre
     * @param list  La liste dans laquelle rechercher le bagage
     * @param id    L'identifiant du bagage à retrouver
     * @return      L'élement <code>Bagage</code> correspondant, <code>null</code> si aucun bagage possédant cet
     *              identifiant n'a été trouvé
     */
    public static Bagage getBagageById(LinkedList<Bagage> list, Integer id) {
        for (Bagage el : list) {
            if(el.getId() == id){
                return el;
            }
        }
        return null;
    }
    
}
