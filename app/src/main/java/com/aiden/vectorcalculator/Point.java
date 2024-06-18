package com.aiden.vectorcalculator;

public class Point extends Vector{

    public Point() {
        super();
    }

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    public Point(double[] array) {
        super(array);
    }

    @Override
    public String toLatex() {
        String latex = "(";
        if (x == (int) x) {
            latex += String.valueOf((int) x);
        }
        else {
            latex += String.valueOf(((int) (x * 10)) / 10.0);
        }
        latex += ",";
        if (y == (int) y) {
            latex += String.valueOf((int) y);
        }
        else {
            latex += String.valueOf(((int) (y * 10)) / 10.0);
        }
        latex += ",";
        if (z == (int) z) {
            latex += String.valueOf((int) z);
        }
        else {
            latex += String.valueOf(((int) (z * 10)) / 10.0);
        }
        latex += ")";
        return latex;
    }
}