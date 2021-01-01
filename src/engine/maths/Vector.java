package engine.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Used for to represent dimensional math, e.g. position, sizes, colors, etc.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Vector {
    public List<Float> dimensions = new ArrayList<>();

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

    public float toDistance() {
        AtomicReference<Float> distance = new AtomicReference<>((float) 0);
        dimensions.stream().map(dimension -> {
            return Math.pow(dimension, 2);
        }).forEach(dimension -> {
            distance.updateAndGet(v -> new Float(v + dimension));
        });
        return (float) Math.sqrt(distance.get());
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

    /**
     * Gets the value of largest of the axis, exclusive of positive of negatives.
     * @return Value of largest axis.
     */
    public float largest() {
        AtomicReference<Float> highest = new AtomicReference<>((float) 0);
        dimensions.forEach((dim) -> {
            if (dim * dim > highest.get() * highest.get()) {
                highest.set(dim);
            }
        });
        return highest.get();
    }

    /**
     * Gets the value of the axis of <code>index</code>.
     * @param index The index of the value that you are checking
     * @return The value of the index.
     */
    public float getAxis(int index) {
        return dimensions.get(index);
    }

    /**
     * Sets the value of index <code>index</code>.
     * @param index The index of which you want to change the value.
     * @param value The value to change to.
     */
    public void setAxis(int index, float value) {
        dimensions.set(index, value);
    }
}