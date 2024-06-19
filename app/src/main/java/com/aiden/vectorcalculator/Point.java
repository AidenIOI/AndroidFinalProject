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
        latex += VectorMath.valueOf(x);
        latex += ",";
        latex += VectorMath.valueOf(y);
        latex += ",";
        latex += VectorMath.valueOf(z);
        latex += ")";
        return latex;
    }
}