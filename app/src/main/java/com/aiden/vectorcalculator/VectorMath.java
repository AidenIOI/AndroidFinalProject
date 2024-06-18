package com.aiden.vectorcalculator;

import android.util.Log;

public class VectorMath {
    public enum Intersections {
        SKEW,
        PAIRS,
        DISTINCT,
        POINT,
        LINE,
        COINCIDENT
    }
    public static double dotProduct(Vector vec1, Vector vec2) {
        return    vec1.x * vec2.x
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

        int absX = Math.abs((int) vector.x);
        int absY = Math.abs((int) vector.y);
        int absZ = Math.abs((int) vector.z);

        int minVal = absX;

        if ((absY < minVal && absY != 0) || minVal == 0) minVal = absY;
        if ((absZ < minVal && absZ != 0) || minVal == 0) minVal = absZ;

        int factor = 1;
        for (int i = 2; i <= minVal; i++) {
            if (vector.x % i == 0 && vector.y % i == 0 && vector.z % i == 0) {
                factor = i;
            }
        }

        return factor;
    }

    private static boolean equal(Vector vect1, Vector vect2) {
        return ((vect1.x == vect2.x && vect1.y == vect2.y && vect1.z== vect2.z)
                || (vect1.x == -vect2.x && vect1.y == -vect2.y && vect1.z== -vect2.z));
    }

    private static boolean pointOnLine (Vector point, Line line) {
        double tx;
        double ty;
        double tz;

        tx = (point.x - line.posVec().x) / line.dirVec().x;
        ty = (point.y - line.posVec().y) / line.dirVec().y;
        tz = (point.z - line.posVec().z) / line.dirVec().z;

        if (Double.isNaN(tx)) {
            if (Double.isNaN(ty)) {
                return true;
            }
            if (Double.isNaN(tz)) {
                return true;
            }
            return ty == tz;
        }
        if (Double.isNaN(ty)) {
            if (Double.isNaN(tz)) {
                return true;
            }
            return tx == tz;
        }
        if (Double.isNaN(tz)) {
            return tx == ty;
        }
        return tx == ty && ty == tz;
    }

    private static boolean pointOnPlane (Vector point, Plane plane) {
        Vector normal = plane.normVec();

        Log.println(Log.INFO, "VectorMath", point.toLatex());

        return normal.x * point.x + normal.y * point.y + normal.z * point.z + plane.D() == 0;
    }

    private static double[] findST(Line line1, Line line2) {
        double[] st = new double[2];
        Vector pos1 = line1.posVec();
        Vector pos2 = line2.posVec();
        Vector dir1 = line1.dirVec();
        Vector dir2 = line2.dirVec();
        // General equation for t
        st[1] = (dir1.x * (pos1.y - pos2.y) + pos2.x - pos1.x) / ((dir2.y * dir1.x) - (dir2.x * dir1.y));
        // Use t to solve for s
        st[0] = (pos2.x + st[1] * dir2.x - pos1.x) / dir1.x;

        return st;
    }

    public static Point getPOI(Line line1, Line line2) {
        double[] st = findST(line1, line2);

        double x = line1.posVec().x + st[0] * line1.dirVec().x;
        double y = line1.posVec().y + st[0] * line1.dirVec().y;
        double z = line1.posVec().z + st[0] * line1.dirVec().z;

        return new Point(x, y, z);
    }

    public static Point getPOI(Plane plane1, Plane plane2, Plane plane3) {
        // Eliminate x
        Vector norm1 = plane1.normVec();
        Vector norm2 = plane2.normVec();
        Vector norm3 = plane3.normVec();

        // Plane 1 = Plane 2
        double mul1 = norm1.x / norm2.x;
        double elim1y = norm1.y - norm2.y * mul1;
        double elim1z = norm1.z - norm2.z * mul1;
        double elim1D = plane1.D() - plane2.D() * mul1;

        // Plane 1 = Plane 3
        double mul2 = norm1.x / norm3.x;
        double elim2y = norm1.y - norm3.y * mul2;
        double elim2z = norm1.z - norm3.z * mul2;
        double elim2D = plane1.D() - plane3.D() * mul2;

        // Solve for z
        double mul3 = elim1y / elim2y;
        double z = - (elim1D - (elim2D * mul3)) / (elim1z - (elim2z * mul3));

        // Solve for remaining values
        double y = - (elim1D + elim1z * z) / elim1y;
        double x = - (norm1.y * y + norm1.z * z + plane1.D()) / norm1.x;

        return new Point(x, y, z);
    }

    public static Line getLOI(Plane plane1, Plane plane2) {
       Vector norm1 = plane1.normVec();
       double a1 = norm1.x;
       double b1 = norm1.y;
       double c1 = norm1.z;
       double d1 = plane1.D();
       Vector norm2 = plane2.normVec();
       double a2 = norm2.x;
       double b2 = norm2.y;
       double c2 = norm2.z;
       double d2 = plane2.D();

       double mul = a1 / a2;
       double denom = c1 - (c2 * mul);

       double Zt = ((b2 * mul) - b1) / denom;
       double Zp = ((d2 * mul) - d1) / denom;
       double Xt = -((c1 * Zt) + b1) / a1;
       double Xp = -((c1 * Zp) + d1) / a1;

       return new Line(Xp, 0, Zp, Xt, 1, Zt);
    }

