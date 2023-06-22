package sae.solution_perso;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import  sae.distance;

public class MainSAE3 {

    public static int nbCouleurs = 5;

    public static Map<Integer, Integer> getMapColors(BufferedImage img) {
        Map<Integer, Integer> res = new TreeMap<>();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int pixel = img.getRGB(i, j);
                int nb;
                if (res.containsKey(pixel)) {
                    nb = res.get(pixel) + 1;
                } else {
                    nb = 1;
                }
                res.put(pixel, nb);
            }
        }
        return res;

    }
    // methode recursive pour recuperé les ieme couleurs les plus utilisés de la map
    public static int[] getMaxColorsUsed (int nb, Map<Integer, Integer> map, int[] res, int size) {
        if(nb == 0) {
            return res;
        }
        System.out.println("---------");
        System.out.println("taille : " + size);
        Set<Integer> keys = map.keySet();
        List<Integer> listKey = new ArrayList<>(keys);
        int indiceCouleur = res.length-nb;
        System.out.println("indice couleur : " + indiceCouleur);
        int indicePossible = size/nbCouleurs;
        System.out.println("nombre d indice possible : " + indicePossible);
        int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int i = listKey.indexOf(k);
            if (i >= indicePossible*indiceCouleur && i < indicePossible*indiceCouleur+indicePossible) {
                int c = map.get(k);
                if (c > max) {
                    max = c;
                    kmax = k;
                    System.out.println(i);
                    System.out.println(max);
                }
            }
        }
        System.out.println("kmax : " + kmax);
        System.out.println("R: " + distance.R(kmax));
        System.out.println("R: " + distance.G(kmax));
        System.out.println("R: " + distance.B(kmax));


        res[indiceCouleur] = kmax;
        map.remove(kmax);
        return getMaxColorsUsed(nb-1, map, res, size);

    }

    public static void main(String[] args) throws IOException {
        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(),  BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
        Map<Integer, Integer> map = getMapColors(img1);
        Set<Integer> keys = map.keySet();
        System.out.println("Nombre de couleurs : " + keys.size());
        for (int key : keys) {
            System.out.println(key);
        }

        int[] colors = getMaxColorsUsed(nbCouleurs, map, new int[nbCouleurs], map.size());


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
        ImageIO.write(img2, "jpg", new File("images_etudiants/resultSAE3.jpg"));

    }


}
