package engine.objects;

import java.util.ArrayList;
import java.util.Collection;
import engine.Script;
import engine.graphics.Renderer;
import engine.io.Window.Input;
import engine.io.Window;
import engine.maths.Vector;

/**
 * Represents a game object.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class GameObject implements Script {
	public static Collection<SpriteRenderer> spriteRenders = new ArrayList<>();
	public static Collection<Script> scripts = new ArrayList<>();

	public Transform transform;
	public SpriteRenderer spriteRenderer;

	/**
	 * Constructor.
	 * @param position The starting position of the game object.
	 * @param size The starting size of the game object.
	 * @param image The image to be displayed on the game object.
	 */
	public GameObject(Window window, Vector position, Vector size, Vector color, int layer, String image) {
		this.transform = new Transform();
		this.spriteRenderer = new SpriteRenderer(color, image, layer, window, this);

		this.transform.position = position;
		this.transform.size = size;

		scripts.add(this);
		spriteRenders.add(spriteRenderer);
	}

	/**
	 * Size, positions, movement, rotation, etc.
	 *
	 * @author Bailey
	 */
	public class Transform {
		//public float rotation;

		public Vector position, size;

		/**
		 * Moves the object, including delta time.
		 * @param delta How much it is going to change by.
		 */
		public void translate(Vector delta) {
			position = position.plus(delta.times((float) (1 / spriteRenderer.getWindow().time.deltaTime)));
		}
	}

	/**
	 * The properties that define how the sprite/object is rendered.
	 *
	 * @author Bailey
	 */
	public class SpriteRenderer {
		private final GameObject object;
		private final Window window;

		public boolean multiple;

		public Sprite sprite;
		public SpriteSheet spriteSheet;
		public boolean flipX = false, flipY = false;
		public int layer;
		public Vector color;

		public SpriteRenderer(Vector color, String image, int layer, Window window, GameObject object) {
			this.color = color;
			this.sprite = new Sprite(image);
			this.window = window;
			this.object = object;
			this.multiple = false;
			this.layer = layer;
		}
		public float[] calculateVertices() {
			return new float[] {
					((object.transform.position.getAxis(0) - Renderer.camera.position.getAxis(0) - object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.getAxis(1) - Renderer.camera.position.getAxis(1) + object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0, //Top Left
					((object.transform.position.getAxis(0) - Renderer.camera.position.getAxis(0) - object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.getAxis(1) - Renderer.camera.position.getAxis(1) - object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0, //Bottom Left
					((object.transform.position.getAxis(0) - Renderer.camera.position.getAxis(0) + object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.getAxis(1) - Renderer.camera.position.getAxis(1) - object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0, //Top Right
					((object.transform.position.getAxis(0) - Renderer.camera.position.getAxis(0) + object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * Renderer.camera.scale, ((object.transform.position.getAxis(1) - Renderer.camera.position.getAxis(1) + object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * Renderer.camera.scale, 0  //Bottom Right
			};
		}
		/**
		 * Gets the texture coordinates used for rendering.
		 * @return Texture Coordinates.
		 */
		public float[] calculateTextureCoords() {
			return new float[]{
					(flipX ? sprite.texCords[6] : sprite.texCords[0]), (flipY ? sprite.texCords[3] : sprite.texCords[1]), //V0
					(flipX ? sprite.texCords[4] : sprite.texCords[2]), (flipY ? sprite.texCords[1] : sprite.texCords[3]), //V1
					(flipX ? sprite.texCords[2] : sprite.texCords[6]), (flipY ? sprite.texCords[7] : sprite.texCords[5]), //V2
					(flipX ? sprite.texCords[0] : sprite.texCords[6]), (flipY ? sprite.texCords[5] : sprite.texCords[7])  //V3
			};
		}
		/**
		 * Gets the color coordinates used for rendering.
		 * @return Color coords
		 */
		public float[] calculateColorCoords() {
			return new float[] {
				color.getAxis(0), color.getAxis(1), color.getAxis(2), color.getAxis(3),
				color.getAxis(0), color.getAxis(1), color.getAxis(2), color.getAxis(3),
				color.getAxis(0), color.getAxis(1), color.getAxis(2), color.getAxis(3),
				color.getAxis(0), color.getAxis(1), color.getAxis(2), color.getAxis(3),
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
