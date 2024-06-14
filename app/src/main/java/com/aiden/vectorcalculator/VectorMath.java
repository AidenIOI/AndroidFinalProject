package com.aiden.vectorcalculator;

public class VectorMath {
    public enum Intersections {
        NONE,
        POINT,
        LINE,
        PLANE
    }
    public static double dotProduct(double[] vec1, double[] vec2) {
        return    vec1[0] * vec2[0]
                + vec1[1] * vec2[1]
                + vec1[2] * vec2[2];
    }

    public static double[] crossProduct(double[] vec1, double[] vec2) {
        return new double[] {
                vec1[1] * vec2[2] - vec1[2] * vec2[1],
                vec1[2] * vec2[0] - vec1[0] * vec2[2],
                vec1[0] * vec2[1] - vec1[1] * vec2[1]
        };
    }

    public static int commonFactor(double[] vector) {
        // If any of the numbers are decimal numbers, return 1
        for (int i = 0; i < 3; i++) {
            if (vector[i] != (int) vector[i]) return 1;
        }
        int minVal = (int) vector[0];

        if (vector[1] < minVal) minVal = (int) vector[1];
        if (vector[2] < minVal) minVal = (int) vector[2];

        int factor = 1;
        for (int i = 2; i < minVal / 2; i++) {
            if (vector[0] % i == 0 && vector[1] % i == 0 && vector[2] % i == 0) {
                factor = i;
            }
        }

        return factor;
    }

    private static boolean equal(double[] vect1, double[] vect2) {
        for (int i = 0; i < 3; i++) {
            if (vect1[i] != vect2[i]) return false;
        }
        return true;
    }

    public static Intersections checkLineIntersection(
            double[] line1Pos, double[] line1Dir, double[] line2Pos, double[] line2Dir) {
        double[] line1DirFactored = line1Dir;
        double[] line2DirFactored = line2Dir;

        int factor = commonFactor(line1Dir);
        if (factor > 1) {
            line1DirFactored[0] /= factor;
            line1DirFactored[1] /= factor;
            line1DirFactored[2] /= factor;
        }
        factor = commonFactor(line2Dir);
        if (factor > 1) {
            line2DirFactored[0] /= factor;
            line2DirFactored[1] /= factor;
            line2DirFactored[2] /= factor;
        }

        // Parallel lines
        if (equal(line1DirFactored, line2DirFactored)) {
            // If position vectors are equal, lines are coincident, otherwise they are skew (no sols)
            return equal(line1Pos, line2Pos) ? Intersections.LINE : Intersections.NONE;
        }
        // Non-parallel lines
        else {
            // TODO: Check if a POI exists, then solve if it does
        }
    }
}
