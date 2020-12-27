package engine.graphics;

/**
 * Represents the a texture at a higher level.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Sprite {
    public Sprite(String image, float[] texCoords) {
        this.image = image;
        this.texCoords = texCoords;
    }
    public Sprite(String image) {
        this.image = image;
        this.texCoords = new float[]{
            0, 0, //V0
            0, 1, //V1
            1, 1, //V2
            1, 0  //V3
        };
    }

    public String image;
    public float[] texCoords;
}
