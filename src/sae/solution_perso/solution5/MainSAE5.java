package sae.solution_perso.solution5;

import sae.Distance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;


public class MainSAE5 {

    public static void main(String[] args) throws IOException {

        if(args.length != 2) {
            System.out.println("Usage : java MainSAE5 file-to-img nbColor");
            return;
        }


        int nbCouleurs = Integer.parseInt(args[1]);

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File(args[0]));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
       AvgColorsFromPartsProvider acfpp = new AvgColorsFromPartsProvider(img1);


        List<List<Integer>> colorParts = acfpp.splitColorMap(nbCouleurs);

        int[] colors = acfpp.getColorsArray(colorParts);

        Instant debut = Instant.now();

        //récupération des distances entre la couleur d'un pixel et les couleurs du tableau
        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int rgb = img1.getRGB(i, j);

                double[] distances = new double[nbCouleurs];

                //Recherche de la distance minimal et récupération de la couleur associée
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
        ImageIO.write(img2, "jpg", new File("test_image/resultSAE5.jpg"));

        long duree = Duration.between(debut, Instant.now()).toMillis();
        System.out.println("Image calculée en :" + duree + " ms");
    }


}

