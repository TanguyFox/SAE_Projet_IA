package sae;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Distance {

    public static double R (int rgb) {
        return (rgb & 0xff0000) >> 16;
    }

    public static double G (int rgb) {
        return (rgb & 0xff00) >> 8;
    }

    public static double B (int rgb) {
        return rgb & 0xff;
    }

    public static long distance (BufferedImage img1, BufferedImage img2) {
        long dist = 0;

        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {

                int p1 = img1.getRGB(i,j);
                int p2 = img2.getRGB(i,j);

                dist += Math.pow(R(p1) - R(p2),2) + Math.pow(G(p1) - G(p2),2) + Math.pow(B(p1) - B(p2),2);
            }
        }
        return dist;
    }

    public static double distanceCouleur(Color c1, Color c2) {
        int rgb1 = c1.getRGB();
        int rgb2 = c2.getRGB();

        return Math.pow(R(rgb1) - R(rgb2), 2) + Math.pow(G(rgb1) - G(rgb2), 2) + Math.pow(B(rgb1) - B(rgb2), 2);
    }
}
