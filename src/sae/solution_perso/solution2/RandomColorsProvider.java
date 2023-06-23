package sae.solution_perso.solution2;

import sae.MapColors;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomColorsProvider {

    //Attribute that will contain the img colors
    private final Map<Integer, Integer> colors;

    public RandomColorsProvider(BufferedImage img){
        this.colors = MapColors.getHashMapColors(img);
    }

    /**
     * Method that returns nb random color from the image colors
     * @param nb the number of color to seek
     * @param res the tab that contains the representative color
     * @return res
     */
    public int[] getRandomColorsFromImg (int nb, int[] res) {
        if(nb == 0) {
            return res;
        }
        Set<Integer> keys = this.colors.keySet();
        Random r = new Random();
        int kmax = (int) keys.toArray()[r.nextInt(keys.size())];

        res[res.length-nb] = kmax;
        this.colors.remove(kmax);
        return getRandomColorsFromImg(nb-1, res);

    }
}
