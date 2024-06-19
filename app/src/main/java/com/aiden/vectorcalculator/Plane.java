package com.aiden.vectorcalculator;

import static com.aiden.vectorcalculator.VectorMath.crossProduct;

public class Plane {
    private Vector posVec;
    private Vector dir1Vec;
    private Vector dir2Vec;
    private Vector normVec;

    private double D;

    Plane() {
        posVec = new Vector();
        dir1Vec = new Vector();
        dir2Vec = new Vector();
        normVec = new Vector();
        D = 0;
    }

    Plane(double posX, double posY, double posZ, double dir1X, double dir1Y, double dir1Z, double dir2X, double dir2Y, double dir2Z) {
        posVec = new Vector(posX, posY, posZ);
        dir1Vec = new Vector(dir1X, dir1Y, dir1Z);
        dir2Vec = new Vector(dir2X, dir2Y, dir2Z);

        normVec = crossProduct(dir1Vec, dir2Vec);

        D = -((normVec.x * posVec.x) + (normVec.y * posVec.y) + (normVec.z * posVec.z));
    }

    Plane(double[] pos, double[] dir1, double[] dir2) {
        posVec = new Vector(pos);
        dir1Vec = new Vector(dir1);
        dir2Vec = new Vector(dir2);

        normVec = crossProduct(dir1Vec, dir2Vec);

        D = -((normVec.x * posVec.x) + (normVec.y * posVec.y) + (normVec.z * posVec.z));
    }

    Plane(Vector pos, Vector dir1, Vector dir2) {
        posVec = pos;
        dir1Vec = dir1;
        dir2Vec = dir2;

        normVec = crossProduct(dir1Vec, dir2Vec);

        D = -((normVec.x * posVec.x) + (normVec.y * posVec.y) + (normVec.z * posVec.z));
    }

    Plane(Vector normal, double d) {
        dir1Vec = new Vector(-normal.z, 0, normal.x);

        // Use cross product of normal and dir1Vector to find dir2Vector
        dir2Vec = crossProduct(normal, dir1Vec);

        // Solve for an arbitrary point on the plane (y = 1, z = 1, solve for x)
        posVec = new Vector (
                - (d + normal.y + normal.z) / normal.x, // Ax = -By - Cz - D = -(By + Cz + D) / A
                1,
                1
        );

        normVec = normal;

        D = d;
    }

    public Vector posVec() {
        return posVec;
    }

    public Vector dir1Vec() {
        return dir1Vec;
    }

    public Vector dir2Vec() {
        return dir2Vec;
    }

    public Vector normVec() {
        return normVec;
    }

    public double D() {
        return D;
    }

    public String vectorEqnLatex() {
        return "[x,y,z]=" + posVec.toLatex() + "+s" + dir1Vec.toLatex() + "+t" + dir2Vec.toLatex();
    }

    public String paraXLatex() {
        String latex = "";

        if (posVec.x != 0) {
            latex += VectorMath.valueOf(posVec.x);
        }
        if (dir1Vec.x < 0 || (latex.isEmpty() && dir1Vec.x > 0)) {
            latex += VectorMath.valueOf(dir1Vec.x) + "s";
        }
        else if (dir1Vec.x > 0) {
            latex += "+" + VectorMath.valueOf(dir1Vec.x) + "s";
        }
        if (dir2Vec.x < 0 || (latex.isEmpty() && dir2Vec.x > 0)) {
            latex += VectorMath.valueOf(dir2Vec.x) + "t";
        }
        else if (dir2Vec.x > 0) {
            latex += "+" + VectorMath.valueOf(dir2Vec.x) + "t";
        }

        return "x=" + (latex.isEmpty() ? "0" : latex);
    }

    public String paraYLatex() {
        String latex = "";

        if (posVec.y != 0) {
            latex += VectorMath.valueOf(posVec.y);
        }
        if (dir1Vec.y < 0 || (latex.isEmpty() && dir1Vec.y > 0)) {
            latex += VectorMath.valueOf(dir1Vec.y) + "s";
        }
        else if (dir1Vec.y > 0) {
            latex += "+" + VectorMath.valueOf(dir1Vec.y) + "s";
        }
        if (dir2Vec.y < 0 || (latex.isEmpty() && dir2Vec.y > 0)) {
            latex += VectorMath.valueOf(dir2Vec.y) + "t";
        }
        else if (dir2Vec.y > 0) {
            latex += "+" + VectorMath.valueOf(dir2Vec.y) + "t";
        }

        return "y=" + (latex.isEmpty() ? "0" : latex);
    }

    public String paraZLatex() {
        String latex = "";

        if (posVec.z != 0) {
            latex += VectorMath.valueOf(posVec.z);
        }
        if (dir1Vec.z < 0 || (latex.isEmpty() && dir1Vec.z > 0)) {
            latex += VectorMath.valueOf(dir1Vec.z) + "s";
        }
        else if (dir1Vec.z > 0) {
            latex += "+" + VectorMath.valueOf(dir1Vec.z) + "s";
        }
        if (dir2Vec.z < 0 || (latex.isEmpty() && dir2Vec.z > 0)) {
            latex += VectorMath.valueOf(dir2Vec.z) + "t";
        }
        else if (dir2Vec.z > 0) {
            latex += "+" + VectorMath.valueOf(dir2Vec.z) + "t";
        }

        return "z=" + (latex.isEmpty() ? "0" : latex);
    }

    public String scalarEqnLatex() {
        Vector normal = normVec;

        String latex = "";
        if (normal.x != 0) {
            latex += VectorMath.valueOf(normal.x) + "x";
        }
        if (normal.y < 0 || (latex.isEmpty() && normal.y != 0)) {
            latex += VectorMath.valueOf(normal.y) + "y";
        }
        else if (normal.y > 0) {
            latex += "+" + VectorMath.valueOf(normal.y) + "y";
        }
        if (normal.z < 0 || (latex.isEmpty() && normal.z != 0)) {
            latex += VectorMath.valueOf(normal.z) + "z";
        }
        else if (normal.y > 0) {
            latex += "+" + VectorMath.valueOf(normal.z) + "z";
        }
        if (D < 0 || (latex.isEmpty() && D != 0)) {
            latex += VectorMath.valueOf(D);
        }
        else if (D > 0) {
            latex += "+" + VectorMath.valueOf(D);
        }

        return latex.isEmpty() ? "" : "0=" + latex ;
    }
}
