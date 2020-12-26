package engine.graphics.shaders;

/**
 * Used to create static models
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class StaticShader extends ShaderProgram {
    public static final String VERTEX_FILE = "src/engine/graphics/shaders/mainVertex.glsl";
    public static final String FRAGMENT_FILE = "src/engine/graphics/shaders/mainFragment.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }
}
