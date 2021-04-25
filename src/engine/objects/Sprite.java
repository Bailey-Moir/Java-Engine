package engine.objects;

import engine.graphics.Loader;
import org.newdawn.slick.opengl.Texture;

/**
 * Represents the a texture at a higher level.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Sprite {
    public Texture texture;
    public float[] texCords;

    /**
     * The constructor for a sprite with special texture cords.
     * @param image The file path of the sprite's image, starting from the res folder.
     * @param texCords The texture coordinates.
     */
    public Sprite(String image, float[] texCords) {
        this.texture = Loader.createTexture(image);
        this.texCords = texCords;
    }

    /**
     * The constructor for a sprite with special texture cords.
     * @param texture The texture of the sprite.
     * @param texCords The texture coordinates.
     */
    public Sprite(Texture texture, float[] texCords) {
        this.texture = texture;
        this.texCords = texCords;
    }

    /**
     * The constructor for a sprite with the default texture cords.
     * @param image The file path of the sprite's image, starting from the res folder.
     */
    public Sprite(String image) {
        this.texture = Loader.createTexture(image);
        this.texCords = new float[]{
                0, 0, //V0
                0, 1, //V1
                1, 1, //V2
                1, 0  //V3
        };
    }

    /**
     * The constructor for a sprite with the default texture cords.
     * @param texture The texture of the image.
     */
    public Sprite(Texture texture) {
        this.texture = texture;
        this.texCords = new float[]{
                0, 0, //V0
                0, 1, //V1
                1, 1, //V2
                1, 0  //V3
        };
    }
}
