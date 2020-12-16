package engine;

import engine.maths.Vector2f;

/**
 * Represents a game camera.
 *
 * @author Bailey Moir
 */

public class Camera {
	public Vector2f position;

	/**
	 * The default constructor.
	 * @param pos The start position of the camera
	 */
	public Camera(Vector2f pos) {
		this.position = pos;
	}
}
