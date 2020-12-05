package engine.maths;

/**
 * Used for three dimensional math, e.g. position, sizes, etc.
 *  
 * @author Bailey
 */

public class Vector3f {
	public float x, y, z;
	
	/**
	 * The default constructor. Defines the members.
	 * @param x What you want x to be.
	 * @param y What you want y to be.
	 * @param z What you want z to be.
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Contructor for types of double instead of float. Defines the members.
	 * @param x What you want x to be.
	 * @param y What you want y to be.
	 * @param z What you want z to be.
	 */
	public Vector3f(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}
	
	/**
	 * Sets the values of x, y, and z without setting it to a whole new Vector3f.
	 * @param x The new x value.
	 * @param y The new y value.
	 * @param z The new z value.
	 */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Sets the values of x, y, and z without setting it to a whole new Vector3f; uses doubles instead of floats.
	 * @param x The new x value.
	 * @param y The new y value.
	 * @param z The new z value.
	 */
	public void set(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}
	
	/**
	 * Returns a string for debugging purposes.
	 * @return a string containing the values of x, y, and z, in the format of "{x}, {y}, {z}".
	 */
	public String toString() {
		return x + ", " + y + ", " + z;
	}
	
	/**
	 * Adds a Vector3f to the current Vector3f.
	 * @param b The Vector3f you want to add to the current.
	 */
	public void plus(Vector3f b) {
		this.set(this.x + b.x, this.y + b.y, this.z + b.z);
	}

	/**
	 * Adds values to the current Vector3f's values.
	 * @param x What you want to add to the local x.
	 * @param y What you want to add to the local y.
	 * @param z What you want to add to the local z.
	 */
	public void plus(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	/**
	 * Times the current Vector3f by another.
	 * @param b The Vector3f you want to times the current by.
	 */
	public void times(Vector3f b) {
		this.x *= b.x;
		this.y *= b.y;
		this.z *= b.z;
	}
	
	/**
	 * Times the current Vector3f by a value.
	 * @param b The value you want to times the current Vector3f by.
	 */
	public void times(float b) {
		this.x *= b;
		this.y *= b;
		this.z *= b;
	}
	
	/**
	 * Times the current Vector3f by a value. Uses double instead of float.
	 * @param b The value you want to times the current Vector3f by.
	 */
	public void times(double b) {
		this.x *= b;
		this.y *= b;
		this.z *= b;
	}
}
