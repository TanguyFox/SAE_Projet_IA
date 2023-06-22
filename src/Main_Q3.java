import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q3 {

    public static int[] getCouleurs(int rgb) {
        int[] res = new int[3];
        res[0] = rgb & 0xff;
        res[1] = (rgb & 0xff00) >> 8;
        res[2] = (rgb & 0xff0000) >> 16;

        return res;
    }


    public static void main(String[] args) throws IOException {

        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < img1.getWidth(); i++) {

            for (int j = 0; j < img1.getHeight(); j++) {

                int[] couleurs = getCouleurs(img1.getRGB(i, j));
                int rgb = (couleurs[0]+couleurs[1]+couleurs[2])/3;
                img2.setRGB(i, j, rgb);

            }
        }
        ImageIO.write(img2, "png", new File("images_etudiants/testQ3.png"));

    }
}
