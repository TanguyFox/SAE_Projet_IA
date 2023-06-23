package tp_prepa_SAE;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q2 {

    public static void main(String[] args) throws IOException {

        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);


        for (int i = 0; i < img1.getWidth(); i++) {

            for (int j = 0; j < img1.getHeight(); j++) {

                int rgb = img1.getRGB(i, j);
                img2.setRGB(i, j, rgb);

            }
        }

        ImageIO.write(img2, "jpg", new File("images_etudiants/testQ2.jpg"));


    }
}
