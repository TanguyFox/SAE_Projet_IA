import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main_Q5 {

    public static int[] getCouleurs(int rgb) {
        int[] res = new int[3];
        res[0] = rgb & 0xff;
        res[1] = (rgb & 0xff00) >> 8;
        res[2] = (rgb & 0xff0000) >> 16;

        return res;
    }

    public static void main(String[] args) throws IOException {

        Color[] couleurs = new Color[5];
        couleurs[0] = Color.GREEN;
        couleurs[1] = Color.YELLOW;
        couleurs[2] = Color.RED;
        couleurs[3] = Color.ORANGE;


        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int rgb = img1.getRGB(i, j);
                int[] c0 = getCouleurs(rgb);
                int[] c1 = getCouleurs(couleurs[0].getRGB());
                int[] c2 = getCouleurs(couleurs[1].getRGB());
                int[] c3 = getCouleurs(couleurs[2].getRGB());
                int[] c4 = getCouleurs(couleurs[3].getRGB());

                double[] distances = new double [4];
                distances[0] = Math.pow(c0[0] - c1[0], 2) + Math.pow(c0[1] - c1[1], 2) + Math.pow(c0[2] - c1[2], 2);
                distances[1] = Math.pow(c0[0] - c2[0], 2) + Math.pow(c0[1] - c2[1], 2) + Math.pow(c0[2] - c2[2], 2);
                distances[2] = Math.pow(c0[0] - c3[0], 2) + Math.pow(c0[1] - c3[1], 2) + Math.pow(c0[2] - c3[2], 2);
                distances[3] = Math.pow(c0[0] - c4[0], 2) + Math.pow(c0[1] - c4[1], 2) + Math.pow(c0[2] - c4[2], 2);

                Color minc = new Color(0);
                double mind = 10000000;
                for (int k = 0; k < distances.length; k++) {
                    if(distances[k] < mind) {
                        mind = distances[k];
                        minc = couleurs[k];
                    }
                }
                img2.setRGB(i, j, minc.getRGB());

            }
        }
        ImageIO.write(img2, "jpg", new File("images_etudiants/testQ5.jpg"));



    }
}
