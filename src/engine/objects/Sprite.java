package engine.objects;

/**
 * Represents the a texture at a higher level.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Sprite {
    public String image;
    public String name;
    public float[] texCoords;

    /**
     * The constructor for a sprite with special texture coords.
     * @param image The file path of the sprite's image, starting from the res folder.
     * @param texCoords The texture coordinates.
     */
    public Sprite(String image, float[] texCoords) {
        this.image = image;
        this.texCoords = texCoords;
        this.name = "";
    }

    /**
     * The constructor for a sprite with the default texture coords.
     * @param image The file path of the sprite's image, starting from the res folder.
     */
    public Sprite(String image) {
        this.image = image;
        this.texCoords = new float[]{
                0, 0, //V0
                0, 1, //V1
                1, 1, //V2
                1, 0  //V3
        };
        this.name = "";
    }
}
