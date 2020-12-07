package engine.maths;

/**
 * Used for three dimensional math, e.g. position, sizes, etc.
 *  
 * @author Bailey
 */

public class Vector4f {
	public float x, y, z, a;
	
	/**
	 * The default constructor. Defines the members.
	 * @param x What you want x to be.
	 * @param y What you want y to be.
	 * @param z What you want z to be.
	 * @param a What you want a to be.
	 */
	public Vector4f(float x, float y, float z, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}
	
	/**
	 * Creates a Vector4f with all of the values equal to b.	
	 * @param b The value of each dimension.
	 * @return The new Vector4f.
	 */
	public static Vector4f square(float b) {
		return new Vector4f(b, b, b, b);
	}
	
	/**
	 * Sets the values of <code>x</code>, <code>y</code>, <code>z</code>, and <code>a</code> without setting it to a whole new Vector4f.
	 * @param x The new x value.
	 * @param y The new y value.
	 * @param z The new z value.
	 * @param z The new a value.
	 */
	public void set(float x, float y, float z, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns a string for debugging purposes.
	 * @return a string containing the values of <code>x</code>, <code>y</code>, <code>z</code>, and <code>a</code> in the format of "<b>x, y, z, a<b>".
	 */
	public String toString() {
		return x + ", " + y + ", " + z + ", " + a;
	}
	
	/**
	 * Adds a Vector4f to the current Vector4f and returns the sum.
	 * @param b The Vector4f you want to add to the current.
	 * @return the sum.
	 */
	public Vector4f plus(Vector4f b) {
		return new Vector4f(this.x + b.x, this.y + b.y, this.z + b.y, this.a + b.a);
	}

	/**
	 * Adds values to the current Vector4f's values and returns the new Vector4f.
	 * @param x What you want to add to the local x.
	 * @param y What you want to add to the local y.
	 * @param z What you want to add to the local z.
	 * @param a What you want to add to the local a.
	 * @return the sum.
	 */
	public Vector4f plus(float x, float y, float z, float a) {
		return new Vector4f(this.x + x, this.y + y, this.z + z, this.a + a);
	}
	
	/**
	 * Minus a Vector4f from the current and returns the new Vector4f;
	 * @param b The Vector to minus by.
	 * @return The new Vector4f.
	 */
	public Vector4f minus(Vector4f b) {
		return new Vector4f(this.x - b.x, this.y - b.y, this.z - b.z, this.a - b.a);
	}
	
	/**
	 * Times the current Vector4f by another and return the product.
	 * @param b The Vector4f you want to times the current by.
	 * @return the product.
	 */
	public Vector4f times(Vector4f b) {
		return new Vector4f(this.x * b.x, this.y * b.y, this.z * b.z, this.a * b.a);
	}
	
	/**
	 * Times the current Vector4f by a value and returns the product.
	 * @param b The value you want to times the current Vector4f by.
	 * @return the product.
	 */
	public Vector4f times(float b) {
		return new Vector4f(this.x * b, this.y * b, this.z * b, this.a * b);
	}

}