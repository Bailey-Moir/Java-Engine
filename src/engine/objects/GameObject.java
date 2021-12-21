package engine.objects;

import engine.GlobalStorage;
import engine.graphics.Renderer;
import engine.io.Window;
import engine.maths.Vector2;
import engine.maths.Vector4;

/**
 * Represents a game object.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class GameObject {
    public Transform transform;
    public SpriteRenderer spriteRenderer;

    /**
     * Constructor.
     * @param position The starting position of the game object.
     * @param size The starting size of the game object.
     * @param image The image to be displayed on the game object.
     */
    public GameObject(Vector2 position, Vector2 size, Vector4 color, int layer, String image) {
        this.transform = new Transform();
        this.spriteRenderer = new SpriteRenderer(color, image, layer, this);

        this.transform.position = position;
        this.transform.size = size;

        GlobalStorage.spriteRenders.add(spriteRenderer);
    }

    /**
     * Size, positions, movement, rotation, etc.
     *
     * @author Bailey
     */
    public class Transform {
        //public float rotation;

        public Vector2 position, size;

        /**
         * Moves the object, including delta time.
         * @param delta How much it is going to change by.
         */
        public void translate(Vector2 delta) {
            position = position._plus(delta._times((float) (1 / spriteRenderer.getWindow().time.deltaTime)));
        }
    }

    static public class SpriteRenderer {
        public GameObject object;
        private final Window window;

        public Sprite sprite;
        public SpriteSheet spriteSheet;
        public boolean flipX = false, flipY = false;
        public int layer;
        public Vector4 color;

        /**
         * Constructor.
         *
         * @param color The 4 dimension color vector.
         * @param image The file path of the image (if there is any).
         * @param layer The layer to render the sprite on.
         * @param object Parent object of the SpriteRenderer.
         */
        public SpriteRenderer(Vector4 color, String image, int layer, GameObject object) {
            this.color = color;
            this.sprite = new Sprite(image);
            this.window = GlobalStorage.CurrentWindow;
            this.object = object;
            this.layer = layer;
        }

        /**
         * Calculates the vertices for displaying the image.
         * @return The vertices.
         */
        public float[] calculateVertices() {
            return new float[] {
                    ((object.transform.position.x - Renderer.camera.position.x - object.transform.size.x / 2) / getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.y - Renderer.camera.position.y + object.transform.size.y / 2) / getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0, //Top Left
                    ((object.transform.position.x - Renderer.camera.position.x - object.transform.size.x / 2) / getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.y - Renderer.camera.position.y - object.transform.size.y / 2) / getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0, //Bottom Left
                    ((object.transform.position.x - Renderer.camera.position.x + object.transform.size.x / 2) / getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.y - Renderer.camera.position.y - object.transform.size.y / 2) / getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0, //Top Right
                    ((object.transform.position.x - Renderer.camera.position.x + object.transform.size.x / 2) / getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.y - Renderer.camera.position.y + object.transform.size.y / 2) / getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0  //Bottom Right
            };
        }
        /**
         * Gets the texture coordinates used for rendering.
         * @return Texture Coordinates.
         */
        public float[] calculateTextureCoords() {
            return new float[]{
                    (flipX ? sprite.texCoords[6] : sprite.texCoords[0]), (flipY ? sprite.texCoords[3] : sprite.texCoords[1]), //V0
                    (flipX ? sprite.texCoords[4] : sprite.texCoords[2]), (flipY ? sprite.texCoords[1] : sprite.texCoords[3]), //V1
                    (flipX ? sprite.texCoords[2] : sprite.texCoords[6]), (flipY ? sprite.texCoords[7] : sprite.texCoords[5]), //V2
                    (flipX ? sprite.texCoords[0] : sprite.texCoords[6]), (flipY ? sprite.texCoords[5] : sprite.texCoords[7])  //V3
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

        /**
         * Default getter for the window.
         * @return The window (class).
         */
        public Window getWindow() {
            return window;
        }
    }
}
