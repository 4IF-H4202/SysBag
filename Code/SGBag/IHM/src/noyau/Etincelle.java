package noyau;

import bibliothequesTiers.Point;

public class Etincelle {

    /**
     * @attribute: d�cc�l�ration minimum (en m�tres/seconde)
     */
    private final static Double DECELERATION_MIN = Double.valueOf(0);
    /**
     * @attribute : d�cc�l�ration maximum (en m�tres/seconde)
     */
    private final static Double DECELERATION_MAX = Double.valueOf(200);
    /**
     * @attribute : intensit� minimum
     */
    private final static Double INTENSITE_MIN = Double.valueOf(1);
    /**
     * @attribute : intensit� maximum
     */
    private final static Double INTENSITE_MAX = Double.valueOf(5);
    /**
     * @attribute : dur�e de vie (en secondes)
     */
    public final static Double DUREE_DE_VIE = Double.valueOf(0.5);
    /**
     * @attribute : rail portant l'�tincelle
     */
    protected Rail rail;
    /**
     * @attribute : position (en m�tres) de l'�tincelle sur le rail
     */
    protected Double position; 
    /**
     * @attribute : indice de progression de l'�tincelle, de 0 � 1
     */
    protected Double progression; 
    /**
     * @attribute : intensit�, entre INTENSITE_MIN et INTENSITE_MAX
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
     * @param deceleration  L'intensit� de la d�c�l�ration du chariot � l'origine de l'�tincelle
     * @param position      La position sur le rail (en m�tres) � laquelle le freinage est survenu
     */
    public Etincelle(Double deceleration, Double position) {
        super();
        intensite = (deceleration - DECELERATION_MIN)*(INTENSITE_MAX - INTENSITE_MIN)/(DECELERATION_MAX - DECELERATION_MIN) + INTENSITE_MIN;
        this.position = position;
        progression = Double.valueOf(0);
    }

    /**
     * Modifie l'indice de progression de l'�tincelle
     * @param progression   Le nouvel indice de progression de l'�tincelle (entre <code>0.0</code> et <code>1.0</code>)
     */
    public void setProgression(Double progression) {
        this.progression = progression;
    }

    /**
     * R�cup�re l'indice de progression de l'�tincelle
     * @return  L'indice de progression de l'�tincelle
     */
    public Double getProgression() {
        return progression;
    }

    /**
     * Fait avancer la progression de l'�tincelle, en fonction du jetonTemps
     * @param jetonTemps    Dur�e (en secondes) pendant laquelle faire �voluer l'�tincelle
     */
    public void Avancer(Double jetonTemps) {
        progression += jetonTemps/DUREE_DE_VIE;
    }

    /**
     * Modifie le rail sur lequel l'�tincelle a �t� d�clench�e
     * @param rail  Le nouveau rail � affecter
     */
    public void setRail(Rail rail) {
        this.rail = rail;
    }
    
    /**
     * R�cup�re le rail sur lequel l'�tincelle a �t� d�clench�e
     * @return  L'objet <code>Rail</code> sur lequel l'�tincelle a �t� d�clench�e
     */
    public Rail getRail() {
        return rail;
    }

    /**
     * Calcule et retourne le point correspondant aux coordonn�es absolues de l'�tincelle, dans le rep�re r�el
     * @return  Le point correspondant aux coordonn�es absolues (en m�tres) de l'�tincelle
     */
    public Point getCoordonneesAbsolues() {
        double deltaX = Double.valueOf(0), deltaY = Double.valueOf(0);
        double dirX;
        // Calcul de la diff�rence en abscisse et en ordonn�e du centre du bagage par rapport au noeud d'origine du chemin    
        
        // Calcul du sens de mouvement du bagage
        if (rail.getTheta() == Math.toRadians(-90)){    // Si le chemin n'est pas vertical
            if (rail.getNoeudDeb().getY() - rail.getNoeudFin().getY() < 0)
            {   dirX = Double.valueOf(1); }  // D�placement vers le bas
            else
            {   dirX = Double.valueOf(-1); } // D�placement vers le haut        
        }else{
            if (rail.getNoeudDeb().getX() - rail.getNoeudFin().getX() < 0)
            {   dirX = Double.valueOf(1); }  // D�placement vers la droite
            else
            {   dirX = Double.valueOf(-1); } // D�placement vers la gauche        
        }
        deltaX = position*Math.cos(rail.getTheta());
        deltaY = position*Math.sin(rail.getTheta());
        return new Point(rail.getNoeudDeb().getX()+ deltaX, rail.getNoeudDeb().getY() + deltaY);
    }

    /**
     * R�cup�re l'instensit� de l'�tincelle
     * @return L'intensit� de l'�tincelle
     */
    public Double getIntensite() {
        return intensite;
    }
}
