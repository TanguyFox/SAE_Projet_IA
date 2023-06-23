package sae.solution_perso;

import sae.distance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class MainSAE5 {

    public static int nbCouleurs = 20;

    public static Map<Integer, Integer> getMapColors(BufferedImage img) {
        TreeMap<Integer, Integer> res = new TreeMap<>();

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
    private static List<List<Integer>> splitColorMap(Map<Integer, Integer> colorMap, int numParts) {
        // Convertir la HashMap en une liste de paires (couleur (getRGB), nombre d'occurrences)
        List<Map.Entry<Integer, Integer>> colorList = new ArrayList<>(colorMap.entrySet());

        // Trier la liste de couleurs en fonction du nombre d'occurrences (ordre décroissant)
        colorList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Diviser la liste triée en 'numParts' parties égales (ou presque égales)
        List<List<Integer>> colorParts = new ArrayList<>();
        int colorsPerPart = colorList.size() / numParts;
        int remainingColors = colorList.size() % numParts;
        int startIndex = 0;

        for (int i = 0; i < numParts; i++) {
            int partSize = colorsPerPart;
            if (remainingColors > 0) {
                partSize++;
                remainingColors--;
            }

            List<Integer> part = new ArrayList<>();
            for (int j = startIndex; j < startIndex + partSize; j++) {
                part.add(colorList.get(j).getKey());
            }

            colorParts.add(part);
            startIndex += partSize;
        }

        return colorParts;
    }

    private static Color calculateAverageColor(List<Integer> colors) {
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;


        for (int rgb : colors) {
            Color color = new Color(rgb);
            totalRed += color.getRed();
            totalGreen += color.getGreen();
            totalBlue += color.getBlue();
        }

        int avgRed = totalRed / colors.size();
        int avgGreen = totalGreen / colors.size();
        int avgBlue = totalBlue / colors.size();

        return new Color(avgRed, avgGreen, avgBlue);
    }

    public static int[] getColorsArray(List<List<Integer>> colorParts){
        int[] colors = new int[colorParts.size()];
        for (int i = 0; i < colorParts.size(); i++) {
            colors[i] = calculateAverageColor(colorParts.get(i)).getRGB();
        }
        return colors;
    }


    public static void main(String[] args) throws IOException {
        // creation des bufferedImage
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // recuperation des couleurs utiliser ainsi que leurs nombres de fois dans img1
        Map<Integer, Integer> map = getMapColors(img1);
        Set<Integer> keys = map.keySet();


        List<List<Integer>> colorParts = splitColorMap(map, nbCouleurs);

        int[] colors = getColorsArray(colorParts);


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
        ImageIO.write(img2, "jpg", new File("test_image/resultSAE5.jpg"));

    }


}

