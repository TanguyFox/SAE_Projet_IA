package sae;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapColors {

    public static Map<Integer, Integer> getArrayMapColors(BufferedImage img) {
        Map<Integer, Integer> res = new HashMap<Integer, Integer>();

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

    public static Map<Integer, Integer> getTreeMapColors(BufferedImage img) {
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
}
