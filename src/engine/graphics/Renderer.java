
package engine.graphics;

import org.lwjgl.opengl.GL30;

import engine.GameObject;

/**
 * Renders GameObject.
 * 
 * @author Bailey
 */

public class Renderer {	
	/**
	 * Renders all of the GameObject.allObjects defined in the class GameObject.allObjects.
	 * @see GameObject.allObjects
	 */
	public static void render() {
		Loader loader = new Loader();
		
		TexturedModel[] texturedModels = new TexturedModel[GameObject.allObjects.size()];
		
		//Order to connect them
      	int[] indices = { //Starts at 0 not 1;
      			0, 1, 2,
      			3, 1, 2
      	};
		
      	int i = 0;
      	for (GameObject object : GameObject.allObjects) {
      		float[] vertexColor = new float[] {
      			object.spriteRenderer.color.x, object.spriteRenderer.color.y, object.spriteRenderer.color.z, 
      			object.spriteRenderer.color.x, object.spriteRenderer.color.y, object.spriteRenderer.color.z, 
      			object.spriteRenderer.color.x, object.spriteRenderer.color.y, object.spriteRenderer.color.z, 
      			object.spriteRenderer.color.x, object.spriteRenderer.color.y, object.spriteRenderer.color.z
      		};
      		
          	texturedModels[i] = new TexturedModel(loader.loadToVAO(object.transform.calculateVertices(), vertexColor, indices), loader.loadTexture(object.spriteRenderer.image));
          	i++;
      	}
		
		for (TexturedModel texturedModel : texturedModels) {
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
