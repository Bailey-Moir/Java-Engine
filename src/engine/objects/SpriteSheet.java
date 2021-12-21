package engine.objects;

import engine.graphics.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sprite sheet.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class SpriteSheet {
    public String sheet;
    private final List<Sprite> generatedSprites = new ArrayList<>();
    private final int[] settings;

    /**
     * A constructor.
     * @param sheet The file path of the sheet's image, starting from the res folder.
     * @param cellw The width of each cell.
     * @param cellh The height of each cell.
     * @param marginw The width of the margin between each cell.
     * @param marginh The height of the margin between each cell.
     */
    public SpriteSheet(String sheet, int cellw, int cellh, int marginw, int marginh) {
        this.sheet = sheet;
        this.settings = new int[] {cellw, cellh, marginw, marginh};
        gen();
    }
    /**
     * A constructor.
     * @param sheet The file path of the sheet's image, starting from the res folder.
     * @param settings An array, containing the values of:
     * <ol style="margin-top: 0px; padding-left: 10px;">
     *     <li>The pixel width of each cell.</li>
     *     <li>The pixel height of each cell.</li>
     *     <li>The pixel width of the margin between each cell.</li>
     *     <li>The pixel height of the margin between each cell.</li>
     * </ol>
     */
    public SpriteSheet(String sheet, int[] settings) {
        this.sheet = sheet;
        this.settings = settings;
    }

    /**
     * Generates the sprites from the sprite sheet.
     */
    public void gen() {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File("res/" + sheet + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bimg != null;

        int width = bimg.getWidth(), height = bimg.getHeight();

        //c = w / (t + m) : Gets count, and takes away the remainder.
        int tilesH = width / (settings[0] + settings[2]);
        int tilesV = height / (settings[1] + settings[3]);
        for (int y = 0; y < tilesV; y++) {
            for (int x = 0; x < tilesH; x++) {
                float startX = x * (settings[0] + settings[2]);
                float startY = y * (settings[1] + settings[3]);
                generatedSprites.add(new Sprite(sheet, new float[]{
                        startX / width, startY / height,
                        startX / width, (startY + settings[1]) / height,
                        (startX + settings[0]) / width, (startY + settings[1]) / height,
                        (startX + settings[0]) / width, startY / height
                }));
            }
        }
    }

    /**
     * Gets the sprite that has the number of <code>identifier</code>. Uses 0 notation.
     * @param identifier The number of the sprite.
     * @return The sprite of identifier;
     */
    public Sprite getSprite(int identifier) {
        return generatedSprites.get(identifier);
    }
}
