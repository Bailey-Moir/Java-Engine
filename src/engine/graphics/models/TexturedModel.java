package engine.graphics.models;

import engine.graphics.textures.ModelTexture;

/**
 * A raw model with a texture.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class TexturedModel {
    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.rawModel = model;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
