
package engine.graphics;

import org.lwjgl.opengl.GL30;

import engine.Camera;
import engine.GameObject;

import java.nio.ByteBuffer;

/**
 * Renders GameObject.
 *
 * TODO Fix textures, using shaders.
 * 
 * @author Bailey
 */

public class Renderer {	
	public static Camera currentCamera;
	
	/**
	 * Renders all of GameObject.allObjects.
	 */
	public static void render() {		
		Loader loader = new Loader();
		
		TexturedModel[] texturedModels = new TexturedModel[GameObject.allObjects.size()];
		
		//Order to connect them
      	int[] indices = { //Starts at 0 not 1;
      			0, 1, 2,
      			3, 1, 2
      	};

		float texCoords[] = {
				0.0f, 0.0f,  // lower-left corner
				1.0f, 0.0f,  // lower-right corner
				1.0f, 1.0f,   // top-right corner
				0.0f, 1.0f   // top-left corner
		};
		
      	int i = 0;
      	for (GameObject object : GameObject.allObjects) {
      		float[] vertexColor = new float[] {
      			object.spriteRenderer.color.dimensions.get(0), object.spriteRenderer.color.dimensions.get(1), object.spriteRenderer.color.dimensions.get(2), object.spriteRenderer.color.dimensions.get(3),
      			object.spriteRenderer.color.dimensions.get(0), object.spriteRenderer.color.dimensions.get(1), object.spriteRenderer.color.dimensions.get(2), object.spriteRenderer.color.dimensions.get(3),
      			object.spriteRenderer.color.dimensions.get(0), object.spriteRenderer.color.dimensions.get(1), object.spriteRenderer.color.dimensions.get(2), object.spriteRenderer.color.dimensions.get(3),
      			object.spriteRenderer.color.dimensions.get(0), object.spriteRenderer.color.dimensions.get(1), object.spriteRenderer.color.dimensions.get(2), object.spriteRenderer.color.dimensions.get(3)
      		};
      		
          	texturedModels[i] = new TexturedModel(loader.loadToVAO(object.transform.calculateVertices(currentCamera.position), vertexColor, indices, texCoords), loader.loadTexture(object.spriteRenderer.image));
          	i++;
      	}
		
		for (TexturedModel texturedModel : texturedModels) {
			//GL30.nglGenTextures(1, texturedModel.getTextureID());
			GL30.glEnable(GL30.GL_BLEND);
			GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
			VAO model = texturedModel.getVAOObject();

			GL30.glBindVertexArray(model.getVaoID());
			GL30.glEnableVertexAttribArray(0); //Enables attrib lists
			GL30.glEnableVertexAttribArray(1);

			GL30.glActiveTexture(GL30.GL_TEXTURE0); //Tells OpenGL what texture to render.
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturedModel.getTextureID());
			GL30.glDrawElements(GL30.GL_TRIANGLES, model.getVertexCount(), GL30.GL_UNSIGNED_INT, 0); //Params = (mode, indices, type, startPoint)
			
			GL30.glDisableVertexAttribArray(0); //Disables attrib lists
			GL30.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0); //Unbinds current VAO
		}
	}
}
