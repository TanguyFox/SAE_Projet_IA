package sae.solution_kmeans;

import sae.Distance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class KMeans {

    // retourne l'indice du tableau des couleurs centroides dont la distance est la plus proche de couleur
    public static int getIndiceProche(int couleur, int[] couleurs) {
        double distMin = Double.MAX_VALUE;
        int indiceMin = -1;

        for (int i = 0; i < couleurs.length; i++) {
            double dist = Distance.distanceCouleur(new Color(couleur), new Color(couleurs[i]));
            if (dist < distMin) {
                distMin = dist;
                indiceMin = i;
            }
        }
        return indiceMin;
    }

    // permet de redefinir le centroid lié au groupe donnée
    public static int setCentroide(List<Integer> groupe) {
        double r = 0;
        double g = 0;
        double b = 0;
        for (int c : groupe) {
            r += Distance.R(c);
            g += Distance.G(c);
            b += Distance.B(c);

        }
        r /= groupe.size();
        g /= groupe.size();
        b /= groupe.size();

        Color c = new Color((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
        return c.getRGB();

    }

    // permet de tester si le tableau des centroid achangé (condition d'arret)
    public static boolean TabComparator(int[] tab1, int[] tab2) {
        boolean res = true;
        for (int i = 0; i < tab1.length; i++) {
            if (tab1[i] != tab2[i]) {
                // System.out.println(Math.abs(tab1[i] - tab2[i]));
                res = false;
                break;
            }
        }
        if (res) {
            System.out.println("ARRET");
        }
        return res;
    }

    // methode permettant d'ecrire l'image i dans l'image 2 à partir des couleurs données
    public static void dessinerImage(BufferedImage img1, BufferedImage img2, int[] colors) throws IOException {
        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int pixel = img1.getRGB(i, j);
                int color = 0;
                double distanceMin = Double.MAX_VALUE;
                for (int c : colors) {
                    double dist = Distance.distanceCouleur(new Color(c), new Color(pixel));
                    if (dist < distanceMin) {
                        distanceMin = dist;
                        color = c;
                    }
                }
                img2.setRGB(i, j, color);
            }
        }
        ImageIO.write(img2, "jpg", new File("test_image/resultKMeans.jpg"));
    }

    // permets de retourner les couleurs calculées à partir de l'algorithme de KMeans
    public static int[] getColorsWithKMeans (int nb, Set<Integer> keys) {
        int[] couleurs = new int[nb];
        for (int i = 0; i < nb; i++) {
            Random r = new Random();
            couleurs[i] = (int) keys.toArray()[r.nextInt(keys.size())];
        }

        boolean stop = false;
        while (!stop) {
            // initialisations des groupes
            List<List<Integer>> groupes = new ArrayList<>();
            for (int i = 0; i < nb; i++) {
                groupes.add(new ArrayList<Integer>());
            }

            // implementations des groupes en fonctions des distances couleur-centroide
            for (int key : keys) {
                int i = getIndiceProche(key, couleurs);
                groupes.get(i).add(key);
            }

            int[] res = couleurs.clone();

            // modifications des couleurs centroides en fonction des nouveau groupes
            for (int i = 0; i < nb; i++) {
                couleurs[i] = setCentroide(groupes.get(i));
            }
            //condition d'arret
            stop = TabComparator(res, couleurs);
        }
        return couleurs;
    }
}
