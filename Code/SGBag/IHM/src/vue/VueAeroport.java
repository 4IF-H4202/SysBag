package vue;

import bibliothequesTiers.Point;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import noyau.Aeroport;

import noyau.Bagage;
import noyau.Chariot;
import noyau.Chemin;
import noyau.ElementNoyau;

import noyau.Intersection;

import noyau.Rail;

import noyau.TapisRoulant;

import noyau.Toboggan;

import noyau.Vol;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

/** Vue de l'a�roport qui g�re l'affichage (les vues) de tous les �l�ments affich�s dans notre interface.
 */
public class VueAeroport {

    private int largeur;    // Largeur du cadre en m�tre.
    private int hauteur;    // Hauteur du cadre en m�tre.
    private double coefReducteur;   // Coefficient initial d'agrandissement/de r�tr�cissement d'affichage des �l�ments.
    private Double coefZoom = 2.0;   // Coefficient de zoom pour l'affichage des �l�ments.
    private Integer x0 = 0; // Coordonn�es du coin haut-gauche (origine de ce qui est affich�)
    private Integer y0 = 0;
    private LinkedList<VueElementsClickable> elements;  // Liste des vue d'�l�ments � g�rer.
    private LinkedList<VueElementsClickable> menus = new LinkedList<VueElementsClickable> ();  // Liste des vue de menus � g�rer.
    private LinkedList<VueBagage> bagages;  // Liste des bagages g�r�s dans l'a�roport.
    private Aeroport aeroport;
    private Skin skin;      // Instance contenant les skins des �l�ments � afficher.
    
    private boolean afficheChariot = true;
    private boolean afficheIntersection = true;
    private boolean afficheRail = true;
    private boolean afficheVoieGarage = true;
    private boolean afficheTapisRoulant = true;
    private boolean afficheToboggan = true;
    private boolean afficheBagage = true;
    
    

