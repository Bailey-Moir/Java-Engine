package engine.maths;

/**
 * A vector with 3 axes. Used for to represent dimensional math, e.g. position, sizes, colour etc.
 *
 * @author Bailey
 */

public class Vector3 {
    public float x, y, z;

    /**
     * The default constructor. Defines the members.
     * @param x The value of the x-axis.
     * @param y The value of the y-axis.
     * @param z The value of the z-axis.
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * A constructor. For assigning non-reference objects.
     * @param source The Vector3 you are copying.
     */
    public Vector3(Vector3 source) {
        x = source.x;
        y = source.y;
        z = source.z;
    }

    /**
     * A constructor. Where x, y, and z all equal the given value.
     * @param value The value of the axes.
     */
    public Vector3(float value) {
        x = value;
        y = value;
        z = value;
    }

    /**
     * Creates a Vector3 with axes equaling 0.
     * @return A Vector3 with axes of 0.
     */
    public static Vector3 zero() {
        return new Vector3(0, 0, 0);
    }

    /**
     * Sets the values of each axis without setting it to a whole new Vector.
     * @param x The value of the x-axis.
     * @param y The value of the y-axis.
     * @param z The value of the z-axis.
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns a string for debugging purposes.
     * @return a string containing the values of each axis in the format of "<b>x, y, z<b>".
     */
    public String toString() {
        return x + ", " + y + ", " + z;
    }

    /**
     * Uses Pythagoras theorem to calculate the length of the vector.
     * @return The length of the vector.
     */
    public float length() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Adds a Vector3 to the current Vector3 and returns the sum.
     * @param input The Vector3 you want to add to the current.
     * @return the sum.
     */
    public Vector3 _plus(Vector3 input) {
        return new Vector3(x + input.x, y + input.y, z + input.z);
    }

    /**
     * Adds axes to the current Vector3 and returns the sum.
     * @param x What to add to the x-axis.
     * @param y What to add to the y-axis.
     * @param z What to add to the z-axis.
     * @return the sum.
     */
    public Vector3 _plus(float x, float y, float z) {
        return new Vector3(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Minuses a Vector3 from the current Vector3 and returns the difference.
     * @param input The Vector3 you want to minus from the current.
     * @return the difference.
     */
    public Vector3 _minus(Vector3 input) {
        return new Vector3(x - input.x, y - input.y, z - input.z);
    }

    /**
     * Minuses axes from the current Vector3 and returns the difference.
     * @param x What to minus off the x-axis.
     * @param y What to minus off the y-axis.
     * @param z What to minus off the y-axis.
     * @return the difference.
     */
    public Vector3 _minus(float x, float y, float z) {
        return new Vector3(this.x - x, this.y - y, this.z - z);
    }

    /**
     * Times the axes of the current Vector3 by the axes of another Vector3 and returns the product.
     * @param input The Vector3 you want to times the current by.
     * @return The product.
     */
    public Vector3 _times(Vector3 input) {
        return new Vector3(x * input.x, y * input.x, z * input.z);
    }

    /**
     * Times the axes of the current Vector3 by other axes returns the product.
     * @param x What to times the x-axis by.
     * @param y What to times the y-axis by.
     * @param z What to times the z-axis by.
     * @return The product-
     */
    public Vector3 _times(float x, float y, float z) {
        return new Vector3(this.x * x, this.y * y, this.z * z);
    }

    /**
     * Times the current Vector by a number and returns the product.
     * @param input The number you want to times the current Vector by.
     * @return The product.
     */
    public Vector3 _times(float input) {
        return this._times(input, input, input);
    }

    /**
     * Gets the value of largest of the axis, accounting for positive of negatives.
     * @return Value of the largest axis.
     */
    public float largest() {
        return x >= y ? (Math.max(x, z)) : (Math.max(y, z));
    }

    /**
     * Turns the negative axes to positives, like the <code>Math.abs()</code> function.
     * @return The original vector but all negative axes flipped to be positive.
     */
    public Vector3 abs() {
        return new Vector3(Math.abs(x), Math.abs(y), Math.abs(z));
    }
}