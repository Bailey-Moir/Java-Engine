package engine.maths;

/**
 * Used for two dimensional math, e.g. position, sizes, etc.
 *  
 * @author Bailey
 */

public class Vector2f {
	public float x, y;
	
	/**
	 * The default constructor. Defines the members.
	 * @param x What you want x to be.
	 * @param y What you want y to be.
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Contructor for types of double instead of float. Defines the members.
	 * @param x What you want x to be.
	 * @param y What you want y to be.
	 */
	public Vector2f(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}
	
	public static Vector2f square(double b) {
		return new Vector2f(b, b);
		
	}
	
	/**
	 * Sets the values of x and y without setting it to a whole new Vector2f.
	 * @param x The new x value.
	 * @param y The new y value.
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the values of x and y without setting it to a whole new Vector2f; uses doubles instead of floats.
	 * @param x The new x value.
	 * @param y The new y value.
	 */
	public void set(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}
	
	/**
	 * Returns a string for debugging purposes.
	 * @return a string containing the values of x and y, in the format of "{x}, {y}".
	 */
	public String toString() {
		return x + ", " + y;
	}
	
	/**
	 * Adds a Vector2f to the current Vector2f.
	 * @param b The Vector2f you want to add to the current.
	 * @return the sum.
	 */
	public Vector2f plus(Vector2f b) {
		return new Vector2f(this.x + b.x, this.y + b.y);
	}

	/**
	 * Adds values to the current Vector2f's values.
	 * @param x What you want to add to the local x.
	 * @param y What you want to add to the local y.
	 * @return the sum.
	 */
	public Vector2f plus(float x, float y) {
		return new Vector2f(this.x + x, this.y + y);
	}
	
	public Vector2f minus(Vector2f b) {
		return new Vector2f(this.x - b.x, this.y - b.y);
	}
	
	/**
	 * Times the current Vector2f by another.
	 * @param b The Vector2f you want to times the current by.
	 * @return the product.
	 */
	public Vector2f times(Vector2f b) {
		return new Vector2f(this.x * b.x, this.y * b.y);
	}
	
	/**
	 * Times the current Vector2f by a value.
	 * @param b The value you want to times the current Vector2f by.
	 * @return the product.
	 */
	public Vector2f times(float b) {
		return new Vector2f(this.x * b, this.y * b);
	}
	
	/**
	 * Times the current Vector2f by a value. Uses double instead of float.
	 * @param b The value you want to times the current Vector2f by.
	 * @return the product.
	 */
	public Vector2f times(double b) {
		return new Vector2f(this.x * b, this.y * b);
	}

}