    /**
     * Constructeur de la classe.
     * 
     * @param aeroport  L'a�roport � g�rer.
     * @param largeur   Largeur du cadre en pixel.
     * @param hauteur   Hauteur du cadre en pixel.
     */
    public VueAeroport(Aeroport aeroport, int largeur, int hauteur){
        
        skin = new Skin();
        bagages = new LinkedList<VueBagage>();
        
        // On importe les images dans la classe Skin
        try{
            // cr�ation d'une fabrique de documents
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
    
            // cr�ation d'un constructeur de documents
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();
    
            // lecture du contenu d'un fichier XML avec DOM
            File xml = new File("src\\vue\\Skin_XML.xml");
            Document document = constructeur.parse(xml);
    
            Element racine = document.getDocumentElement();
            skin.ImporterImagesSkin(racine);
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        elements = new LinkedList<VueElementsClickable>();
        this.aeroport = aeroport;        
        this.largeur = largeur;
        this.hauteur = hauteur;
        
        double rapportPanel = (double)this.hauteur/(double)this.largeur;
        double rapportAeroport = (double)aeroport.getLargeurM()/(double)aeroport.getLongueurM();
        
        coefReducteur = (rapportPanel < rapportAeroport) ? (double)hauteur/(double)aeroport.getLargeurM() : 
            (double)largeur/(double)aeroport.getLongueurM();
        
        chargerVues();
    }
    
    /**
     * Ajoute un objet VueElementsClickable � la liste de vues.
     * @param vEC vue ajout�.
     */
    public void ajouterVueElementsClickable(VueElementsClickable vEC){
        elements.add(vEC);
    }
    
    /**
     * Ajoute un objet menu VueElementsClickable de la liste de vues.
     * @param vEC vue � supprimer.
     */    
    public void ajouterVueMenu(VueElementsClickable vEC){
        menus.add(vEC);
     /*   Point pt = vEC.getCoordMax();
        xMax = (xMax < (int)pt.getX())? (int)pt.getX():xMax;
        yMax = (yMax < (int)pt.getY())? (int)pt.getY():yMax; 
    */
    }
    
    
    /**
     * Supprime un objet VueElementsClickable de la liste de vues.
     * @param vEC vue � supprimer.
     */
    public void supprimerVueElementsClickable(VueElementsClickable vEC){    // A tester quand le programme compilera
        elements.remove(vEC);
    }


    /**
     * Dessine tous les �l�ments � afficher dans le cadre.
     * @param g Instance Graphics du Panel.
     */
    public void Dessiner(Graphics g) {        
        // Efface le contenu du cadre pour redessiner.
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, largeur, hauteur);
                
        bagages = new LinkedList<VueBagage>();
        
        // Affichage des �l�ments dans le cadre
        for (int i = 0; i < elements.size() ; i++){
            if (elements.get(i).getClass()==VueChariot.class){
                if (afficheChariot) {
                    Chariot chariot = (Chariot)elements.get(i).getElement();
                    if(chariot.getEstArrete()==true){
                        elements.get(i).setSurbrillance(true);
                    }
                    else{
                        elements.get(i).setSurbrillance(false);
                    }
                    elements.get(i).Dessiner(g);
                }
                
            }
            else if ((elements.get(i).getClass()==VueRail.class) && afficheRail) {
                elements.get(i).Dessiner(g);
            }
            else if (elements.get(i).getClass()==VueIntersection.class) {
                VueIntersection v = (VueIntersection)elements.get(i);
                Intersection inter = (Intersection)(v.getElement());
                if (!inter.getVoiesSuiv().isEmpty()) {
                    if (afficheIntersection) {
                        elements.get(i).Dessiner(g);
                    }
                } else if(inter.getVoiesSuiv().isEmpty() && afficheToboggan && inter.getPointRetrait()!=null){
                    elements.get(i).Dessiner(g);
                } else if(inter.getVoiesSuiv().isEmpty() && afficheTapisRoulant && inter.getPointDepot()!=null){
                        elements.get(i).Dessiner(g);
                }
            }
            else if ((elements.get(i).getClass()==VueVoieGarage.class) && afficheVoieGarage) {
                elements.get(i).Dessiner(g);
            }
            else if ((elements.get(i).getClass()==VueTapisRoulant.class) && afficheTapisRoulant) {
                elements.get(i).Dessiner(g);
            }
            else if ((elements.get(i).getClass()==VueToboggan.class) && afficheToboggan) {
                elements.get(i).Dessiner(g);
            }
            
        }
        for(VueBagage bagage : bagages) {
            if (afficheBagage){
            bagage.Dessiner(g);
        }
        }
        
        for (VueElementsClickable el : menus) {
            el.Dessiner(g);
        }
        
        
  //      g2D.setTransform(oldTrans);
    }

    /**
     * Renvoie l'�l�ment correspondant aux coordonn�es en param�tres.
     * @param x Abscisse en pixel.
     * @param y Ordonn�e en pixel.
     * @return null si les coordonn�es ne correspondent � aucun �l�ment, dans le cas contraire la vue de l'�lement correspondante est renvoy�e.
     */
    public VueElementsClickable selectElement(int x, int y){
        
        // V�rification sur les choix de direction
        for (VueElementsClickable vue : menus) {
            vue.preciserSelection(false);
            if (vue.EstClicke(x, y)){
                vue.preciserSelection(true);
                return vue;
            }
        }
/*         for (int i = 0; i < elements.size(); i++){
            if (elements.get(i).getClass() == VueChoixDirection.class){
                VueElementsClickable vue = elements.get(i);
                vue.preciserSelection(false);
                if (vue.EstClicke(x, y)){
                    vue.preciserSelection(true);
                    return vue;
                }
            }                
        } */
        // V�rification sur les chariots
        for (int i = 0; i < elements.size(); i++){
            if (elements.get(i).getClass() == VueChariot.class){
                VueElementsClickable vue = elements.get(i);
                vue.preciserSelection(false);
                if (vue.EstClicke(x, y)){
                    vue.preciserSelection(true);
                    return vue;
                }
            }                
        }
        // V�rification sur les autres vues.
        for (int i = 0; i < elements.size(); i++){
            if (elements.get(i).getClass() != VueChoixDirection.class && elements.get(i).getClass() != VueChariot.class){
            VueElementsClickable vue = elements.get(i);
            vue.preciserSelection(false);
            if (vue.EstClicke(x, y)){
                vue.preciserSelection(true);
                return vue;
            }
            }
        }
        return null;
    }
    
