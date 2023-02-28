package Project4;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ToBufferedImage {

    public BufferedImage imageToBufferedImage(Image img){
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bi;

    }

}
