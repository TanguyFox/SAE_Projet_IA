package sae.solution_perso.solution4;

import sae.Distance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;


import javax.imageio.ImageIO;



public class MainSAE4 {

    public static int nbCouleurs = 5;

    public static void main(String[] args) throws IOException {
        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_diverses_small/peinture/Klimt_small.png"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
        MaxColorsFromPartsProvider mcfp = new MaxColorsFromPartsProvider(img1);

        int[] colors = mcfp.getMaxColorsFromParts(nbCouleurs, new int[nbCouleurs], mcfp.getColors().size(), nbCouleurs);

        Instant debut = Instant.now();
        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int rgb = img1.getRGB(i, j);

                double[] distances = new double[nbCouleurs];
                for (int k = 0; k < distances.length; k++) {
                    distances[k] = Distance.distanceCouleur(new Color(rgb),new Color(colors[k]));
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
        ImageIO.write(img2, "jpg", new File("test_image/resultSAE4.jpg"));

        long duree = Duration.between(debut, Instant.now()).toMillis();
        System.out.println("Image calculée en :" + duree + " ms");
    }


}

