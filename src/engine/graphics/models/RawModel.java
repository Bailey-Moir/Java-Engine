package engine.graphics.models;

/**
 * Represents a 3D model stored in memory.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class RawModel {
    private int vaoID, vertexCount;

    /**
     * The default constructor.
     * @param vaoID The id that the VAO id stored in.
     * @param vertexCount the number of vertices
     */
    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
