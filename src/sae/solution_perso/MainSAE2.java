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

import static sae.MapColors.getArrayMapColors;
import static sae.MaxColorsUsed.getMaxColorsUsed2;

public class MainSAE2 {


    public static void main(String[] args) throws IOException {
        // definition du nombre de couleurs max
        int nbCouleurs = 5;

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
        Map<Integer, Integer> map = getArrayMapColors(img1);

        Set<Integer> keys = map.keySet();
        System.out.println("Nombre de couleurs : " + keys.size());

        int[] colors = getMaxColorsUsed2(nbCouleurs, map, new int[nbCouleurs]);


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

