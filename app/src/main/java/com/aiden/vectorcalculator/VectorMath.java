package com.aiden.vectorcalculator;

public class VectorMath {
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
}
