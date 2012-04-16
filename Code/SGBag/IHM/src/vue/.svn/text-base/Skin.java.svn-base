package vue;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import noyau.Aeroport;
import noyau.ElementNoyau;
import noyau.Intersection;
import noyau.Rail;
import noyau.TapisRoulant;
import noyau.Toboggan;
import noyau.Vol;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


    /** Objet stockant un dictionnaire d'images correspondant aux skins des éléments de l'application.
     * @author H4204
     */
public class Skin{
    
    // Liste des images qui seront chargées dans un dictionnaire d'images
    protected BufferedImage imgIntersection = null;
    protected BufferedImage imgIntersectionSelection = null;
    protected BufferedImage imgRail = null;
    protected BufferedImage imgBagage = null;
    protected BufferedImage imgChariot = null;
    protected BufferedImage imgChariotSelection = null;
    protected BufferedImage imgChariotSurbrillance = null;
    protected BufferedImage imgGarage = null;
    protected BufferedImage imgGarageSelection = null;
    protected BufferedImage imgTapisRoulant = null;
    protected BufferedImage imgToboggan = null;
    protected BufferedImage imgDebutCheminPorteurSelection = null;
    protected BufferedImage imgDebutTapisRoulant = null;
    protected BufferedImage imgDebutTapisRoulantSurbrillance = null;
    protected BufferedImage imgFinToboggan = null;
    protected BufferedImage imgMenuChariotManuel = null;
    protected BufferedImage imgMenuChariotManuel_ChargerBagage = null;
    protected BufferedImage imgMenuChariotManuel_DechargerBagage = null;
    protected BufferedImage imgMenuChariotManuel_Direction = null;

    protected List<BufferedImage> listImgEtincelleD = null;
    protected List<BufferedImage> listImgEtincelleG =  null;
    
    protected HashMap<String,BufferedImage> mapImages = null;

