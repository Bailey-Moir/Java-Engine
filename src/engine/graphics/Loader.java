package engine.graphics;

import engine.graphics.models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads models into memory.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Loader {

    static private List<Integer> vaos = new ArrayList<>();
    static private List<Integer> vbos = new ArrayList<>();
    static private List<Integer> textures = new ArrayList<>();

    /**
     * Takes in position of the model's vertices, loads that data into a vao, and returns info about the vao as a raw model object.
     * @param positions The positions of each vertex.
     * @param indices The order at which to connect the vertices.
     * @return The information about the model.
     */
    static public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] colors, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 4, colors);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    /**
     * Creates a id for a texture.
     * @param fileName The location of the image.
     * @return The id of the image.
     */
    static public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    /**
     * Creates a texture.
     * @param fileName The location of the image.
     * @return The image.
     */
    static public Texture createTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        textures.add(texture.getTextureID());
        return texture;
    }

    /**
     * Deletes all of the VAOs and VBOs from memory
     */
    static public void clear() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL30.glDeleteBuffers(vbo);
        }
        for (int texture : textures) {
            GL30.glDeleteTextures(texture);
        }
    }

    /**
     * Creates a vao and returns the id of said vao, as well as binding the vao.
     *
     * @return
     */
    static private int createVAO() {
        int vaoID = GL30.glGenVertexArrays(); //Creats an empty VAO.
        vaos.add(vaoID);
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
        int vboID = GL30.glGenBuffers();
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
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
        int vboID = GL30.glGenBuffers();
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vboID);
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
