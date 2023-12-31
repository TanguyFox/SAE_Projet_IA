package sae.solution_perso.solution1;

import sae.MapColors;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

public class MaxColorsProvider {

    //Attribute that will contain the img colors
    private final Map<Integer, Integer> colors;

    public MaxColorsProvider(BufferedImage img){
        this.colors = MapColors.getHashMapColors(img);
    }

    /**
     * Method that returns nb max color in the img colors
     * @param nb the number of colors to seek
     * @param res the tab containing the nb representative colors in the images
     * @return res
     */
    public int[] getMaxColorsUsed (int nb, int[] res) {

        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = this.colors.keySet();
        int max = -1;
        int kmax = 0;
        for (int k : keys) {
            int c = this.colors.get(k);
            if (c > max) {
                max = c;
                kmax = k;
            }
        }
        res[res.length-nb] = kmax;
        this.colors.remove(kmax);
        return getMaxColorsUsed(nb-1, res);

    }




}
