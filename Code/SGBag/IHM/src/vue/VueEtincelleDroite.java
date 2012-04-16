package vue;

import bibliothequesTiers.Point;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.List;

import noyau.Etincelle;

/**Classe gérant l'affichage des étincelles de droite du chariot.
 */
public class VueEtincelleDroite extends VueEtincelle {
    /**Constructeur de la classe VueEtincelleDroite
     * @param etincelle Objet à afficher.
     * @param vueAeroport Vue gérant l'objet vueEtincelleDroite
     */
    public VueEtincelleDroite(Etincelle etincelle, VueAeroport vueAeroport) {
        super(etincelle, vueAeroport);
        listImgEtincelle = vueAeroport.getSkin().getListImgEtincelleD();
    }
}
