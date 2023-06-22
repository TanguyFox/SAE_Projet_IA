package sae.solution_perso;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import sae.distance;

import javax.imageio.ImageIO;

import static sae.solution_perso.MainSAE1.getMapColors;

public class MainSAE4 {


    public static Map<Color, Boolean> getColor() {
        Map<Color, Boolean> palette = new HashMap<>();
        palette.put(Color.RED, false);
        palette.put(Color.GREEN, false);
        palette.put(Color.BLUE, false);
        palette.put(Color.YELLOW, false);
        palette.put(Color.ORANGE, false);
        palette.put(Color.WHITE, false);
        //palette.put(Color.BLACK, false);

        return palette;
    }

    public static int[] getMaxColorsUsed(int nb, Map<Integer, Integer> map, Map<Color, Boolean> palette, int[] res) {


        if (nb == 0) {
            System.out.println(res);
            for (int i : res) {
                System.out.println(new Color(i));
            }
            return res;
        }
        int max = -1;
        Set<Integer> keys = map.keySet();
        for (int k : keys) {
            double distance = Double.MAX_VALUE;
            for (Color cl : palette.keySet()) {
                double distance2 = distance;
                distance = sae.distance.distanceCouleur(cl, new Color(k));
                if (distance < distance2) {
                    if (!palette.get(cl)){
                        System.out.println("color : "+cl);
                        palette.put(cl, true);
                        System.out.println(palette.size());
                        res[res.length - nb] = cl.getRGB();
                    }

                }
            }
    }
        return getMaxColorsUsed(nb-1, map, palette, res);

}

    public static void main(String[] args) throws IOException {
        // definition du nombre de couleurs max
        int nbCouleurs = 2;

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
        Map<Integer, Integer> map = getMapColors(img1);
        Map<Color, Boolean> palette = getColor();

        Set<Integer> keys = map.keySet();
        System.out.println("Nombre de couleurs : " + keys.size());

        int[] colors = getMaxColorsUsed(nbCouleurs, map, palette, new int[nbCouleurs]);


        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int rgb = img1.getRGB(i, j);

                double[] distances = new double[nbCouleurs];
                for (int k = 0; k < distances.length; k++) {
                    distances[k] = distance.distanceCouleur(new Color(rgb), new Color(colors[k]));
                }

                double distancesMin = distances[0];
                int colorMin = colors[0];
                for (int k = 1; k < distances.length; k++) {
                    if (distances[k] < distancesMin) {
                        distancesMin = distances[k];
                        colorMin = colors[k];
                    }
                }
                img2.setRGB(i, j, colorMin);
            }
        }
        ImageIO.write(img2, "jpg", new File("images_etudiants/resultSAE4.jpg"));

    }
}
