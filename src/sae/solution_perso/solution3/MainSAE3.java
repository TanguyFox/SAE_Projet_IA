package sae.solution_perso.solution3;

import sae.Distance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class MainSAE3 {

    public static void main(String[] args) throws IOException {
        // definition du nombre de couleurs max
        int nbCouleurs = 60;

        Map<Color, Boolean> palette = new HashMap<>();



        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_diverses_small/animaux/ours.png"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
       ColorsFromPaletteProvider cfpp = new ColorsFromPaletteProvider(img1);

        int[] colors = cfpp.getColorsFromPalette(nbCouleurs, new int[nbCouleurs]);
        System.out.println(Arrays.toString(colors));


        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int rgb = img1.getRGB(i, j);

                double[] distances = new double[nbCouleurs];
                for (int k = 0; k < distances.length; k++) {
                    distances[k] = Distance.distanceCouleur(new Color(rgb), new Color(colors[k]));
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
        ImageIO.write(img2, "jpg", new File("test_image/resultSAE3.jpg"));

    }
}
