package engine.graphics;

/**
 * A glorified VAO.
 * 
 * @author Bailey
 */

public class VAO {
	
	private int vaoID;
	private int vertexCount;
	
	/**
	 * The default constructor.
	 * @param vaoID The id of the VAO
	 * @param vertexCount The amount of vertices on the object.
	 */
	public VAO(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	/**
	 * Basic getter for the VAO id.
	 * @return The VAO id.
	 */
	public int getVaoID() {
		return vaoID;
	}

	/**
	 * Basic getter for the vertex count.
	 * @return The vertex count.
	 */
	public int getVertexCount() {
		return vertexCount;
	}
	
}