    /**
     * Constructeur de la classe.
     */
    public Skin() {
        super();
        mapImages = new HashMap<String,BufferedImage>();
        listImgEtincelleD = new ArrayList<BufferedImage>();
        listImgEtincelleG = new ArrayList<BufferedImage>();
    }  
    
    
    /**
     * Méthode de chargement des images à partir d'un fichier xml
     * @param noeudDOMRacine
     * @return PARSE_OK si tout s'est bien déroulé, et PARSE_ERROR dans le cas d'une erreur.
     */
    public int ImporterImagesSkin(Element noeudDOMRacine){
        
        String lienImage = null;
        
        
        String tag = "imgIntersection";
        NodeList liste = noeudDOMRacine.getElementsByTagName(tag);
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgIntersection = (Element) liste.item(i);           
            lienImage = imgIntersection.getAttribute("lien_image");
        }        
        try {
            imgIntersection = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgIntersectionSelection";
        liste = noeudDOMRacine.getElementsByTagName(tag);
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgIntersectionSelection = (Element) liste.item(i);
            lienImage = imgIntersectionSelection.getAttribute("lien_image");
        }
        try {
            imgIntersectionSelection = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgRail";
        liste = noeudDOMRacine.getElementsByTagName(tag);
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgRail = (Element) liste.item(i);
            lienImage = imgRail.getAttribute("lien_image");
        }
        try {
            imgRail = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgBagage";
        liste = noeudDOMRacine.getElementsByTagName(tag);
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgBagage = (Element) liste.item(i);
            lienImage = imgBagage.getAttribute("lien_image");
        }
        try {
            imgBagage = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgChariot";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgChariot = (Element) liste.item(i);
            lienImage = imgChariot.getAttribute("lien_image");
        }        
        try {
            imgChariot = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }        
        
        
        tag = "imgChariotSelection";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgChariotSelection = (Element) liste.item(i);
            lienImage = imgChariotSelection.getAttribute("lien_image");
        }        
        try {
            imgChariotSelection = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
            
                
        tag = "imgChariotSurbrillance";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgChariotSurbrillance = (Element) liste.item(i);
            lienImage = imgChariotSurbrillance.getAttribute("lien_image");
        }
        try {
            imgChariotSurbrillance = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgGarage";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgGarage = (Element) liste.item(i);
            lienImage = imgGarage.getAttribute("lien_image");
        }        
        try {
            imgGarage = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgGarageSelection";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgGarageSelection = (Element) liste.item(i);
            lienImage = imgGarageSelection.getAttribute("lien_image");
        }        
        try {
            imgGarageSelection = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgTapisRoulant";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgTapisRoulant = (Element) liste.item(i);
            lienImage = imgTapisRoulant.getAttribute("lien_image");
        }        
        try {
            imgTapisRoulant = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgToboggan";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgToboggan = (Element) liste.item(i);
            lienImage = imgToboggan.getAttribute("lien_image");
        }        
        try {
            imgToboggan = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgDebutCheminPorteurSelection";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgDebutCheminPorteurSelection = (Element) liste.item(i);
            lienImage = imgDebutCheminPorteurSelection.getAttribute("lien_image");
        }        
        try {
            imgDebutCheminPorteurSelection = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgDebutTapisRoulant";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgDebutTapisRoulant = (Element) liste.item(i);
            lienImage = imgDebutTapisRoulant.getAttribute("lien_image");
        }        
        try {
            imgDebutTapisRoulant = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgDebutTapisRoulantSurbrillance";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgDebutTapisRoulantSurbrillance = (Element) liste.item(i);
            lienImage = imgDebutTapisRoulantSurbrillance.getAttribute("lien_image");
        }        
        try {
            imgDebutTapisRoulantSurbrillance = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgFinToboggan";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgFinToboggan = (Element) liste.item(i);
            lienImage = imgFinToboggan.getAttribute("lien_image");
        }        
        try {
            imgFinToboggan = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgMenuChariotManuel";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgMenuChariotManuel = (Element) liste.item(i);
            lienImage = imgMenuChariotManuel.getAttribute("lien_image");
        }        
        try {
            imgMenuChariotManuel = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgMenuChariotManuel_ChargerBagage";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgMenuChariotManuel_ChargerBagage = (Element) liste.item(i);
            lienImage = imgMenuChariotManuel_ChargerBagage.getAttribute("lien_image");
        }        
        try {
            imgMenuChariotManuel_ChargerBagage = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgMenuChariotManuel_DechargerBagage";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgMenuChariotManuel_DechargerBagage = (Element) liste.item(i);
            lienImage = imgMenuChariotManuel_DechargerBagage.getAttribute("lien_image");
        }        
        try {
            imgMenuChariotManuel_DechargerBagage = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
        
        tag = "imgMenuChariotManuel_Direction";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        for (int i = 0; i < liste.getLength(); i++) {
            Element imgMenuChariotManuel_Direction = (Element) liste.item(i);
            lienImage = imgMenuChariotManuel_Direction.getAttribute("lien_image");
        }        
        try {
            imgMenuChariotManuel_Direction = ImageIO.read(new File(lienImage)); 
        } catch (IOException e) {
            System.out.println("Erreur de lecture de l'image.");
        }
        
                
        tag = "listImgEtincelleD";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        if(liste.getLength()!=1){
            return ElementNoyau.PARSE_ERROR;
        }        
        Element listEtincelleD = (Element) liste.item(0);
        tag = "imgEtincelle";
        NodeList noeud = listEtincelleD.getElementsByTagName(tag);        
        for (int i = 0; i < noeud.getLength(); i++) {
            Element etincelle = (Element) noeud.item(i);
            lienImage = etincelle.getAttribute("lien_image");        
            try {
                BufferedImage imgEtincelle = ImageIO.read(new File(lienImage)); 
                listImgEtincelleD.add(imgEtincelle);
            } catch (IOException e) {
                System.out.println("Erreur de lecture de l'image.");
            }        
        }
        
        
        tag = "listImgEtincelleG";
        liste = noeudDOMRacine.getElementsByTagName(tag);        
        if(liste.getLength()!=1){
            return ElementNoyau.PARSE_ERROR;
        }        
        Element listEtincelleG = (Element) liste.item(0);
        tag = "imgEtincelle";
        noeud = listEtincelleG.getElementsByTagName(tag);        
        for (int i = 0; i < noeud.getLength(); i++) {
            Element etincelle = (Element) noeud.item(i);
            lienImage = etincelle.getAttribute("lien_image");        
            try {
                BufferedImage imgEtincelle = ImageIO.read(new File(lienImage)); 
                listImgEtincelleG.add(imgEtincelle);
            } catch (IOException e) {
                System.out.println("Erreur de lecture de l'image.");
            }        
        }
        
        // On ajoute les éléments à la map
        mapImages.put("vue.VueIntersection", imgIntersection);
        mapImages.put("vue.VueIntersectionSelection", imgIntersectionSelection);
        mapImages.put("vue.VueRail", imgRail);
        mapImages.put("vue.VueBagage", imgBagage);
        mapImages.put("vue.VueChariot", imgChariot);
        mapImages.put("vue.VueChariotSelection", imgChariotSelection);
        mapImages.put("vue.VueChariotSurbrillance", imgChariotSurbrillance);
        mapImages.put("vue.VueVoieGarage", imgGarage);
        mapImages.put("vue.VueVoieGarageSelection", imgGarageSelection);
        mapImages.put("vue.VueTapisRoulant", imgTapisRoulant);
        mapImages.put("vue.VueToboggan", imgToboggan);
        mapImages.put("vue.VueIntersectionFinToboggan", imgFinToboggan);
        mapImages.put("vue.VueIntersectionCheminPorteurSelection", imgDebutCheminPorteurSelection);
        mapImages.put("vue.VueIntersectionTapisRoulant", imgDebutTapisRoulant);
        mapImages.put("vue.VueIntersectionTapisRoulantSurbrillance", imgDebutTapisRoulantSurbrillance);
        mapImages.put("vue.VueMenuChoixDirection", imgMenuChariotManuel);
        mapImages.put("vue.VueChoixDirectionCharger", imgMenuChariotManuel_ChargerBagage);
        mapImages.put("vue.VueChoixDirectionDecharger", imgMenuChariotManuel_DechargerBagage);
        mapImages.put("vue.VueChoixDirection", imgMenuChariotManuel_Direction);
                
        return ElementNoyau.PARSE_OK;
    }

    /**
     * @return Le dictionnaire d'images
     */
    public HashMap<String, BufferedImage> getMapImages() {
        return mapImages;
    }

    /**
     * @return La liste d'image d'étincelles de droite
     */
    public List<BufferedImage> getListImgEtincelleD () {
        return listImgEtincelleD;
    }
    
    /**
     * @return La liste d'image d'étincelles de gauche
     */
    public List<BufferedImage> getListImgEtincelleG() {
        return listImgEtincelleG;
    }
}
