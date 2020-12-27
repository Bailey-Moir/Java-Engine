package engine;

import java.util.ArrayList;
import java.util.List;

import engine.graphics.Sprite;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector;
import engine.components.Rigidbody;

/**
 * Represents a game object.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class GameObject {
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
		this.spriteRenderer = new SpriteRenderer();

		this.transform.position = position;
		this.transform.size = size;
		this.spriteRenderer.color = color;
		this.spriteRenderer.sprite = new Sprite(image);

		this.spriteRenderer.window = window;

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

		/**
		 * Calculates the position of the vertices using known variables. Used for rendering.
		 * @return the vertices.
		 */
		public float[] calculateVertices() {
			return new float[] {
					(position.getAxis(0) - size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) + size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Top Left
					(position.getAxis(0) - size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) - size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Bottom Left
					(position.getAxis(0) + size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) + size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Top Right
					(position.getAxis(0) + size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) - size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160 //Bottom Right
			};
		}

		/**
		 * Calculates the position of the vertices using known variables. Used for rendering.
		 * @return the vertices.
		 */
		public float[] calculateVertices(Vector offset) {
			return new float[] {
					(position.getAxis(0) - offset.getAxis(0) - size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) - offset.getAxis(1) + size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, 0, //Top Left
					(position.getAxis(0) - offset.getAxis(0) - size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) - offset.getAxis(1) - size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, 0, //Bottom Left
					(position.getAxis(0) - offset.getAxis(0) + size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) - offset.getAxis(1) - size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, 0, //Top Right
					(position.getAxis(0) - offset.getAxis(0) + size.getAxis(0) / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.getAxis(1) - offset.getAxis(1) + size.getAxis(1) / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, 0  //Bottom Right
			};
		}
	}

	/**
	 * The properties that define how the sprite/object is rendered.
	 *
	 * @author Bailey
	 */
	public class SpriteRenderer {
		public Sprite sprite;

		private Window window;

		public int layer;
		public Vector color;

		/**
		 * Default getter for the window.
		 * @return The window (class).
		 */
		public Window getWindow() {
			return window;
		}
	}
}
