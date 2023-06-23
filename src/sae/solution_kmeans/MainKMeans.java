package sae.solution_kmeans;

import javax.imageio.ImageIO;

import sae.Distance;
import sae.MapColors;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.*;

import static sae.solution_kmeans.KMeans.dessinerImage;
import static sae.solution_kmeans.KMeans.getColorsWithKMeans;

public class MainKMeans {

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 2) {
            System.out.println("Usage : java MainKMeans file-to-img nbColor");
            return;
        }


        int nbCouleurs = Integer.parseInt(args[1]);

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File(args[0]));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs d'img1
        Map<Integer, Integer> map = MapColors.getHashMapColors(img1);
        Set<Integer> keys = map.keySet();

        Instant debut = Instant.now();

        //initialisation des couleurs centroides
        int[] couleurs = getColorsWithKMeans(nbCouleurs, keys);

        //dessine l'image
        dessinerImage(img1, img2, couleurs);

        long duree = Duration.between(debut, Instant.now()).toMillis();

        System.out.println("Image calculée en :" + duree + " ms");
    }
}