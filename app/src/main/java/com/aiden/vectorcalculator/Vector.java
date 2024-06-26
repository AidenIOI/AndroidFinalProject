package com.aiden.vectorcalculator;

public class Vector {
    public double x;
    public double y;
    public double z;

    Vector() {
        x = y = z = 0;
    }

    Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vector(double[] array) {
        x = array[0];
        y = array[1];
        z = array[2];
    } 

    public void fill(double[] array) {
        x = array[0];
        y = array[1];
        z = array[2];
    }

    public Vector copy() {
        return new Vector(x, y, z);
    }

    public boolean isFactorable() {
        // Factorable if all numbers are whole and greater than 1 (or less than -1), or zero
        if (x == (int) x && y == (int) y && z == (int) z) {
            if ((Math.abs(x) > 1 || x == 0) && (Math.abs(y) > 1 || y == 0) && (Math.abs(z) > 1 || z == 0)) {
                return true;
            }
        }
        return false;
    }

    public String toLatex() {
        String latex = "[";
        latex += VectorMath.valueOf(x);
        latex += ",";
        latex += VectorMath.valueOf(y);
        latex += ",";
        latex += VectorMath.valueOf(z);
        latex += "]";
        return latex;
    }
}