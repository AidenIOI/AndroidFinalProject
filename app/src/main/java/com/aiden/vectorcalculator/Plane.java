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
        int pos = (int) posVec.x;
        int dir1 = (int) dir1Vec.x;
        int dir2 = (int) dir2Vec.x;

        if (posVec.x != 0) {
            if (pos == posVec.x) {
                latex += pos;
            }
            else {
                latex += posVec.x;
            }
        }
        if (dir1Vec.x < 0 || (latex.isEmpty() && dir1Vec.x > 0)) {
            if (dir1 == dir1Vec.x) {
                latex += dir1 + "s";
            }
            else {
                latex += dir1Vec.x + "s";
            }
        }
        else if (dir1Vec.x > 0) {
            if (dir1 == dir1Vec.x) {
                latex += "+" + dir1 + "s";
            }
            else {
                latex += "+" + dir1Vec.x + "s";
            }
        }
        if (dir2Vec.x < 0 || (latex.isEmpty() && dir2Vec.x > 0)) {
            if (dir2 == dir2Vec.x) {
                latex += dir2 + "t";
            }
            else {
                latex += dir2Vec.x + "t";
            }
        }
        else if (dir2Vec.x > 0) {
            if (dir2 == dir2Vec.x) {
                latex += "+" + dir2 + "t";
            }
            else {
                latex += "+" + dir2Vec.x + "t";
            }
        }
        return "x=" + (latex.isEmpty() ? "0" : latex);
    }

    public String paraYLatex() {
        String latex = "";
        int pos = (int) posVec.y;
        int dir1 = (int) dir1Vec.y;
        int dir2 = (int) dir2Vec.y;

        if (posVec.y != 0) {
            if (pos == posVec.y) {
                latex += pos;
            }
            else {
                latex += posVec.y;
            }
        }
        if (dir1Vec.y < 0 || (latex.isEmpty() && dir1Vec.y > 0)) {
            if (dir1 == dir1Vec.y) {
                latex += dir1 + "s";
            }
            else {
                latex += dir1Vec.y + "s";
            }
        }
        else if (dir1Vec.y > 0) {
            if (dir1 == dir1Vec.y) {
                latex += "+" + dir1 + "s";
            }
            else {
                latex += "+" + dir1Vec.y + "s";
            }
        }
        if (dir2Vec.y < 0 || (latex.isEmpty() && dir2Vec.y > 0)) {
            if (dir2 == dir2Vec.y) {
                latex += dir2 + "t";
            }
            else {
                latex += dir2Vec.y + "t";
            }
        }
        else if (dir2Vec.y > 0) {
            if (dir2 == dir2Vec.y) {
                latex += "+" + dir2 + "t";
            }
            else {
                latex += "+" + dir2Vec.y + "t";
            }
        }
        return "y=" + (latex.isEmpty() ? "0" : latex);
    }

    public String paraZLatex() {
        String latex = "";
        int pos = (int) posVec.z;
        int dir1 = (int) dir1Vec.z;
        int dir2 = (int) dir2Vec.z;

        if (posVec.z != 0) {
            if (pos == posVec.z) {
                latex += pos;
            }
            else {
                latex += posVec.z;
            }
        }
        if (dir1Vec.z < 0 || (latex.isEmpty() && dir1Vec.z > 0)) {
            if (dir1 == dir1Vec.z) {
                latex += dir1 + "s";
            }
            else {
                latex += dir1Vec.z + "s";
            }
        }
        else if (dir1Vec.z > 0) {
            if (dir1 == dir1Vec.z) {
                latex += "+" + dir1 + "s";
            }
            else {
                latex += "+" + dir1Vec.z + "s";
            }
        }
        if (dir2Vec.z < 0 || (latex.isEmpty() && dir2Vec.z > 0)) {
            if (dir2 == dir2Vec.z) {
                latex += dir2 + "t";
            }
            else {
                latex += dir2Vec.z + "t";
            }
        }
        else if (dir2Vec.z > 0) {
            if (dir2 == dir2Vec.z) {
                latex += "+" + dir2 + "t";
            }
            else {
                latex += "+" + dir2Vec.z + "t";
            }
        }
        return "z=" + (latex.isEmpty() ? "0" : latex);
    }

    public String scalarEqnLatex() {
        Vector normal = normVec;

        String latex = "";
        if (normal.x != 0) {
            latex += String.valueOf(normal.x) + "x";
        }
        if (normal.y < 0 || (latex.isEmpty() && normal.y != 0)) {
            latex += String.valueOf(normal.y) + "y";
        }
        else if (normal.y > 0) {
            latex += "+" + String.valueOf(normal.y) + "y";
        }
        if (normal.z < 0 || (latex.isEmpty() && normal.z != 0)) {
            latex += String.valueOf(normal.z) + "z";
        }
        else if (normal.y > 0) {
            latex += "+" + String.valueOf(normal.z) + "z";
        }
        if (D < 0 || (latex.isEmpty() && D != 0)) {
            latex += String.valueOf(D);
        }
        else if (D > 0) {
            latex += "+" + String.valueOf(D);
        }

        return latex.isEmpty() ? "" : "0=" + latex ;
    }
}