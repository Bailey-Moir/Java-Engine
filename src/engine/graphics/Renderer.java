package engine.graphics;

import engine.Camera;
import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import org.lwjgl.opengl.GL30;

/**
 * Renders models.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Renderer {
    static public Camera camera;

    static public void render(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);

        GL30.glActiveTexture(GL30.GL_TEXTURE0); //What texture bank.
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturedModel.getTexture().getID());
        GL30.glDrawElements(GL30.GL_TRIANGLES, model.getVertexCount(), GL30.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
