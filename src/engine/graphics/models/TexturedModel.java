package engine.graphics.models;

import engine.graphics.textures.ModelTexture;

/**
 * A raw model with a texture.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class TexturedModel {
    public final RawModel rawModel;
    public final ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.rawModel = model;
        this.texture = texture;
    }
}
