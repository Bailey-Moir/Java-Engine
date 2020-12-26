package engine.graphics;

import org.newdawn.slick.opengl.Texture;

/**
 * A VAO with a texture.
 * @see VAO
 * 
 * @author Bailey
 */

public class TexturedModel {
	
	private VAO VAO;
	private Texture texture;
	
	/**
	 * The default constructor.
	 * @param model The VAO (class not id).
	 * @param textureID the texture id.
	 */
	public TexturedModel(VAO model, Texture textureID) {
		this.VAO = model;
		this.texture = textureID;
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
		return texture.getTextureID();
	}

	public byte[] getTextureData() {
		return texture.getTextureData();
	}

	public int getTextureWidth() {
		return texture.getTextureWidth();
	}

	public int getTextureHeight() {
		return texture.getTextureWidth();
	}
	
}
