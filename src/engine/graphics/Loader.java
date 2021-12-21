package engine.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.STBI_rgb_alpha;

/**
 * Loads models into memory.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Loader {
    static private final List<Integer> VAOs = new ArrayList<>();
    static private final List<Integer> VBOs = new ArrayList<>();

    /**
     * Takes in position of the model's vertices, loads that data into a vao, and returns info about the vao as a raw model object.
     * @param positions The positions of each vertex.
     * @param indices The order at which to connect the vertices.
     * @return The information about the model.
     */
    static public int loadToVAO(float[] positions, float[] textureCords, float[] colors, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCords);
        storeDataInAttributeList(2, 4, colors);
        unbindVAO();
        return vaoID;
    }

    /**
     * Creates an id for a texture.
     * @param image The location of the image.
     * @return The id of the image.
     */
    static public int loadTexture(String image) {
        String absolutePath = System.getProperty("user.dir") + "\\res\\" + image + ".png";
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer data = STBImage.stbi_load(absolutePath, x, y, channels, STBI_rgb_alpha);

        assert data != null;

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x.get(0), y.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);

        return texture;
    }

    /**
     * Deletes all the VAOs and VBOs from memory
     */
    static public void clear() {
        for (int vao : VAOs) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int VBO : VBOs) {
            GL30.glDeleteBuffers(VBO);
        }
    }

    /**
     * Creates a vao and returns the id of said vao, as well as binding the vao.
     *
     * @return The ID of the vao.
     */
    static private int createVAO() {
        int vaoID = GL30.glGenVertexArrays(); //Creates an empty VAO.
        VAOs.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Stores data in one of the attribute lists of the VAO.
     * @param index The index of the attribute.
     * @param coordinateSize The vector size.
     * @param data  the data to store in the vao.
     */
    static private void storeDataInAttributeList(int index, int coordinateSize, float[] data) {
        int VBOid = GL30.glGenBuffers();
        VBOs.add(VBOid);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, VBOid);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);

        GL30.glVertexAttribPointer(index, coordinateSize, GL30.GL_FLOAT , false, 0, 0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }
    
    /**
     * Unbinds the VAO.
     */
    static private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Binds indices buffer to the VAO.
     */
    static private void bindIndicesBuffer(int[] indices) {
        int VBOid = GL30.glGenBuffers();
        VBOs.add(VBOid);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, VBOid);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
    }

    /**
     * Stores data in a float buffer.
     * @param data The data to store.
     * @return The buffer with the data in it.
     */
    static private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Swaps from writing mode to reading mode.
        return buffer;
    }


    /**
     * Stores data in an int buffer.
     * @param data The data to store.
     * @return The buffer with the data in it.
     */
    static private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Swaps from writing mode to reading mode.
        return buffer;
    }
}
