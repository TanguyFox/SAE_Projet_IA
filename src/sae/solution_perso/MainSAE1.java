package sae.solution_perso;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainSAE1 {


    public static Map<Integer, Integer> getMapColors(BufferedImage img) {
        Map<Integer, Integer> res = new HashMap<Integer, Integer>();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int pixel = img.getRGB(i, j);
                int nb;
                if (res.containsKey(pixel)) {
                    nb = res.get(pixel) + 1;
                } else {
                    nb = 1;
                }
                res.put(pixel, nb);
            }
        }
        return res;

    }

    public static void main(String[] args) throws IOException {
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < img1.getHeight(); i++) {

        }




    }


}
