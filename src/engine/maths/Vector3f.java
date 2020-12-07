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
	 * Creates a Vector3f with all of the values equal to b.	
	 * @param b The value of each dimension.
	 * @return The new Vector3f.
	 */
	public static Vector3f square(float b) {
		return new Vector3f(b, b, b);
		
	}
	
	/**
	 * Sets the values of <code>x</code>, <code>y</code>, and <code>z</code> without setting it to a whole new Vector3f.
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
	 * Returns a string for debugging purposes.
	 * @return a string containing the values of <code>x</code>, <code>y</code>, and <code>z</code>, in the format of "<b>x, y, z<b>".
	 */
	public String toString() {
		return x + ", " + y + ", " + z;
	}
	
	/**
	 * Adds a Vector3f to the current Vector3f and returns the sum.
	 * @param b The Vector3f you want to add to the current.
	 * @return the sum.
	 */
	public Vector3f plus(Vector3f b) {
		return new Vector3f(this.x + b.x, this.y + b.y, this.z + b.y);
	}

	/**
	 * Adds values to the current Vector3f's values and returns the new Vector3f.
	 * @param x What you want to add to the local x.
	 * @param y What you want to add to the local y.
	 * @param z What you want to add to the local z.
	 * @return the sum.
	 */
	public Vector3f plus(float x, float y, float z) {
		return new Vector3f(this.x + x, this.y + y, this.z + z);
	}
	
	/**
	 * Minus a Vector3f from the current and returns the new Vector3f;
	 * @param b The Vector to minus by.
	 * @return The new Vector3f.
	 */
	public Vector3f minus(Vector3f b) {
		return new Vector3f(this.x - b.x, this.y - b.y, this.z - b.z);
	}
	
	/**
	 * Times the current Vector3f by another and return the product.
	 * @param b The Vector3f you want to times the current by.
	 * @return the product.
	 */
	public Vector3f times(Vector3f b) {
		return new Vector3f(this.x * b.x, this.y * b.y, this.y * b.y);
	}
	
	/**
	 * Times the current Vector3f by a value and returns the product.
	 * @param b The value you want to times the current Vector3f by.
	 * @return the product.
	 */
	public Vector3f times(float b) {
		return new Vector3f(this.x * b, this.y * b, this.z * b);
	}
}