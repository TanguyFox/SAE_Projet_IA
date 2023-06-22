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


    public static Map<Color, Boolean> palette = new HashMap<>();


    public static int[] getMaxColorsUsed(int nb, Map<Integer, Integer> map, int[] res) {
        if (nb == 0) {
            return res;
        }

        int max = -1;
        int kmax = 0;
        Color color = null;

        Set<Integer> keys = map.keySet();
        for (int k : keys) {
            int c = map.get(k);
            if (c > max) {
                double distanceMin = Double.MAX_VALUE;
                Color colorMin = null;
                for (Color cl : palette.keySet()) {
                    if (!palette.get(cl)) {
                        double distance = sae.distance.distanceCouleur(cl, new Color(k));
                        if (distance < distanceMin) {
                            distanceMin = distance;
                            colorMin = cl;
                        }
                    }
                }
                if (colorMin != null) {
                    palette.put(colorMin, true);
                    max = c;
                    kmax = k;
                    color = colorMin;
                }
            }
        }
        if (color != null) {
            System.out.println("couleur : " +color.getRGB());
            res[res.length - nb] =  color.getRGB();
            map.remove(kmax);
        }

        return getMaxColorsUsed(nb - 1, map, res);
    }

    public static void main(String[] args) throws IOException {
        // definition du nombre de couleurs max
        int nbCouleurs = 4;

        palette.put(Color.RED, false);
        palette.put(Color.GREEN, false);
        palette.put(Color.BLUE, false);
        palette.put(Color.YELLOW, false);
        palette.put(Color.ORANGE, false);
        palette.put(Color.WHITE, false);

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

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
