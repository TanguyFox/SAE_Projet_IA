package sae.solution_perso.solution4;

import sae.MapColors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MaxColorsFromPartsProvider {

    private final Map<Integer, Integer> colors;

    public MaxColorsFromPartsProvider(BufferedImage img){
        this.colors = MapColors.getTreeMapColors(img);
    }

    public int[]  getMaxColorsFromParts(int nb, int[] res, int size, int nbCouleurs) {
        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = this.colors.keySet();
        List<Integer> listKey = new ArrayList<>(keys);
        int indiceCouleur = res.length-nb;
        int indicePossible = size/nbCouleurs;
        int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int i = listKey.indexOf(k);
            if (i >= indicePossible*indiceCouleur && i < indicePossible*indiceCouleur+indicePossible) {
                int c = this.colors.get(k);
                if (c > max) {
                    max = c;
                    kmax = k;
                }
            }
        }
        res[indiceCouleur] = kmax;
        this.colors.remove(kmax);
        return getMaxColorsFromParts(nb-1, res, size, nbCouleurs);

    }

    public Map<Integer, Integer> getColors() {
        return colors;
    }
}
