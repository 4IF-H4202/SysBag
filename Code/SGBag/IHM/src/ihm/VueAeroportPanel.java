package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import java.util.LinkedList;

import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import noyau.Aeroport;

import noyau.ElementNoyau;
import noyau.Vol;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vue.VueAeroport;

public class VueAeroportPanel extends JPanel {
    
    @SuppressWarnings("compatibility:7727597063969333885")
    private static final long serialVersionUID = 1L;
    private VueAeroport vueAeroport;                // Vue g�rant l'affichant du syst�me.
    private Aeroport aeroport;                      // Noyau du syt�me SysBag, � visualiser.
    //private BufferedImage logo;
    
    private BufferedImage image;
    private RenderingThread renderingThread;
       // buffer m�moire (2eme buffer)
    private Graphics buffer;
       // image m�moire correspondante au buffer
   class RenderingThread extends Thread {
       /**
        *  Ce thread appelle le rafraichissement de notre fen�tre 
        *  toutes les 10 milli-secondes
        */
        public void run(){
           while(true){
              try {
                 repaint(); 
                 sleep(20);
               // buffer.drawImage(logo, 0, 0, 194, 94, null);
              } catch ( Exception e ) {} 
           }
        }
     }  

    VueAeroportPanel() {
        super();
/*         try {
           //logo = ImageIO.read(new File("src\\ihm\\images\\logo_application.png"));
        } catch (IOException e) {
        } */
        //logo.setBounds(new Rectangle(-5, -5, 191, 94));
        //this.add(logo, BorderLayout.CENTER);
        renderingThread = new RenderingThread();
        renderingThread.start();
        this.setIgnoreRepaint(true);
    }

    public VueAeroport GetVueAeroport() {
        return vueAeroport;
    }
    
    // TODO : A red�finir 
    @Override
    public void paintComponent(Graphics g)
    {
        //cr�ation du buffer si il n'existe pas
        if(buffer==null){
            image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            buffer = image.getGraphics();
        }
        //on dessine sur le buffer m�moire :
        if (vueAeroport != null) {
            vueAeroport.Dessiner(buffer);
           // buffer.drawImage(logo, 0, 0, 194, 94, this);
            g.drawImage(image, 0, 0, this) ;
        }
        else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    /** Fais appel � la m�thode de aeroport pour sauvegarder chaque �l�ment du noyau
     * @param document
     * @return Element (contenant les donn�es)
     */
    public Element SauvegarderVueAeroportAPartirDeDOMXML(Document document) {
               
        Element racine = aeroport.CreerNoeudXML(document);
        if (racine == null ) {
            return null;
        }
        System.out.println("Sauvegarde effectu�e avec succ�s !");
        
        return racine;
    }


    /** Fais appel � la m�thode de aeroport pour charger chaque �l�ment du noyau, puis redessine
     *  les vues associ�s � chaque objet
     * @param racine
     * @return OK ou ERROR
     */
    public int ConstruireVueAeroportAPartirDeDOMXML(Element racine) {
              
        aeroport = new Aeroport();
        int retour = aeroport.ConstruireAPartirDeDOMXML(racine);
        if (retour != Aeroport.PARSE_OK) {
            return retour;
        }

        vueAeroport = new VueAeroport(aeroport, this.getWidth(), this.getHeight());
        
        this.Redessiner();

        return Aeroport.PARSE_OK;
    }


    /** Fais appel � la m�thode de aeroport pour charger chaque �l�ment du noyau (ainsi que leurs bagages et leurs positions),
     *  puis redessine les vues associ�s � chaque objet
     * @param racine
     * @return OK ou ERROR
     */
    public int ConstruireSimulAPartirDeDOMXML(Element racine) {
               
        int retour = aeroport.ConstruireSimulAPartirDeDOMXML(racine);
        if (retour != Aeroport.PARSE_OK) {
            return Aeroport.PARSE_ERROR;
        }
        
        this.Redessiner();

        return Aeroport.PARSE_OK;
    }
    

    public void Redessiner() {
        Graphics g = getGraphics();
        update(g);
    }
    
    public LinkedList<Vol> getVols(){
        return this.vueAeroport.getAeroport().getVols();
    }

    Aeroport getAeroport() {
        return aeroport;
    }

}
