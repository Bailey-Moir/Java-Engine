package engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture extends BufferedImage {
    public String image;

    public static Texture gen(String image) {
        try {
            return new Texture( image, ImageIO.read(new File("res/" + image + ".png")) );
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    public Texture(String image, BufferedImage buffer) {
        super(buffer.getWidth(), buffer.getHeight(), buffer.getType());

        super.setData(buffer.getData());

        this.image = image;
    }
}
