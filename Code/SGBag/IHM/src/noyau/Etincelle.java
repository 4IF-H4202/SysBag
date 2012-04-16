package noyau;

import bibliothequesTiers.Point;

public class Etincelle {

    /**
     * @attribute: déccélération minimum (en mètres/seconde)
     */
    private final static Double DECELERATION_MIN = Double.valueOf(0);
    /**
     * @attribute : déccélération maximum (en mètres/seconde)
     */
    private final static Double DECELERATION_MAX = Double.valueOf(200);
    /**
     * @attribute : intensité minimum
     */
    private final static Double INTENSITE_MIN = Double.valueOf(1);
    /**
     * @attribute : intensité maximum
     */
    private final static Double INTENSITE_MAX = Double.valueOf(5);
    /**
     * @attribute : durée de vie (en secondes)
     */
    public final static Double DUREE_DE_VIE = Double.valueOf(0.5);
    /**
     * @attribute : rail portant l'étincelle
     */
    protected Rail rail;
    /**
     * @attribute : position (en mètres) de l'étincelle sur le rail
     */
    protected Double position; 
    /**
     * @attribute : indice de progression de l'étincelle, de 0 à 1
     */
    protected Double progression; 
    /**
     * @attribute : intensité, entre INTENSITE_MIN et INTENSITE_MAX
     */
    protected Double intensite;


    /**
     * Constructeur de la classe
     */
    public Etincelle() {
        super();
        position = Double.valueOf(0);
        intensite = INTENSITE_MIN;
        progression = Double.valueOf(0);
    }

    /**
     * Constructeur de la classe
     * @param deceleration  L'intensité de la décélération du chariot à l'origine de l'étincelle
     * @param position      La position sur le rail (en mètres) à laquelle le freinage est survenu
     */
    public Etincelle(Double deceleration, Double position) {
        super();
        intensite = (deceleration - DECELERATION_MIN)*(INTENSITE_MAX - INTENSITE_MIN)/(DECELERATION_MAX - DECELERATION_MIN) + INTENSITE_MIN;
        this.position = position;
        progression = Double.valueOf(0);
    }

    /**
     * Modifie l'indice de progression de l'étincelle
     * @param progression   Le nouvel indice de progression de l'étincelle (entre <code>0.0</code> et <code>1.0</code>)
     */
    public void setProgression(Double progression) {
        this.progression = progression;
    }

    /**
     * Récupère l'indice de progression de l'étincelle
     * @return  L'indice de progression de l'étincelle
     */
    public Double getProgression() {
        return progression;
    }

    /**
     * Fait avancer la progression de l'étincelle, en fonction du jetonTemps
     * @param jetonTemps    Durée (en secondes) pendant laquelle faire évoluer l'étincelle
     */
    public void Avancer(Double jetonTemps) {
        progression += jetonTemps/DUREE_DE_VIE;
    }

    /**
     * Modifie le rail sur lequel l'étincelle a été déclenchée
     * @param rail  Le nouveau rail à affecter
     */
    public void setRail(Rail rail) {
        this.rail = rail;
    }
    
    /**
     * Récupère le rail sur lequel l'étincelle a été déclenchée
     * @return  L'objet <code>Rail</code> sur lequel l'étincelle a été déclenchée
     */
    public Rail getRail() {
        return rail;
    }

    /**
     * Calcule et retourne le point correspondant aux coordonnées absolues de l'étincelle, dans le repère réel
     * @return  Le point correspondant aux coordonnées absolues (en mètres) de l'étincelle
     */
    public Point getCoordonneesAbsolues() {
        double deltaX = Double.valueOf(0), deltaY = Double.valueOf(0);
        double dirX;
        // Calcul de la différence en abscisse et en ordonnée du centre du bagage par rapport au noeud d'origine du chemin    
        
        // Calcul du sens de mouvement du bagage
        if (rail.getTheta() == Math.toRadians(-90)){    // Si le chemin n'est pas vertical
            if (rail.getNoeudDeb().getY() - rail.getNoeudFin().getY() < 0)
            {   dirX = Double.valueOf(1); }  // Déplacement vers le bas
            else
            {   dirX = Double.valueOf(-1); } // Déplacement vers le haut        
        }else{
            if (rail.getNoeudDeb().getX() - rail.getNoeudFin().getX() < 0)
            {   dirX = Double.valueOf(1); }  // Déplacement vers la droite
            else
            {   dirX = Double.valueOf(-1); } // Déplacement vers la gauche        
        }
        deltaX = position*Math.cos(rail.getTheta());
        deltaY = position*Math.sin(rail.getTheta());
        return new Point(rail.getNoeudDeb().getX()+ deltaX, rail.getNoeudDeb().getY() + deltaY);
    }

    /**
     * Récupère l'instensité de l'étincelle
     * @return L'intensité de l'étincelle
     */
    public Double getIntensite() {
        return intensite;
    }
}
