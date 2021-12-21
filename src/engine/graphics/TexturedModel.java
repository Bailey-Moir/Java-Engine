package engine.graphics;

/**
 * A raw model with a texture.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class TexturedModel {
    public final int modelTexture;
    public final int vaoID;
    public int vertexCount;

    public TexturedModel(int modelTexture, int vaoID, int vertexCount) {
        this.modelTexture = modelTexture;
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }
}
