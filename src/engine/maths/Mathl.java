package engine.maths;

/**
 * Handles custom math operations.
 *
 * TODO Makes Mathl more memory efficient.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Mathl {
    /**
     * Gets an array of floats and returns the largest value exclusive of positive or negative.
     * @param list The array of floats.
     * @return The value of the largest item in the array.
     */
    public static float maxVX(float[] list) {
        float highest = 0;
        for (float item : list) {
            if (Math.abs(item) > Math.abs(highest)) {
                highest = item;
            }
        }
        return highest;
    }
    /**
     * Gets an array of doubles and returns the largest value exclusive of positive or negative.
     * @param list The array of doubles.
     * @return The value of the largest item in the array.
     */
    public static double maxVX(double[] list) {
        double highest = 0;
        for (double item : list) {
            if (Math.abs(item) > Math.abs(highest)) {
                highest = item;
            }
        }
        return highest;
    }
    /**
     * Gets an array of floats and returns the largest value.
     * @param list The array of floats.
     * @return The value of the largest item in the array.
     */
    public static float maxV(float[] list) {
        float highest = 0;
        for (float item : list) {
            if (item > highest) {
                highest = item;
            }
        }
        return highest;
    }
    /**
     * Gets an array of doubles and returns the largest value.
     * @param list The array of doubles.
     * @return The value of the largest item in the array.
     */
    public static double maxV(double[] list) {
        double highest = 0;
        for (double item : list) {
            if (item > highest) {
                highest = item;
            }
        }
        return highest;
    }

    /**
     * Gets an array of floats and returns the largest index exclusive of positive or negative.
     * @param list The array of floats.
     * @return The index of the largest item in the array.
     */
    public static int maxIX(float[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (Math.abs(list[i] ) > Math.abs(list[lowest])) {
                lowest = i;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of doubles and returns the largest index exclusive of positive or negative.
     * @param list The array of doubles.
     * @return The index of the largest item in the array.
     */
    public static int maxIX(double[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] * list[i] > list[lowest] * list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of floats and returns the largest index.
     * @param list The array of floats.
     * @return The index of the largest item in the array.
     */
    public static int maxI(float[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] > list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of doubles and returns the largest index.
     * @param list The array of doubles.
     * @return The index of the largest item in the array.
     */
    public static int maxI(double[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] > list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }

    /**
     * Gets an array of floats and returns the smallest value exclusive of positive or negative.
     * @param list The array of floats.
     * @return The value of the smallest item in the array.
     */
    public static float minVX(float[] list) {
        float lowest = list[0];
        for (float item : list) {
            if (item * item < lowest * lowest) {
                lowest = item;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of doubles and returns the smallest value exclusive of positive or negative.
     * @param list The array of doubles.
     * @return The value of the smallest item in the array.
     */
    public static double minVX(double[] list) {
        double lowest = list[0];
        for (double item : list) {
            if (item * item < lowest * lowest) {
                lowest = item;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of floats and returns the smallest value.
     * @param list The array of floats.
     * @return The value of the smallest item in the array.
     */
    public static float minV(float[] list) {
        float lowest = list[0];
        for (float item : list) {
            if (item < lowest) {
                lowest = item;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of doubles and returns the smallest value.
     * @param list The array of doubles.
     * @return The value of the smallest item in the array.
     */
    public static double minV(double[] list) {
        double lowest = list[0];
        for (double item : list) {
            if (item < lowest) {
                lowest = item;
            }
        }
        return lowest;
    }

    /**
     * Gets an array of floats and returns the smallest index exclusive of positive or negative.
     * @param list The array of floats.
     * @return The index of the smallest item in the array.
     */
    public static int minIX(float[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] * list[i] < list[lowest] * list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of doubles and returns the smallest index exclusive of positive or negative.
     * @param list The array of doubles.
     * @return The index of the smallest item in the array.
     */
    public static int minIX(double[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] * list[i] < list[lowest] * list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of floats and returns the smallest index.
     * @param list The array of floats.
     * @return The index of the smallest item in the array.
     */
    public static int minI(float[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] < list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }
    /**
     * Gets an array of doubles and returns the smallest index.
     * @param list The array of doubles.
     * @return The index of the smallest item in the array.
     */
    public static int minI(double[] list) {
        int lowest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] < list[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }


}