    /**
     * Convertit le point en m�tre en param�tre en point en pixel.
     * @param pointEnM Point en m�tre.
     * @return Point en pixel.
     */
    public Point convertirEnPixel (Point pointEnM){
        Point pointResultat = new Point();
        pointResultat.setX(pointEnM.getX() * this.coefReducteur*coefZoom/2);
        pointResultat.setY(pointEnM.getY() * this.coefReducteur*coefZoom/2); 
        return pointResultat;
    }
    
    /**
     * Convertie une distance en m�tre en distance en pixel.
     * @param dist Distance en m�tre.
     * @return Distance en pixel.
     */
    public Double convertirEnPixel (Double dist){
        return (dist * this.coefReducteur*coefZoom/2);
    }
    
    
    /**
     * Renvoie l'instance a�roport correspondant � l'affichage.
     * @return L'instance a�roport g�r�.
     */
    public Aeroport getAeroport(){
        return aeroport;
    }
    
    /**
     * Modifie l'instance a�roport g�r�.
     * @param aeroport Nouvelle a�roport.
     */
    public void setAeroport(Aeroport aeroport){
        this.aeroport = aeroport;
    }    

    /**
     * Fait avancer tous les objets de l'a�roport d'un "instant".
     */
    public void avancer(){
        aeroport.avancer();
    }


    /**
     * Retourne la liste d'�l�ments � afficher.
     * @return Liste d'�l�ments � afficher.
     */
    public LinkedList<VueElementsClickable> getElements(){
        return elements;
    }
    

    /**
     * D�finit les tapis roulants � mettre en surbrillance ou non, en fonction du param�tre.
     * @param enSurbrillance Bool�en correspondant � la surbrillance des tapis roulants.
     */
    public void SurbrillanceTR(boolean enSurbrillance){
        for (VueElementsClickable vueElementCourant : elements ){
            if(vueElementCourant.getClass()==VueIntersection.class ){ // Si la vue correspond � une intersection.
                Intersection intersection = (Intersection) vueElementCourant.getElement();
                if(intersection.getPointDepot()!=null){ // tapis roulant pr�sent
                    vueElementCourant.preciserSelection(false);
                    vueElementCourant.setSurbrillance(enSurbrillance);
                }
            }   
        }
    }

    /**
     * Construit une simulation � partir d'un fichier de simulation XML.
     * Cr�ation de tous les �l�ments de l'a�roport ainsi que ses vues.
     * @param noeudDOMRacine Element correspondant � un noeud XML.
     * @return PARSE_OK si tout se passe bien, sinon PARSE_ERROR.
     */
    public int ConstruireSimulAPartirDeDOMXML(Element noeudDOMRacine) {
        // Cr�ation de tous les �l�ments.

        // On passe l'integralit� du fichier � Aeroport :
        if (aeroport.ConstruireSimulAPartirDeDOMXML(noeudDOMRacine) != Aeroport.PARSE_OK) {
            return Aeroport.PARSE_ERROR;
        }
        
        // Cr�ation de vues pour chaque �l�ment.
        // Pour chaque �l�ment reli� � a�roport (qui poss�de une vue) on cr�e la vue associ�e !        
        //chargerVues();
        return ElementNoyau.PARSE_OK;
    }
 
 
    /**
     * @return Le coefficient d'aggrandissement/de r�tr�cissement des affichages.
     */
    public double getCoefReducteur() {
        return coefReducteur*coefZoom/2;
}

