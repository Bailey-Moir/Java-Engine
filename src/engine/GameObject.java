package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

/**
 * Represents a game object.
 * 
 * @author Bailey
 */

public class GameObject {
	public static List<GameObject> allObjects = new ArrayList<GameObject>();
	
	public Transform transform;	
	public spriteRenderer spriteRenderer;
	
	protected Input input;
	
	/**
	 * Constructor.
	 * @param position The starting position of the game object.
	 * @param size The starting size of the game object.
	 * @param image The image to be displayed on the game object.
	 */
	public GameObject(Vector2f position, Vector2f size, Window window, Vector3f color, String image) {
		this.transform = new Transform();
		this.spriteRenderer = new spriteRenderer();	
		
		this.transform.position = position;
		this.transform.size = size;
		this.spriteRenderer.color = color;
		this.spriteRenderer.image = "null";
		
		this.spriteRenderer.window = window;
		
		this.input = window.input;
		
		allObjects.add(this);
	}
	
	public Consumer<Integer> StartFunc, UpdateFunc;
	
	/**
	 * Size, positions, movement, rotation, etc.
	 * 
	 * @author Bailey
	 */
	public class Transform {
		public float rotation;
		
		public Vector2f position, size;

		/**
		 * Moves the object, including delta time.
		 * @param delta How much it is going to change by.
		 */
		public void translate(Vector2f delta) {
			position = position.plus(delta.times(1 / spriteRenderer.getWindow().time.deltaTime));
		}
		
		/**
		 * Calculates the position of the vertices using known variables. Used for rendering.
		 * @return the vertices.
		 */
		public float[] calculateVertices() {
			float[] vertices = {
					(position.x - size.x / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.y + size.y / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Top Left
					(position.x - size.x / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.y - size.y / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Bottom Left
					(position.x + size.x / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.y + size.y / 2) / spriteRenderer.getWindow().getHEIGHT() * 160, //Top Right
					(position.x + size.x / 2) / spriteRenderer.getWindow().getWIDTH() * 160, (position.y - size.y / 2) / spriteRenderer.getWindow().getHEIGHT() * 160 //Bottom Right
			};
			return vertices;
		}
	}
	
	/**
	 * The properties that define how the sprite/object is rendered.
	 * 
	 * @author Bailey
	 */
	public class spriteRenderer {
		private Window window;
		
		public String image;
		public int layer;		
		public Vector3f color;
		
		public Window getWindow() {
			return window;
		}
	}
}
