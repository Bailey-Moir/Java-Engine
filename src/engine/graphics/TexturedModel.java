package engine.graphics;

/**
 * A VAO with a texture.
 * @see VAO
 * 
 * @author Bailey
 */

public class TexturedModel {
	
	private VAO VAO;
	private int textureID;
	
	/**
	 * The default constructor.
	 * @param model The VAO (class not id).
	 * @param textureID the texture id.
	 */
	public TexturedModel(VAO model, int textureID) {
		this.VAO = model;
		this.textureID = textureID;
	}

	/**
	 * @return The VAO (class not id).
	 */
	public VAO getVAOObject() {
		return VAO;
	}

	/**
	 * @return the id of the texture.
	 */
	public int getTextureID() {
		return textureID;
	}
	
}
