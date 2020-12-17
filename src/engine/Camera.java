package engine;

import engine.maths.Vector;

/**
 * Represents a game camera.
 *
 * @author Bailey Moir
 */

public class Camera {
	public Vector position;

	/**
	 * The default constructor.
	 * @param pos The start position of the camera
	 */
	public Camera(Vector pos) {
		this.position = pos;
	}
}