    public static Intersections intersection(Line line1, Line line2) {
        Vector line1DirFactored = line1.dirVec().copy();
        Vector line2DirFactored = line2.dirVec().copy();

        int factor = commonFactor(line1.dirVec());
        if (factor > 1) {
            line1DirFactored.x /= factor;
            line1DirFactored.y /= factor;
            line1DirFactored.z /= factor;
        }
        factor = commonFactor(line2.dirVec());
        if (factor > 1) {
            line2DirFactored.x /= factor;
            line2DirFactored.y /= factor;
            line2DirFactored.z /= factor;
        }
        // Parallel lines
        if (equal(line1DirFactored, line2DirFactored)) {
            // If a point from line 1 exists on line 2, they must be coincident
            return pointOnLine(line1.posVec(), line2) ? Intersections.COINCIDENT : Intersections.DISTINCT;
        }
        // Non-parallel lines
        else {
            double[] st = findST(line1, line2);

            // If the given S and T values do not work for the third equation,
            // there is no solution
            if (line2.posVec().z - line1.posVec().z
                    != (st[0] * line1.dirVec().z) - (st[1] * line2.dirVec().z)) {
                return Intersections.SKEW;
            }
            return Intersections.POINT;
        }
    }

    public static Intersections intersection(Plane plane1, Plane plane2) {
        // Possibilities:   Parallel and distinct: no sols
        //                  Coincident: infinite sols (plane)
        //                  Non-parallel: infinite sols (line)
        Vector norm1Factored = plane1.normVec().copy();
        Vector norm2Factored = plane2.normVec().copy();

        int factor = commonFactor(plane1.normVec());
        if (factor > 1) {
            norm1Factored.x /= factor;
            norm1Factored.y /= factor;
            norm1Factored.z /= factor;
        }
        factor = commonFactor(plane2.normVec());
        if (factor > 1) {
            norm2Factored.x /= factor;
            norm2Factored.y /= factor;
            norm2Factored.z /= factor;
        }

        // Parallel planes
        if (equal(norm1Factored, norm2Factored)) {
            // Coincident - Plane, Distinct - No Solutions
            return pointOnPlane(plane1.posVec(), plane2) ? Intersections.COINCIDENT : Intersections.DISTINCT;
        }

        // Non-parallel
        return Intersections.LINE;
    }

    public static Intersections intersection(Plane plane1, Plane plane2, Plane plane3) {
        Vector[] factoredNorms = {plane1.normVec().copy(), plane2.normVec().copy(), plane3.normVec().copy()};

        for (Vector normal : factoredNorms) {
            int factor = commonFactor(normal);
            if (factor > 1) {
                normal.x /= factor;
                normal.y /= factor;
                normal.z /= factor;
            }
        }

        // Plane 1 and 2 are parallel
        if (equal(factoredNorms[0], factoredNorms[1])) {
            // Coincident
            if (pointOnPlane(plane1.posVec(), plane2)) {
                // Solve 2 plane intersection
                return intersection(plane1, plane3);
            }
            // Parallel and distinct
            return Intersections.DISTINCT;
        }
        // Plane 1 and 3 are parallel
        if (equal(factoredNorms[0], factoredNorms[2])) {
            // Coincident
            if (pointOnPlane(plane1.posVec(), plane3)) {
                // Solve 2 plane intersection
                return intersection(plane1, plane2);
            }
            // Parallel and distinct
            return Intersections.DISTINCT;
        }
        // Plane 2 and 3 are parallel
        if (equal(factoredNorms[1], factoredNorms[2])) {
            // Coincident
            if (pointOnPlane(plane2.posVec(), plane3)) {
                // Solve 2 plane intersection
                return intersection(plane2, plane1);
            }
            // Parallel and distinct
            return Intersections.DISTINCT;
        }
        // Planes are coplanar
        if (dotProduct(factoredNorms[0], crossProduct(factoredNorms[1], factoredNorms[2])) == 0) {
            // Eliminate 1 variable
            Vector norm1 = plane1.normVec();
            Vector norm2 = plane2.normVec();
            Vector norm3 = plane3.normVec();

            // Plane 1 = Plane 2
            double mul1 = norm1.x / norm2.x;
            double elim1y = norm1.y - norm2.y * mul1;
            double elim1z = norm1.z - norm2.z * mul1;
            double elim1D = plane1.D() - plane2.D() * mul1;

            // Plane 1 = Plane 3
            double mul2 = norm1.x / norm3.x;
            double elim2y = norm1.y - norm3.y * mul2;
            double elim2z = norm1.z - norm3.z * mul2;
            double elim2D = plane1.D() - plane3.D() * mul2;

            double mul3 = elim1y / elim2y;
            if (elim1z == elim2z * mul3 && elim1D == elim2D * mul3) {
                return Intersections.LINE;
            }
            // Planes intersect in pairs, no solutions
            return Intersections.PAIRS;
        }
        // Planes intersect at a point
        return Intersections.POINT;
    }
}