    /**
     * @return L'instance Skin.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Charge les vues des �l�ments de l'a�roport.
     */
    private void chargerVues() {
        for (int i = 0; i < aeroport.getRails().size() ; i++){
            VueRail vue = new VueRail(aeroport.getRails().get(i), this);
            elements.add(vue);
        }
        for (int i = 0; i < aeroport.getTapisRoulants().size() ; i++){
            VueTapisRoulant vue= new VueTapisRoulant(aeroport.getTapisRoulants().get(i), this);
            elements.add(vue);
        }
        for (int i = 0; i < aeroport.getToboggans().size() ; i++){
            VueToboggan vue= new VueToboggan(aeroport.getToboggans().get(i), this);
            elements.add(vue);
        }     
        for (int i = 0; i < aeroport.getIntersections().size() ; i++){
            VueIntersection vue= new VueIntersection(aeroport.getIntersections().get(i), this);
            elements.add(vue);
        }
        for (int i = 0; i < aeroport.getVoiesGarage().size() ; i++){
            VueVoieGarage vue= new VueVoieGarage(aeroport.getVoiesGarage().get(i), this);
            elements.add(vue);
        } 
        for (int i = 0; i < aeroport.getChariots().size() ; i++){
            VueChariot vue= new VueChariot(aeroport.getChariots().get(i), this);
            elements.add(vue);
        }
            }            


    /**
     * Indique que tous les �lements de l'a�roport ne sont pas s�lectionn�s.
     */
    public void deselectionnerTout() {
        for (VueElementsClickable el : elements) {
            el.preciserSelection(false);
        }
        for (VueElementsClickable el : menus) {
            el.preciserSelection(false);
    }
    }

    /**
     * Supprime les vues (et donc l'affichage) des menus de choix de direction dans le cadre.
     */
    public void supprimerVuesDirection(){
            menus.clear();
/*         for (VueElementsClickable elementCourant : elements){
            if(elementCourant.getClass()==VueChoixDirection.class){ // si c'est une vueChoixDirection
                VueChoixDirection vue = (VueChoixDirection)elementCourant;
                vue.supprimerVueMenu(); // supprimer le menu et ses choix
                break;
            }
        } */
    }
    
    /**
     * Ajoute une vue de bagage.
     * @param vueBagage Vue du bagage � ajouter.
     */
    public void ajouterVueBagage(VueBagage vueBagage) {
        bagages.add(vueBagage);
    }

    public void setAfficheChariot(boolean afficheChariot) {
        this.afficheChariot = afficheChariot;
    }

    public void setAfficheIntersection(boolean afficheIntersection) {
        this.afficheIntersection = afficheIntersection;
    }

    public void setAfficheRail(boolean afficheRail) {
        this.afficheRail = afficheRail;
    }

    public void setAfficheVoieGarage(boolean afficheVoieGarage) {
        this.afficheVoieGarage = afficheVoieGarage;
    }

    public void setAfficheTapisRoulant(boolean afficheTapisRoulant) {
        this.afficheTapisRoulant = afficheTapisRoulant;
    }

    public void setAfficheToboggan(boolean afficheToboggan) {
        this.afficheToboggan = afficheToboggan;
    }

    public void setAfficheBagage(boolean afficheBagage) {
        this.afficheBagage = afficheBagage;
    }
     public void setCoefZoom(Double coefZoom) {
         if (coefZoom > 0.2 && coefZoom < 10.0) {
            this.coefZoom = coefZoom;
         }
    }

    public Double getCoefZoom() {
        return coefZoom;
    }

    public void setX0(Integer x0) {
        this.x0 = x0;
    }

    public Integer getX0() {
        return x0;
    }

    public void setY0(Integer y0) {
        this.y0 = y0;
    }

    public Integer getY0() {
        return y0;
    }
    
    public Point getPoint0() {
        return new Point((x0.intValue()), (y0.intValue()));
    }
}
