package sae;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class distance {

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

    public static void main(String[] args) throws IOException {
        BufferedImage o = ImageIO.read(new File("images_etudiants/originale.jpg"));

        BufferedImage c2 = ImageIO.read(new File("images_etudiants/coul_2.png"));
        BufferedImage c3 = ImageIO.read(new File("images_etudiants/coul_3.png"));
        BufferedImage c5 = ImageIO.read(new File("images_etudiants/coul_5.png"));
        BufferedImage c10 = ImageIO.read(new File("images_etudiants/coul_10.png"));
        BufferedImage c20 = ImageIO.read(new File("images_etudiants/coul_20.png"));

        System.out.println(distance(o,c2));
        System.out.println(distance(o,c3));
        System.out.println(distance(o,c5));
        System.out.println(distance(o,c10));
        System.out.println(distance(o,c20));
        System.out.println("------------------");

        BufferedImage c = ImageIO.read(new File("images_etudiants/copie.png"));
        BufferedImage cp = ImageIO.read(new File("images_etudiants/copie_pixels.png"));
        BufferedImage cn = ImageIO.read(new File("images_etudiants/copie_nb.png"));
        BufferedImage cr = ImageIO.read(new File("images_etudiants/copie_rouge.png"));
        BufferedImage cb = ImageIO.read(new File("images_etudiants/copie_vert_bleu.png"));

        System.out.println(distance(o,c));
        System.out.println(distance(o,cp));
        System.out.println(distance(o,cn));
        System.out.println(distance(o,cr));
        System.out.println(distance(o,cb));
        System.out.println("------------------");

        BufferedImage yg = ImageIO.read(new File("images_etudiants/copie_proche_YG.png"));
        BufferedImage ygw = ImageIO.read(new File("images_etudiants/copie_proche_YGW.png"));
        BufferedImage ygwo = ImageIO.read(new File("images_etudiants/copie_proche_YGWO.png"));
        BufferedImage ygwop = ImageIO.read(new File("images_etudiants/copie_proche_YGWOP.png"));

        System.out.println(distance(o,yg));
        System.out.println(distance(o,ygw));
        System.out.println(distance(o,ygwo));
        System.out.println(distance(o,ygwop));

    }
}
