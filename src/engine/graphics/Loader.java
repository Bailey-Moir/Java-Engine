package engine.graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Loads models into memory.
 * 
 * @author Bailey
 */

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	public List<Integer> textures = new ArrayList<Integer>();
	
	/**
	 * Loads attributes to a new VAO
	 * @param positions The position of each vertex.
	 * @param textureCoords The texture cords.
	 * @param indices The order at which you connect the positions (vertices).
	 * @return A new VAO.
	 * @see VAO
	 */
	public VAO loadToVAO(float[] positions, float[] vertexColors, int[] indices) {		
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 3, 	vertexColors);
		return new VAO(vaoID, indices.length);
	}
	
	/**
	 * Creates a texture
	 * @param fileName the file name or file path of the png relative to resources
	 * @return The texture's id.
	 */
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("resources/" + fileName + ".png"), GL11.GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	/**
	 * Deletes VAOs, VBOs, and textures.
	 */
	public void cleanUp() {
		for (int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture:textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	/**
	 * Creates a VAO and returns the VAO's id.
	 * @return The VAO id.
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays(); // Creates a empty VAO, returns id.
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * Creates an VBO (Vertex Buffer Object) in the VAO (Vertex Array Object).
	 * @param attributeNumber The attribute identifier, e.g. 1, 2, 3, etc.
	 * @param coordSize How many cords are in each vertex thing.
	 * @param data The information you are storing in the attribute.
	 */
	private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data) {
		int vboID = GL15.glGenBuffers(); // Creates empty VBO, returns id.
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//Parameters: (numberOfAttribList, lengtOfEachVertice, typeOfData, isNormalized, distanceBetweenVertices, dataOffset)
		GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Binds the indices to a VBO.
	 * @param indices The indices to bind
	 */
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers(); // Creates empty VBO, returns id.
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Stores data in an integer buffer.
	 * @param data The data to store.
	 * @return The integer buffer.
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length); // Creates empty int buffer.
		buffer.put(data); // Puts data into int buffer.
		buffer.flip(); // Prepares buffer to be read from, flip() means it knows we are done.
		return buffer;
	}

	/**
	 * Stores data in a float buffer.
	 * @param data The data to store.
	 * @return The float buffer.
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); // Creates empty float buffer.
		buffer.put(data); // Puts data into float buffer.
		buffer.flip(); // Prepares buffer to be read from, flip() means it knows we are done.
		return buffer;
		
	}
} 
