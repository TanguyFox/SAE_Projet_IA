package sae;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MaxColorsUsed {

    // methode recursive pour recuperé les ieme couleurs les plus utilisés de la map

    public static int[] getMaxColorsUsed1 (int nb, Map<Integer, Integer> map, int[] res) {
        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = map.keySet();
        int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int c = map.get(k);
            if (c > max) {
                max = c;
                kmax = k;
            }
        }
        res[res.length-nb] = kmax;
        map.remove(kmax);
        return getMaxColorsUsed1(nb-1, map, res);

    }

    // methode recursive pour recuperé les ieme couleurs les plus utilisés de la map
    public static int[] getMaxColorsUsed2 (int nb, Map<Integer, Integer> map, int[] res) {
        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = map.keySet();
        Random r = new Random();
        int kmax = (int) keys.toArray()[r.nextInt(keys.size())];
        /*int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int c = map.get(k);
            if (c > max) {
                max = c;
                kmax = k;
            }
        }*/
        res[res.length-nb] = kmax;
        map.remove(kmax);
        return getMaxColorsUsed2(nb-1, map, res);

    }

    // methode recursive pour recuperé les ieme couleurs les plus utilisés de la map
    public static int[] getMaxColorsUsed3 (int nb, Map<Integer, Integer> map, int[] res, int size, int nbCouleurs) {
        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = map.keySet();
        List<Integer> listKey = new ArrayList<>(keys);
        int indiceCouleur = res.length-nb;
        int indicePossible = size/nbCouleurs;
        int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int i = listKey.indexOf(k);
            if (i >= indicePossible*indiceCouleur && i < indicePossible*indiceCouleur+indicePossible) {
                int c = map.get(k);
                if (c > max) {
                    max = c;
                    kmax = k;
                }
            }
        }
        res[indiceCouleur] = kmax;
        map.remove(kmax);
        return getMaxColorsUsed3(nb-1, map, res, size, nbCouleurs);

    }

    public static int[] getMaxColorsUsed4(int nb, Map<Integer, Integer> map, int[] res, Map<Color, Boolean> palette) {
        if (nb == 0) {
            return res;
        }

        int max = -1;
        int kmax = 0;
        Color color = null;

        Set<Integer> keys = map.keySet();
        for (int k : keys) {
            int c = map.get(k);
            if (c > max) {
                double distanceMin = Double.MAX_VALUE;
                Color colorMin = null;
                for (Color cl : palette.keySet()) {
                    if (!palette.get(cl)) {
                        double dist = distance.distanceCouleur(cl, new Color(k));
                        if (dist < distanceMin) {
                            distanceMin = dist;
                            colorMin = cl;
                        }
                    }
                }
                if (colorMin != null) {
                    max = c;
                    kmax = k;
                    color = colorMin;
                }
            }
        }
        if (color != null) {
            res[res.length - nb] =  color.getRGB();
            palette.put(color, true);
            map.remove(kmax);
        }

        return getMaxColorsUsed4(nb - 1, map, res, palette);
    }
}
