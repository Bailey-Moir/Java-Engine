package engine.maths;

/**
 * A vector with 4 axes. Used for to represent dimensional math, e.g. colour.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Vector4 {
    public float x, y, z, w;

    /**
     * The default constructor. Defines the members.
     * @param x The value of the x-axis.
     * @param y The value of the y-axis.
     * @param z The value of the z-axis.
     * @param w The value of the w-axis.
     */
    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * A constructor. For assigning non-reference objects.
     * @param source The Vector4 you are copying.
     */
    public Vector4(Vector4 source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * A constructor. Where x, y, z, and w all equal the given value.
     * @param value The value of the axes.
     */
    public Vector4(float value) {
        x = value;
        y = value;
        z = value;
        w = value;
    }

    /**
     * Creates a Vector4 with axes equaling 0.
     * @return A Vector4 with axes of 0.
     */
    public static Vector4 zero() {
        return new Vector4(0, 0, 0, 0);
    }

    /**
     * Sets the values of each axis without setting it to a whole new Vector.
     * @param x The value of the x-axis.
     * @param y The value of the y-axis.
     * @param z The value of the z-axis.
     * @param w The value of the w-axis.
     */
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Returns a string for debugging purposes.
     * @return a string containing the values of each axis in the format of "<b>x, y, z, w<b>".
     */
    public String toString() {
        return x + ", " + y + ", " + z + ", " + w;
    }

    /**
     * Uses Pythagoras theorem to calculate the length of the vector.
     * @return The length of the vector.
     */
    public float length() {
        return (float) Math.sqrt(x*x + y*y + z*z + w*w);
    }

    /**
     * Adds a Vector4 to the current Vector4 and returns the sum.
     * @param input The Vector4 you want to add to the current.
     * @return the sum.
     */
    public Vector4 _plus(Vector4 input) {
        return new Vector4(x + input.x, y + input.y, z + input.z, w + input.w);
    }

    /**
     * Adds axes to the current Vector4 and returns the sum.
     * @param x What to add to the x-axis.
     * @param y What to add to the y-axis.
     * @param z What to add to the z-axis.
     * @param w What to add to the w-axis.
     * @return the sum.
     */
    public Vector4 _plus(float x, float y, float z, float w) {
        return new Vector4(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    /**
     * Minuses a Vector4 from the current Vector4 and returns the difference.
     * @param input The Vector4 you want to minus from the current.
     * @return the difference.
     */
    public Vector4 _minus(Vector4 input) {
        return new Vector4(x - input.x, y - input.y, z - input.z, w - input.w);
    }

    /**
     * Minuses axes from the current Vector4 and returns the difference.
     * @param x What to minus off the x-axis.
     * @param y What to minus off the y-axis.
     * @param z What to minus off the y-axis.
     * @param w What to minus off the w-axis.
     * @return the difference.
     */
    public Vector4 _minus(float x, float y, float z, float w) {
        return new Vector4(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    /**
     * Times the axes of the current Vector4 by the axes of another Vector4 and returns the product.
     * @param input The Vector4 you want to times the current by.
     * @return The product.
     */
    public Vector4 _times(Vector4 input) {
        return new Vector4(x * input.x, y * input.x, z * input.z, w * input.w);
    }

    /**
     * Times the axes of the current Vector4 by other axes returns the product.
     * @param x What to times the x-axis by.
     * @param y What to times the y-axis by.
     * @param z What to times the z-axis by.
     * @param w What to times the w-axis by.
     * @return The product-
     */
    public Vector4 _times(float x, float y, float z, float w) {
        return new Vector4(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    /**
     * Times the current Vector by a number and returns the product.
     * @param input The number you want to times the current Vector by.
     * @return The product.
     */
    public Vector4 _times(float input) {
        return this._times(input, input, input, input);
    }

    /**
     * Gets the value of largest of the axis, accounting for positive of negatives.
     * @return Value of the largest axis.
     */
    public float largest() {
        return x >= y ? (x >= z ? Math.max(x, w) : Math.max(z, w)) : (y >= z ? Math.max(y, w) : Math.max(z, w));

    }

    /**
     * Turns the negative axes to positives, like the <code>Math.abs()</code> function.
     * @return The original vector but all negative axes flipped to be positive.
     */
    public Vector4 abs() {
        return new Vector4(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }
}