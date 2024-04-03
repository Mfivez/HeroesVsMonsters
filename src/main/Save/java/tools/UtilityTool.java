package tools;

import java.awt.*;
import java.awt.image.BufferedImage;



public class UtilityTool {


    /**
     * Faire le scaling d'image lors de la lecture des tiles pour éviter de devoir recalculer le tiles à chaque fois
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {


        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }


}
