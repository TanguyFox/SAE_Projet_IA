package sae.solution_perso;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Random;

public class MainSAE2 {


    public int createRandomColorFilter(int color) {

        String randomHex = generateRandomHex();

        // Créer l'objet Color à partir des composantes RVB
        int filteredColorRGB = color & Integer.parseInt(randomHex, 16);

        // Afficher le résultat
        System.out.println("Couleur aléatoire : " + color);
        return filteredColorRGB;
    }

    private static String generateRandomHex() {
        // Créer une instance de Random
        Random random = new Random();

        // Générer un nombre aléatoire
        int randomNumber = random.nextInt();

        // Formater le nombre en hexadécimal
        String randomHex = Integer.toHexString(randomNumber);

        // S'assurer que la chaîne a une longueur de 6 caractères
        while (randomHex.length() < 6) {
            randomHex = "0" + randomHex;
        }

        return randomHex;
    }

    //Create a filtered image with nbCoul in the image
    public void createImageWithFilter(File originImg) throws IOException {
        BufferedImage img1 = ImageIO.read(new File("images_etudiants/originale.jpg"));
        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {

                int filter = createRandomColorFilter(img1.getRGB(i, j));
                img2.setRGB(i, j, filter);
            }
        }
        ImageIO.write(img2, "png", new File("images_etudiants/testQ3.png"));
    }


}

