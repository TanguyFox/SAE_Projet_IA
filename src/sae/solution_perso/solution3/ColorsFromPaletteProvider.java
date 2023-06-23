package sae.solution_perso.solution3;

import sae.Distance;
import sae.MapColors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ColorsFromPaletteProvider {

    private final Map<Color, Boolean> palette = new HashMap<>();
    private final Map<Integer, Integer> imgColors;

    public ColorsFromPaletteProvider(BufferedImage img) {
        this.imgColors = MapColors.getHashMapColors(img);

        this.palette.put(Color.GREEN, false);
        this.palette.put(Color.YELLOW, false);
        this.palette.put(Color.MAGENTA, false);
        this.palette.put(Color.orange, false);
        this.palette.put(Color.LIGHT_GRAY, false);


    }

    public int[] getColorsFromPalette(int nb, int[] res) {
        if (nb == 0) {
            return res;
        }

        int max = -1;
        int kmax = 0;
        Color color = null;

        Set<Integer> keys = this.imgColors.keySet();
        for (int k : keys) {
            int c = this.imgColors.get(k);
            if (c > max) {
                double distanceMin = Double.MAX_VALUE;
                Color colorMin = null;
                for (Color cl : palette.keySet()) {
                    if (!palette.get(cl)) {
                        double dist = Distance.distanceCouleur(cl, new Color(k));
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
            this.imgColors.remove(kmax);
        }

        return getColorsFromPalette(nb - 1, res);
    }

}
