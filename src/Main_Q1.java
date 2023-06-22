import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q1 {

    public static void main(String[] args) throws IOException {

        // recueration d'une image
        BufferedImage i = ImageIO.read(new File("images_etudiants/originale.jpg"));

        //eciture d'une image a partir de l'image precedente
        ImageIO.write(i, "png", new File("images_etudiants/testQ1.png"));

    }
}
