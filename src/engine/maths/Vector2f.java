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
	 * Creates a Vector2f with all of the values equal to b.	
	 * @param b The value of each dimension.
	 * @return The new Vector2f.
	 */
	public static Vector2f square(float b) {
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
	 * Returns a string for debugging purposes.
	 * @return a string containing the values of <code>x</code> and <code>y</code>, in the format of "<b>x, y<b>".
	 */
	public String toString() {
		return x + ", " + y;
	}
	
	/**
	 * Adds a Vector2f to the current Vector2f and returns the sum.
	 * @param b The Vector2f you want to add to the current.
	 * @return the sum.
	 */
	public Vector2f plus(Vector2f b) {
		return new Vector2f(this.x + b.x, this.y + b.y);
	}

	/**
	 * Adds values to the current Vector2f's values and returns the sum.
	 * @param x What you want to add to the local x.
	 * @param y What you want to add to the local y.
	 * @return the sum.
	 */
	public Vector2f plus(float x, float y) {
		return new Vector2f(this.x + x, this.y + y);
	}
	
	/**
	 * Minus a Vector2f from the current and returns the new Vector2f;
	 * @param b The Vector to minus by.
	 * @return The new Vector2f.
	 */
	public Vector2f minus(Vector2f b) {
		return new Vector2f(this.x - b.x, this.y - b.y);
	}
	
	/**
	 * Times the current Vector2f by another and return the product.
	 * @param b The Vector2f you want to times the current by.
	 * @return the product.
	 */
	public Vector2f times(Vector2f b) {
		return new Vector2f(this.x * b.x, this.y * b.y);
	}
	
	/**
	 * Times the current Vector2f by a value and return the product.
	 * @param b The value you want to times the current Vector2f by.
	 * @return the product.
	 */
	public Vector2f times(float b) {
		return new Vector2f(this.x * b, this.y * b);
	}
}
