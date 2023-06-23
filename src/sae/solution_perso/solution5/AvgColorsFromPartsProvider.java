package sae.solution_perso.solution5;

import sae.MapColors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AvgColorsFromPartsProvider {

    //Attribute that contains the colors of the original image
    private Map<Integer, Integer> imgColors;

    public AvgColorsFromPartsProvider(BufferedImage img){
        this.imgColors = MapColors.getTreeMapColors(img);
    }

    /**
     * Méthode qui sépare la HashMap en numParts attributes
     * @param numParts le nombre de partie à découpé dans la HashMap
     * @return La liste des parties
     */
    public List<List<Integer>> splitColorMap(int numParts) {
        // Convertir la HashMap en une liste de paires (couleur (getRGB), nombre d'occurrences)
        List<Map.Entry<Integer, Integer>> colorList = new ArrayList<>(this.imgColors.entrySet());

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


    /**
     * Méthode qui récupère la couleur moyenne d'une partie
     * @param colors la partie
     * @return la couleur moyenne de la partie
     */
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

    /**
     * Méthode qui retourne le tableau des couleurs moyenne des parties
     * @param colorParts la liste des parties
     * @return Le tableau de la couleur moyenne de chaque partie
     */
    public int[] getColorsArray(List<List<Integer>> colorParts){
        int[] colors = new int[colorParts.size()];
        for (int i = 0; i < colorParts.size(); i++) {
            colors[i] = calculateAverageColor(colorParts.get(i)).getRGB();
        }
        return colors;
    }

}
