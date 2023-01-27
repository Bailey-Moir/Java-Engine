package engine;

import engine.io.Window;
import engine.maths.Vector2;

/**
 * Represents a game camera.
 *
 * @author Bailey Moir
 */

public class Camera {
	public Window window;
	public Vector2 position;
	public float scale;

	/**
	 * The default constructor.
	 * @param pos The start position of the camera.
	 * @param scale The scale (FOV) of the camera.
	 */
	public Camera(Vector2 pos, float scale) {
		this.position = pos;
		this.window = Window.current;
		this.scale = scale;
	}
}
