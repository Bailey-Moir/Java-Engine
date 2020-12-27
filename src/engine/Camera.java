package engine;

import engine.io.Window;
import engine.maths.Vector;

/**
 * Represents a game camera.
 *
 * @author Bailey Moir
 */

@SuppressWarnings("unused")
public class Camera {
	public Window window;
	public Vector position;
	public float scale;

	/**
	 * The default constructor.
	 * @param pos The start position of the camera
	 */
	public Camera(Vector pos, float scale, Window window) {
		this.position = pos;
		this.window = window;
		this.scale = scale;
	}
}
