package engine.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.STBI_rgb_alpha;

/**
 * Loads models into memory.
 *
 * @author Bailey
 */

public class Loader {
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
     * Stores data in one of the attribute lists of the VAO.
     * @param index The index of the attribute.
     * @param coordinateSize The vector size.
     * @param data  The data to store in the vao.
     */
    static public void storeDataInAttributeList(int index, int coordinateSize, float[] data) {        
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);

        GL30.glVertexAttribPointer(index, coordinateSize, GL30.GL_FLOAT , false, 0, 0);
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
    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Swaps from writing mode to reading mode.
        return buffer;
    }
}
