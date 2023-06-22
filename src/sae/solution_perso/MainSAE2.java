package sae.solution_perso;

import sae.distance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainSAE2 {


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
                System.out.println("color : "+pixel);
                res.put(pixel, nb);
            }
        }
        return res;

    }
    // methode recursive pour recuperé les ieme couleurs les plus utilisés de la map
    public static int[] getMaxColorsUsed (int nb, Map<Integer, Integer> map, int[] res) {
        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = map.keySet();
        Random r = new Random();
        int kmax = (int) keys.toArray()[r.nextInt(keys.size())];
        /*int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int c = map.get(k);
            if (c > max) {
                max = c;
                kmax = k;
            }
        }*/
        res[res.length-nb] = kmax;
        map.remove(kmax);
        return getMaxColorsUsed(nb-1, map, res);

    }

    public static void main(String[] args) throws IOException {
        // definition du nombre de couleurs max
        int nbCouleurs = 10;

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
        Map<Integer, Integer> map = getMapColors(img1);

        Set<Integer> keys = map.keySet();
        System.out.println("Nombre de couleurs : " + keys.size());

        int[] colors = getMaxColorsUsed(nbCouleurs, map, new int[nbCouleurs]);


        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int rgb = img1.getRGB(i, j);

                double[] distances = new double[nbCouleurs];
                for (int k = 0; k < distances.length; k++) {
                    distances[k] = distance.distanceCouleur(new Color(rgb),new Color(colors[k]));
                }

                double distancesMin = distances[0];
                int colorMin = colors[0];
                for (int k = 1; k < distances.length; k++) {
                    if(distances[k] < distancesMin) {
                        distancesMin = distances[k];
                        colorMin = colors[k];
                    }
                }
                img2.setRGB(i, j, colorMin);
            }
        }
        ImageIO.write(img2, "jpg", new File("images_etudiants/resultSAE2.jpg"));

    }


}

