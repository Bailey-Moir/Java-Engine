package engine.graphics;

import engine.Camera;
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
        GL30.glBindVertexArray(texturedModel.vaoID);
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);

        GL30.glActiveTexture(GL30.GL_TEXTURE0); //What texture bank.
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturedModel.modelTexture);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
        GL30.glDrawElements(GL30.GL_TRIANGLES, texturedModel.vertexCount, GL30.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
}
