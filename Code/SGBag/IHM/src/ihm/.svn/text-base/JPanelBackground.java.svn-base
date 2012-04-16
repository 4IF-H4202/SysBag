package ihm;

import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class JPanelBackground extends JPanel {

    @SuppressWarnings("compatibility:7727597063969333885")
    protected static final long serialVersionUID = 1L;
    protected transient Image imageBackground;

    public JPanelBackground(String lienImage) {
        super();
        try {
            imageBackground = ImageIO.read(new File(lienImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public JPanelBackground(Image image) {
        super();
        imageBackground = image;
    }
    
    public void paintComponent(Graphics g)
    {
        // Draw the previously loaded image to Component.
        Dimension dimPanel = this.getSize();
        BufferedImage buf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        /* On dessine sur le Graphics de l'image bufferisée. */
        Graphics2D g2D = buf.createGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2D.drawImage(imageBackground, 0, 0, this.getWidth(), this.getHeight(), null);
        g2D.dispose();

        g.drawImage(buf, 0, 0, null) ;
    }
    
    public void resizeToImageSize() {
        this.setSize(imageBackground.getWidth(null), imageBackground.getHeight(null));
    }

    public void setImageBackground(String lienImage) {
        try {
            this.imageBackground = ImageIO.read(new File(lienImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = this.getGraphics();
        update(g);
    }
}
