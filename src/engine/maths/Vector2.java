package engine.maths;

/**
 * A vector with 2 axes. Used for to represent dimensional math, e.g. position, sizes, etc.
 *
 * @author Bailey
 */

public class Vector2 {
    public float x, y;

    /**
     * The default constructor. Defines the members.
     * @param x The value of the x-axis.
     * @param y The value of the y-axis.
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A constructor. For assigning non-reference objects.
     * @param source The Vector2 you are copying.
     */
    public Vector2(Vector2 source) {
        x = source.x;
        y = source.y;
    }

    /**
     * A constructor. Where x and y both equal the given value.
     * @param value The value of the axes.
     */
    public Vector2(float value) {
        x = value;
        y = value;
    }

    /**
     * Creates a Vector2 with axes equaling 0.
     * @return A Vector2 with axes of 0.
     */
    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    /**
     * Sets the values of each axis without setting it to a whole new Vector.
     * @param x The value of the x-axis.
     * @param y The value of the y-axis.
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string for debugging purposes.
     * @return a string containing the values of each axis in the format of "<b>x, y<b>".
     */
    public String toString() {
        return x + ", " + y;
    }

    /**
     * Uses Pythagoras theorem to calculate the length of the vector.
     * @return The length of the vector.
     */
    public float length() {
        return (float) Math.sqrt(x*x + y*y);
    }

    /**
     * Adds a Vector2 to the current Vector2 and returns the sum.
     * @param input The Vector2 you want to add to the current.
     * @return the sum.
     */
    public Vector2 _plus(Vector2 input) {
        return new Vector2(x + input.x, y + input.y);
    }

    /**
     * Adds axes to the current Vector2 and returns the sum.
     * @param x What to add to the x-axis.
     * @param y What to add to the y-axis.
     * @return the sum.
     */
    public Vector2 _plus(float x, float y) {
        return new Vector2(this.x + x, this.y + y);
    }

    /**
     * Minuses a Vector2 from the current Vector2 and returns the difference.
     * @param input The Vector2 you want to minus from the current.
     * @return the difference.
     */
    public Vector2 _minus(Vector2 input) {
        return new Vector2(x - input.x, y - input.y);
    }

    /**
     * Minuses axes from the current Vector2 and returns the difference.
     * @param x What to minus off the x-axis.
     * @param y What to minus off the y-axis.
     * @return the difference.
     */
    public Vector2 _minus(float x, float y) {
        return new Vector2(this.x - x, this.y - y);
    }

    /**
     * Times the axes of the current Vector2 by the axes of another Vector2 and returns the product.
     * @param input The Vector2 you want to times the current by.
     * @return The product.
     */
    public Vector2 _times(Vector2 input) {
        return new Vector2(x * input.x, y * input.x);
    }

    /**
     * Times the axes of the current Vector2 by other axes returns the product.
     * @param x What to times the x-axis by.
     * @param y What to times the y-axis by.
     * @return The product-
     */
    public Vector2 _times(float x, float y) {
        return new Vector2(this.x * x, this.y * y);
    }

    /**
     * Times the current Vector by a number and returns the product.
     * @param input The number you want to times the current Vector by.
     * @return The product.
     */
    public Vector2 _times(float input) {
        return this._times(input, input);
    }

    /**
     * Gets the value of largest of the axis, accounting for of positive of negatives.
     * @return Value of the largest axis.
     */
    public float largest() {
        return Math.max(x, y);
    }

    /**
     * Turns the negative axes to positives, like the <code>Math.abs()</code> function.
     * @return The original vector but all negative axes flipped to be positive.
     */
    public Vector2 abs() {
        return new Vector2(Math.abs(x), Math.abs(y));
    }
}