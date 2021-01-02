package engine.graphics.textures;

/**
 * Represents a texture that we can use to texture models.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class ModelTexture {
    private final int textureID;

    public ModelTexture(int id) {
        this.textureID = id;
    }

    public int getID() {
        return this.textureID;
    }
}
