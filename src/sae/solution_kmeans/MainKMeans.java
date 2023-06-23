package sae.solution_kmeans;

import sae.MapColors;
import sae.MaxColorsUsed;
import sae.distance;
import sae.solution_perso.MainSAE3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainKMeans {

    public static int nbCouleurs = 5;

    public static int seuil = 100;

    // retourne l'indice du tableau des couleurs centroides dont la distance est la plus proche de couleur
    private static int getIndiceProche(int couleur, int[] couleurs) {
        double distMin = Double.MAX_VALUE;
        int indiceMin = -1;

        for (int i = 0; i < couleurs.length; i++) {
            double dist = distance.distanceCouleur(new Color(couleur), new Color(couleurs[i]));
            if (dist < distMin) {
                distMin = dist;
                indiceMin = i;
            }
        }
        return indiceMin;
    }

    public static int setCentroide(List<Integer> groupe) {
        double res = 0;
        for (int c : groupe) {
            res += c;
        }
        return (int) Math.round(res/groupe.size());
    }

    private static boolean TabComparator(int[] tab1, int[] tab2) {
        boolean res = true;
        for (int i = 0; i < tab1.length; i++) {
            if (Math.abs(tab1[i] - tab2[i]) > seuil) {
                System.out.println(Math.abs(tab1[i] - tab2[i]));
                res = false;
                break;
            }
        }
        if (res) {
            System.out.println("ARRET");
        }
        return res;
    }


    private static void dessinerImage(BufferedImage img1, BufferedImage img2, int[] colors) throws IOException {
        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int pixel = img1.getRGB(i, j);
                int color = 0;
                double distanceMin = Double.MAX_VALUE;
                for (int c : colors) {
                    double dist = distance.distanceCouleur(new Color(c), new Color(pixel));
                    if (dist < distanceMin) {
                        distanceMin = dist;
                        color = c;
                    }
                }
                img2.setRGB(i, j, color);
            }
        }
        ImageIO.write(img2, "jpg", new File("images_etudiants/resultKMeans.jpg"));
    }

    private static int[] addCouleur(int[] couleurs, Set<Integer> keys) {
        int[] res = new int[couleurs.length+1];
        for (int i = 0; i < couleurs.length; i++) {
            res[i] = couleurs[i];
        }
        Random r = new Random();
        res[couleurs.length] = (int) keys.toArray()[r.nextInt(keys.size())];
        nbCouleurs++;
        return res;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs d'img1
        Map<Integer, Integer> map = MapColors.getArrayMapColors(img1);
        Set<Integer> keys = map.keySet();

        //initialisation des couleurs centroides
//        int[] couleurs = new int[nbCouleurs];
//        for (int i = 0; i < couleurs.length; i++) {
//            Random r = new Random();
//            couleurs[i] = (int) keys.toArray()[r.nextInt(keys.size())];
//        }

        int[] couleurs = MaxColorsUsed.getMaxColorsUsed3(nbCouleurs, map, new int[nbCouleurs], map.size(), nbCouleurs);


        boolean stop = false;
        int compteur = 0;
        while (!stop && compteur < 10000) {
            compteur++;
            // initialisations des groupes
            List<List<Integer>> groupes = new ArrayList<>();
            for (int i = 0; i < nbCouleurs; i++) {
                groupes.add(new ArrayList<Integer>());
            }

            // implementations des groupes en fonctions des distances couleur-centroide
            for (int key : keys) {
                int i = getIndiceProche(key, couleurs);
                groupes.get(i).add(key);
            }

            int[] res = couleurs.clone();

            // modifications des couleurs centroides en fonction des nouveau groupes
            for (int i = 0; i < couleurs.length; i++) {
                couleurs[i] = setCentroide(groupes.get(i));
            }

            if ((compteur)%100 == 0) {
                System.out.println("iteration : " + compteur);
            }

            //condition d'arret
            stop = TabComparator(res, couleurs);
        }

        //dessine l'image
        dessinerImage(img1, img2, couleurs);

    }
}
