package engine.objects;

import java.util.ArrayList;
import java.util.List;

import engine.Camera;
import engine.Script;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector;

/**
 * Represents a game object.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class GameObject {
	public enum RenderMode {
		SINGLE, SHEET
	}

	public static List<GameObject> allObjects = new ArrayList<>();
	public static List<Script> allScripts = new ArrayList<>();

	public Transform transform;
	public SpriteRenderer spriteRenderer;

	protected Input input;

	/**
	 * Constructor.
	 * @param position The starting position of the game object.
	 * @param size The starting size of the game object.
	 * @param image The image to be displayed on the game object.
	 */
	public GameObject(Vector position, Vector size, Window window, Vector color, String image) {
		this.transform = new Transform();
		this.spriteRenderer = new SpriteRenderer(color, image, window, this);

		this.transform.position = position;
		this.transform.size = size;

		this.input = window.input;

		allObjects.add(this);
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
		private GameObject object;
		private Window window;

		public boolean multiple;

		public RenderMode renderMode;
		public Sprite sprite;
		public SpriteSheet spriteSheet;
		public boolean flipX = false, flipY = false;
		public int layer;
		public Vector color;

		public SpriteRenderer(Vector color, String image, Window window, GameObject object) {
			this.color = color;
			this.sprite = new Sprite(image);
			this.renderMode = RenderMode.SINGLE;
			this.window = window;
			this.object = object;
			this.multiple = false;
		}

		/**
		 * Calculates the position of the vertices using known variables. Used for rendering.
		 * @return the vertices.
		 */
		public float[] calculateVertices() {
			return new float[] {
					(object.transform.position.getAxis(0) - object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (object.transform.position.getAxis(1) + object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Top Left
					(object.transform.position.getAxis(0) - object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (object.transform.position.getAxis(1) - object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Bottom Left
					(object.transform.position.getAxis(0) + object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (object.transform.position.getAxis(1) + object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Top Right
					(object.transform.position.getAxis(0) + object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (object.transform.position.getAxis(1) - object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160 //Bottom Right
			};
		}
		/**
		 * Calculates the object.transform.position of the vertices using known variables. Used for rendering.
		 * @return the vertices.
		 */
		public float[] calculateVertices(Camera camera) {
			return new float[] {
					((object.transform.position.getAxis(0) - camera.position.getAxis(0) - object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * camera.scale, ((object.transform.position.getAxis(1) - camera.position.getAxis(1) + object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * camera.scale, 0, //Top Left
					((object.transform.position.getAxis(0) - camera.position.getAxis(0) - object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * camera.scale, ((object.transform.position.getAxis(1) - camera.position.getAxis(1) - object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * camera.scale, 0, //Bottom Left
					((object.transform.position.getAxis(0) - camera.position.getAxis(0) + object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * camera.scale, ((object.transform.position.getAxis(1) - camera.position.getAxis(1) - object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * camera.scale, 0, //Top Right
					((object.transform.position.getAxis(0) - camera.position.getAxis(0) + object.transform.size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160) * camera.scale, ((object.transform.position.getAxis(1) - camera.position.getAxis(1) + object.transform.size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160) * camera.scale, 0  //Bottom Right
			};
		}
		/**
		 * Gets the texture coordinates used for rendering.
		 * @return Texture Coordinates.
		 */
		public float[] calculateTextureCoords() {
			float[] toReturn = {
					(flipX ? sprite.texCoords[6] : sprite.texCoords[0]), (flipY ? sprite.texCoords[3] : sprite.texCoords[1]), //V0
					(flipX ? sprite.texCoords[4] : sprite.texCoords[2]), (flipY ? sprite.texCoords[1] : sprite.texCoords[3]), //V1
					(flipX ? sprite.texCoords[2] : sprite.texCoords[6]), (flipY ? sprite.texCoords[7] : sprite.texCoords[5]), //V2
					(flipX ? sprite.texCoords[0] : sprite.texCoords[6]), (flipY ? sprite.texCoords[5] : sprite.texCoords[7])  //V3
			};
			return toReturn;
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
				color.getAxis(0), color.getAxis(1), color.getAxis(2), color.getAxis(3)
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
