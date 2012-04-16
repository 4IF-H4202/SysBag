package vue;

import bibliothequesTiers.Point;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import noyau.Chariot;
import noyau.Chemin;
import noyau.Intersection;

/**Classe gérant l'affichage des choix de direction.
 */
public class VueChoixDirection extends VueElementPonctuel {
    /**
     * @attribute Chemin correspondant au choix de direction.
     */
    protected Chemin chemin;
    
    /**
     * @attribute Vue gérant l'affichage du menu des choix de direction.
     */    
    protected VueMenuChoixDirection vueMenu;
    
    /**
     * @attribute Chariot sur lequel s'applique le choix de direction.
     */
    protected Chariot chariot;
    
    private Point vraiPosition; // Position de la flêche de choix de direction sur l'image, en pixel.

    /**Constructeur de la classe VueChoixDirection
     * @param chariot Chariot sur lequel s'applique le choix de direction.
     * @param vueMenu Vue gérant l'affichage du menu des choix de direction.
     * @param chemin Chemin correspondant au choix de direction.
     * @param vueAeroport Objet VueAeroport 
     * @param type
     */
    public VueChoixDirection(Chariot chariot, VueMenuChoixDirection vueMenu, Chemin chemin,
                             VueAeroport vueAeroport, String type) {
        super(chariot.getCoordonneesAbsolues(), -Math.toRadians(180) + chemin.getTheta(), vueAeroport);
        this.chemin = chemin;
        this.vueMenu = vueMenu;
        this.vueMenu.getListeVuesChoix().add(this);        
        this.nomImg = this.getClass().getName();
        this.vraiPosition = new Point(vueAeroport.convertirEnPixel(position).getX()+ 120*Math.cos(Math.toRadians(180)+inclinaison)*vueAeroport.getCoefReducteur()/327,
            vueAeroport.convertirEnPixel(position).getY()+120*Math.sin(Math.toRadians(180)+inclinaison)*vueAeroport.getCoefReducteur()/327);

        System.out.println("La");
        System.out.println("Position base : "+vueAeroport.convertirEnPixel(position).getX()+" ; "+vueAeroport.convertirEnPixel(position).getY());
        // Si le chemin correspond à un tapis roulant
        if (type.equals("tapisRoulant"))
        {
            nomImg+="Charger";
            this.inclinaison += Math.toRadians(180);            
            this.vraiPosition = new Point(vueAeroport.convertirEnPixel(position).getX()- 120*Math.cos(inclinaison)*vueAeroport.getCoefReducteur()/327,
                vueAeroport.convertirEnPixel(position).getY()-120*Math.sin(inclinaison)*vueAeroport.getCoefReducteur()/327);
            System.out.println("Prout");
            System.out.println("Inclinaison : "+inclinaison);
            //System.out.println("ajout abscisse : "+inclinaison);
        }
        // Si le chemin correspond à un toboggan
        else if (type.equals("toboggan"))
        {
            nomImg+="Decharger";
           // this.inclinaison += Math.toRadians(180);
            this.vraiPosition = new Point(vueAeroport.convertirEnPixel(position).getX()- 120*Math.cos(inclinaison)*vueAeroport.getCoefReducteur()/327,
                vueAeroport.convertirEnPixel(position).getY()-120*Math.sin(inclinaison)*vueAeroport.getCoefReducteur()/327);
            System.out.println("Pwet");
        }
        nomImgSelection = nomImg;
        nomImgSurbrillance = nomImg;
        this.chariot=chariot;      
        this.vraiPosition.setX(this.vraiPosition.getX()-vueAeroport.getX0());
        this.vraiPosition.setY(this.vraiPosition.getY()-vueAeroport.getY0());
    }

    @Override
    public void actualiserInfo() {
    }

    @Override
    public Object getElement() {
        return chemin;
    }

    /**Supprime les vues du menu et des choix de direction associés.
     */
    public void supprimerVueMenu(){
        for (int i = 0; i < vueMenu.getListeVuesChoix().size(); i++) {
                 if (this != vueMenu.getListeVuesChoix().get(i));
                     vueAeroport.supprimerVueElementsClickable(vueMenu.getListeVuesChoix().get(i));
        }
        vueAeroport.supprimerVueElementsClickable(vueMenu);
        vueAeroport.supprimerVueElementsClickable(this);
    }
    
    public Chariot getChariot(){
        return this.chariot;
    }


    /**Indique si les coordonnées en paramètre correspondent à l'élément affiché.
     * @param x Abscisse en pixel.
     * @param y Ordonnée en pixel.
     * @return true s'il y a correspondance, et false dans le cas contraire.
     */
    public boolean EstClicke(Integer x, Integer y) { 
        System.out.println("Clic : "+x+"; "+y);
        System.out.println("Vrai Position : "+vraiPosition.getX()+"; "+vraiPosition.getY());
        return (Math.pow(vraiPosition.getX()-x,2)+Math.pow(vraiPosition.getY()-y,2) <= Math.pow(50*2.5*vueAeroport.getCoefReducteur()/327, 2) ); // TODO : modifier avec un coefficient
    }
        
    /**Dessine sur l'objet Graphics en paramètre l'élément.
     * @param g objet Graphics sur lequel l'élément sera dessiné.
     */
    @Override
    public void Dessiner(Graphics g) {
        actualiserInfo();
        Point p_pixel = vueAeroport.convertirEnPixel(position);
        p_pixel.setX(p_pixel.getX()-vueAeroport.getX0());
        p_pixel.setY(p_pixel.getY()-vueAeroport.getY0());
        String nomImageADessiner = nomImg;
        if (surbrillance){
            nomImageADessiner = nomImgSurbrillance;
        }
     
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();        
        g2d.rotate(-1.57075+inclinaison, p_pixel.getX(), p_pixel.getY());
        
        BufferedImage imageADessiner = vueAeroport.getSkin().getMapImages().get(nomImageADessiner);
        BufferedImage imageADessinerScaled = scale(imageADessiner, 2*vueAeroport.getCoefReducteur()/imageADessiner.getWidth());
        this.longueurImgPixel = imageADessinerScaled.getWidth();
        this.largeurImgPixel = imageADessinerScaled.getHeight();
        g.drawImage(imageADessiner, (int) ((p_pixel.getX()- longueurImgPixel/2)), (int)( (p_pixel.getY()- largeurImgPixel/2))
                    , (int)(longueurImgPixel), (int)(largeurImgPixel), null) ;        
        g2d.setTransform(originalTransform);
    }
}