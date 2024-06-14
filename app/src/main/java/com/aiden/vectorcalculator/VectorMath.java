package com.aiden.vectorcalculator;

public class VectorMath {
    public enum Intersections {
        NONE,
        POINT,
        LINE,
        PLANE
    }
    public static double dotProduct(Vector vec1, Vector vec2) {
        return    vec1.x * vec2.y
                + vec1.y * vec2.y
                + vec1.z * vec2.z;
    }

    public static Vector crossProduct(Vector vec1, Vector vec2) {
        return new Vector (
                vec1.y * vec2.z - vec1.z * vec2.y,
                vec1.z * vec2.x - vec1.x * vec2.z,
                vec1.x * vec2.y - vec1.y * vec2.x
        );
    }

    public static int commonFactor(Vector vector) {
        if (!vector.isFactorable()) return 1;
        int minVal = (int) vector.x;

        if (vector.y < minVal) minVal = (int) vector.y;
        if (vector.z < minVal) minVal = (int) vector.z;

        int factor = 1;
        for (int i = 2; i < minVal / 2; i++) {
            if (vector.x % i == 0 && vector.y % i == 0 && vector.z % i == 0) {
                factor = i;
            }
        }

        return factor;
    }

    private static boolean equal(Vector vect1, Vector vect2) {
        if (vect1.x == vect2.x && vect1.y == vect2.y && vect1.z == vect2.z) return true;
        return false;
    }

    public static Intersections checkLineIntersection(
            Vector line1Pos, Vector line1Dir, Vector line2Pos, Vector line2Dir) {
        Vector line1DirFactored = line1Dir.copy();
        Vector line2DirFactored = line2Dir.copy();

        int factor = commonFactor(line1Dir);
        if (factor > 1) {
            line1DirFactored.x /= factor;
            line1DirFactored.y /= factor;
            line1DirFactored.z /= factor;
        }
        factor = commonFactor(line2Dir);
        if (factor > 1) {
            line2DirFactored.x /= factor;
            line2DirFactored.y /= factor;
            line2DirFactored.z /= factor;
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
        return Intersections.NONE; // TODO: REMOVE LATER
    }
}
