package engine.objects;

import engine.graphics.Loader;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sprite sheet.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class SpriteSheet {
    public Texture sheetTex;
    private final List<Sprite> generatedSprites = new ArrayList<>();
    private final int[] settings;
    public float aspectW = 1f, aspectH = 1f;

    /**
     * A constructor.
     * @param sheet The file path of the sheet's image, starting from the res folder.
     * @param cellw The width of each cell.
     * @param cellh The height of each cell.
     * @param marginw The width of the margin between each cell.
     * @param marginh The height of the margin between each cell.
     */
    public SpriteSheet(String sheet, int cellw, int cellh, int marginw, int marginh) {
        this.sheetTex = Loader.createTexture(sheet);
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
        this.sheetTex = Loader.createTexture(sheet);
        this.settings = settings;
    }

    /**
     * Generates the sprites from the sprite sheet.
     */
    public void gen() {
        //c = w / (t + m) : Gets count, and takes away the remainder.
        int tilesH = sheetTex.getImageWidth() / (settings[0] + settings[2]);
        int tilesV = sheetTex.getImageHeight() / (settings[1] + settings[3]);
        for (int y = 0; y < tilesV; y++) {
            for (int x = 0; x < tilesH; x++) {
                float startX = x*(settings[0] + settings[2]);
                float startY = y*(settings[1] + settings[3]);
                generatedSprites.add(new Sprite(sheetTex, new float[]{
                        //x = width / 64; y = height / 32
                        (x * (settings[0] + settings[2])) / (64f / aspectW), (y * (settings[1] + settings[3])) / (64f / aspectH),
                        (x * (settings[0] + settings[2])) / (64f / aspectW), ((y+1f) * settings[1] + y * settings[3]) / (64f / aspectH),
                        ((x+1f) * settings[0] + x * settings[2]) / (64f / aspectW), ((y+1f) * settings[1] + y * settings[3]) / (64f / aspectH),
                        ((x+1f) * settings[0] + x * settings[2]) / (64f / aspectW), (y * (settings[1] + settings[3])) / (64f / aspectH)
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
