package engine.objects.components;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

import engine.graphics.Loader;
import engine.io.Window;
import engine.maths.Vector2;
import engine.maths.Vector3;
import engine.maths.Vector4;
import engine.objects.Component;
import engine.objects.GameObject;
import engine.objects.Sprite;
import engine.objects.SpriteSheet;

public class SpriteRenderer extends Component implements Comparable<SpriteRenderer> {
	public final static List<SpriteRenderer> all = new ArrayList<>();
    private final Window window;

    public SpriteSheet spriteSheet;
    public boolean flipX = false, flipY = false;
    public int layer;

    private Sprite sprite;
    private Vector4 color;
    private int[] indices = {
		0, 1, 3,
		3, 1, 2
	};
    
    private boolean	colorChanged = true;
    private boolean indicesChanged = true;
    
    private int VAO = 0;
    private int	verticesVBO = 0;
    private int	textureCoordsVBO = 0;
    private int	colorVBO = 0;
    private int	indicesVBO = 0;

    /**
     * Constructor.
     * @param color The 4 dimension color vector.
     * @param image The file path of the image (if there is any).
     * @param layer The layer to render the sprite on.
     * @param object Parent object of the SpriteRenderer.
     */
    public SpriteRenderer(Vector4 color, String image, int layer, GameObject object) {
        super(object);
        this.color = color;
        this.sprite = new Sprite(image);
        this.window = Window.current;
        this.layer = layer;
        
        VAO = GL30.glGenVertexArrays(); //Creates an empty VAO.
        
        verticesVBO = GL30.glGenBuffers();
        textureCoordsVBO = GL30.glGenBuffers();
        colorVBO = GL30.glGenBuffers();
        indicesVBO = GL30.glGenBuffers();
        
        all.add(this);
    }
    
    /**
     * Should be called when the GameObject is effectively destructed.
     */
    public void cleanUp() {
    	GL30.glDeleteBuffers(verticesVBO);
    	GL30.glDeleteBuffers(textureCoordsVBO);
    	GL30.glDeleteBuffers(colorVBO);
    	GL30.glDeleteBuffers(indicesVBO);
        GL30.glDeleteVertexArrays(VAO);
    }
    
    /**
     * Renders the Object to the window.
     */
    public void render() {
    	GL30.glBindVertexArray(VAO);
        
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticesVBO);
        Loader.storeDataInAttributeList(0, 3, calculateVertices());
        if (sprite.texCoordsChanged) {
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, textureCoordsVBO);
            Loader.storeDataInAttributeList(1, 2, calculateTextureCoords());
            sprite.texCoordsChanged = false;
        }
        if (colorChanged) {
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, colorVBO);            
            Loader.storeDataInAttributeList(2, 4, calculateColorCoords());
            colorChanged = false;
        }
        if (indicesChanged) {
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
            IntBuffer buffer = Loader.storeDataInIntBuffer(indices);
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
            indicesChanged = false;
        }
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        
        int textureID = Loader.loadTexture(sprite.image);            
        
        GL30.glEnableVertexAttribArray(0); //position
        GL30.glEnableVertexAttribArray(1); //tex coords
        GL30.glEnableVertexAttribArray(2); //color

        GL30.glActiveTexture(GL30.GL_TEXTURE0); //What texture bank.
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureID);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
        GL30.glDrawElements(GL30.GL_TRIANGLES, indices.length, GL30.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    /**
     * Calculates the vertices for displaying the image.
     * @return The vertices.
     */
    public float[] calculateVertices() {
    	Vector2 size = parent.transform.size;
    	int width = getWindow().getWIDTH();
    	int height = getWindow().getHEIGHT();
    	double rotation = 0.5*Math.PI - parent.transform.rotation;        
    	
        float points[] = new float[12];
    	double radius = 0.5 * Math.sqrt(size.x*size.x + size.y*size.y);
        for (int i = -1, j = 0; j < 4; j++) {
        	double local_angle = Math.atan2(j < 2 ? size.x : -size.x, j == 1 || j == 2 ? -size.y : size.y);
        	
        	points[++i] = 160 * Window.activeCamera.scale * (parent.transform.position.x + (float) (radius*Math.cos(local_angle + rotation)) - Window.activeCamera.position.x) / width;
        	points[++i] = 160 * Window.activeCamera.scale * (parent.transform.position.y + (float) (radius*Math.sin(local_angle + rotation)) - Window.activeCamera.position.y) / height;
        	points[++i] = 0;
        }
        
        return points;
    }
    /**
     * Gets the texture coordinates used for rendering.
     * @return Texture Coordinates.
     */
    public float[] calculateTextureCoords() {
    	float[] coords = sprite.getTextureCoordinates();
        return new float[]{
            (flipX ? coords[6] : coords[0]), (flipY ? coords[3] : coords[1]), //V0
            (flipX ? coords[4] : coords[2]), (flipY ? coords[1] : coords[3]), //V1
            (flipX ? coords[2] : coords[6]), (flipY ? coords[7] : coords[5]), //V2
            (flipX ? coords[0] : coords[6]), (flipY ? coords[5] : coords[7])  //V3
        };
    }
    /**
     * Gets the color coordinates used for rendering.
     * @return Color coords
     */
    public float[] calculateColorCoords() {
        return new float[] {
                color.x, color.y, color.z, color.w,
                color.x, color.y, color.z, color.w,
                color.x, color.y, color.z, color.w,
                color.x, color.y, color.z, color.w,
        };
    }
    
    // GETTERS & SETTERS
    
    /**
     * Default getter for the window.
     * @return The window (class).
     */
    public Window getWindow() {
        return window;
    }
    
    /**
     * Get the current indices of the object for rendering.
     * @return Indices.
     */
    public int[] getIndices() {
    	return indices;
    }
    /**
     * Set the indices of the object for rendering.
     * @param v Indices.
     */
    public void setIndices(int[] v) {
    	indices = v;
    	indicesChanged = true;
    }

    /**
     * Get the current color of the object.
     * @return Color.
     */
    public Vector4 getColor() {
    	return color;
    }
    /**
     * Set the color of the object.
     * @param v Color.
     */
    public void setColor(Vector4 v) {
    	color = v;
    	colorChanged = true;
    }
    
    /**
     * Get the current sprite of the object for rendering.
     * @return Sprite.
     */
    public Sprite getSprite() {
    	return sprite;
    }
    /**
     * Set the sprite of the object for rendering.
     * @param v new Sprite.
     */
    public void setSprite(Sprite v) {
    	sprite = v;
    	sprite.texCoordsChanged = true;
    }

	@Override
	public int compareTo(SpriteRenderer v) {
		return this.layer - v.layer;
	}
}
