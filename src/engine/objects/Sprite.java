package engine.objects;

import engine.graphics.Texture;
import engine.objects.GameObject.SpriteRenderer;

import java.io.IOException;

/**
 * Represents the texture at a higher level.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Sprite {
    public String image;
    private float[] texCoords;
    public boolean texCoordsChanged = true;

    /**
     * The constructor for a sprite with special texture coordinates.
     * @param image The file path of the sprite's image, starting from the res folder.
     * @param texCoords The texture coordinates.
     */
    public Sprite(String image, float[] texCoords) {
        this.image = image;
        this.texCoords = texCoords;
    }

    /**
     * The constructor for a sprite with the default texture coordinates.
     * @param image The file path of the sprite's image, starting from the res folder.
     */
    public Sprite(String image) {
        this.image = image;
        this.texCoords = new float[]{
                0, 0, //V0
                0, 1, //V1
                1, 1, //V2
                1, 0  //V3
        };
    }
    
    // GETTERS & SETTERS
    
    /**
     * Get the Texture Coordinates of the sprite.
     * @return Texture Coordinates
     */
    public float[] getTextureCoordinates() {
    	return texCoords;
    }
    /**
     * Set the Texture Coordinates of the sprite.
     * @param texCoords new Texture Coordinates.
     */
    public void setTextureCoordinates(float[] texCoords) {
    	this.texCoords = texCoords;
    	texCoordsChanged = true;
    }
}
