package engine.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for to represent dimensional math, e.g. position, sizes, colors, etc.
 *
 * @author Bailey
 */

public class Vector {
    public ArrayList<Float> dimensions = new ArrayList<Float>();

    /**
     * The default constructor. Defines the members.
     * @param a_dimensions The value of each dimensions.
     */
    public Vector(float[] a_dimensions) {
        for (float dimension : a_dimensions) {
            dimensions.add(dimension);
        }
    }

    /**
     * Creates a Vector with all of the values equal to b.
     * @param b The value of each dimension.
     * @param size The amount of dimensions.
     * @return The new Vector.
     */
    public static Vector square(float b, int size) {
        float[] localDimensions = new float[size];
        for (int i = 0; i < size; i++) {
            localDimensions[i] = b;
        }
        return new Vector(localDimensions);
    }

    /**
     * Sets the values of each dimension without setting it to a whole new Vector.
     * @param b The new dimensions.
     */
    public void set(float[] b) {
        dimensions.clear();
        for (float dimension : b) {
            dimensions.add(dimension);
        }
    }

    /**
     * Returns a string for debugging purposes.
     * @return a string containing the values of each dimension in the format of "<b>x, y, z, w, ...<b>".
     */
    public String toString() {
        String toPrint = "";

        for (float character : dimensions) {
            toPrint += character + ", ";
        }

        return toPrint.substring(0, toPrint.length() - 2);
    }

    /**
     * Adds a Vector to the current Vector and returns the sum.
     * @param b The Vector you want to add to the current.
     * @return the sum.
     */
    public Vector plus(Vector b) {
        try {
            if (b.dimensions.size() != dimensions.size()) {
                throw new NullPointerException("Two vectors have different sizes");
            } else {
                float[] localArr = new float[dimensions.size()];

                for (int i = 0; i < dimensions.size(); i++) {
                    localArr[i] = dimensions.toArray(new Float[0])[i] += b.dimensions.toArray(new Float[0])[i];
                }

                return new Vector(localArr);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Vector.square(0, 1);
        }
    }

    /**
     * Adds dimensions to the current Vector and returns the sum.
     * @param b The dimensions you want to add to the current vector.
     * @return the sum.
     */
    public Vector plus(float[] b) {
        try {
            if (b.length != dimensions.size()) {
                throw new NullPointerException("Two vectors have different sizes");
            } else {
                float[] localArr = new float[dimensions.size()];

                for (int i = 0; i < dimensions.size(); i++) {
                    localArr[i] = dimensions.toArray(new Float[0])[i] += b[i];
                }

                return new Vector(localArr);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Vector.square(0, 1);
        }
    }

    /**
     * Minuses a Vector from the current Vector and returns the difference.
     * @param b The Vector you want to minus from the current.
     * @return the difference.
     */
    public Vector minus(Vector b) {
        try {
            if (b.dimensions.size() != dimensions.size()) {
                throw new NullPointerException("Two vectors have different sizes");
            } else {
                float[] localArr = new float[dimensions.size()];

                for (int i = 0; i < dimensions.size(); i++) {
                    localArr[i] = dimensions.toArray(new Float[0])[i] -= b.dimensions.toArray(new Float[0])[i];
                }

                return new Vector(localArr);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Vector.square(0, 1);
        }
    }

    /**
     * Minuses dimensions from the current Vector and returns the difference.
     * @param b The dimensions you want to minus from the current vector.
     * @return the difference.
     */
    public Vector minus(float[] b) {
        try {
            if (b.length != dimensions.size()) {
                throw new NullPointerException("Two vectors have different sizes");
            } else {
                float[] localArr = new float[dimensions.size()];

                for (int i = 0; i < dimensions.size(); i++) {
                    localArr[i] = dimensions.toArray(new Float[0])[i] -= b[i];
                }

                return new Vector(localArr);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Vector.square(0, 1);
        }
    }

    /**
     * Times the current Vector by a Vector and returns the product.
     * @param b The Vector you want to times by the current.
     * @return the product.
     */
    public Vector times(Vector b) {
        try {
            if (b.dimensions.size() != dimensions.size()) {
                throw new NullPointerException("Two vectors have different sizes");
            } else {
                float[] localArr = new float[dimensions.size()];

                for (int i = 0; i < dimensions.size(); i++) {
                    localArr[i] = dimensions.toArray(new Float[0])[i] *= b.dimensions.toArray(new Float[0])[i];
                }

                return new Vector(localArr);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Vector.square(0, 1);
        }
    }

    /**
     * Times the current Vector by a number and returns the product.
     * @param b The number you want to times  the current Vector by.
     * @return the product.
     */
    public Vector times(float b) {
        float[] localArr = new float[dimensions.size()];

        for (int i = 0; i < dimensions.size(); i++) {
            localArr[i] = dimensions.toArray(new Float[0])[i] *= b;
        }

        return new Vector(localArr);
    }

    public float getAxis(int index) {
        return dimensions.get(index);
    }

    public void setAxis(int index, float value) {
        dimensions.set(index, value);
    }
}