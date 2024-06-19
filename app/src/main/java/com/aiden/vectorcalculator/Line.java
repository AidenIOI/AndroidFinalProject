package com.aiden.vectorcalculator;

public class Line {
    private Vector posVec;
    private Vector dirVec;

    Line() {
        posVec = new Vector();
        dirVec = new Vector();
    }

    Line(double posX, double posY, double posZ, double dirX, double dirY, double dirZ) {
        posVec = new Vector(posX, posY, posZ);
        dirVec = new Vector(dirX, dirY, dirZ);
    }

    Line(double[] pos, double[] dir) {
        posVec = new Vector(pos);
        dirVec = new Vector(dir);
    }

    Line(Vector pos, Vector dir) {
        posVec = pos;
        dirVec = dir;
    }

    public Vector posVec() {
        return posVec;
    }

    public Vector dirVec() {
        return dirVec;
    }

    public String vectorEqnLatex() {
        return "[x,y,z]=" + posVec.toLatex() + "+s" + dirVec.toLatex();
    }

    public String paraXLatex() {
        String latex = "";

        if (posVec.x != 0) {
            latex += VectorMath.valueOf(posVec.x);
        }
        if (dirVec.x < 0 || (latex.isEmpty() && dirVec.x > 0)) {
            latex += VectorMath.valueOf(dirVec.x) + "s";
        }
        else if (dirVec.x > 0) {
            latex += "+" + VectorMath.valueOf(dirVec.x) + "s";
        }

        return "x=" + (latex.isEmpty() ? "0" : latex);
    }

    public String paraYLatex() {
        String latex = "";

        if (posVec.y != 0) {
            latex += VectorMath.valueOf(posVec.y);
        }
        if (dirVec.y < 0 || (latex.isEmpty() && dirVec.y > 0)) {
            latex += VectorMath.valueOf(dirVec.y) + "s";
        }
        else if (dirVec.y > 0) {
            latex += "+" + VectorMath.valueOf(dirVec.y) + "s";
        }

        return "y=" + (latex.isEmpty() ? "0" : latex);
    }

    public String paraZLatex() {
        String latex = "";

        if (posVec.z != 0) {
            latex += VectorMath.valueOf(posVec.z);
        }
        if (dirVec.z < 0 || (latex.isEmpty() && dirVec.z > 0)) {
            latex += VectorMath.valueOf(dirVec.z) + "s";
        }
        else if (dirVec.z > 0) {
            latex += "+" + VectorMath.valueOf(dirVec.z) + "s";
        }

        return "z=" + (latex.isEmpty() ? "0" : latex);
    }

    public String symmEqnLatex() {
        String latex = "";
        // X part
        if (dirVec.x != 0) {
            if (posVec.x == 0) {
                latex += "\\frac{x}{" + VectorMath.valueOf(dirVec.x) + "}";
            }
            else {
                String top = "x";
                if (posVec.x > 0) {
                    top += "+";
                }
                top += VectorMath.valueOf(posVec.x);

                if (dirVec.x == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + VectorMath.valueOf(dirVec.x) + "}";
                }
            }
        }
        // Y part
        if (dirVec.y != 0) {
            if (!latex.isEmpty()) latex += "=";
            if (posVec.y == 0) {
                latex += "\\frac{y}{" + VectorMath.valueOf(dirVec.y) + "}";
            }
            else {
                String top = "y";
                if (posVec.y > 0) {
                    top += "+";
                }
                top += VectorMath.valueOf(posVec.y);

                if (dirVec.y == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + VectorMath.valueOf(dirVec.y) + "}";
                }
            }
        }
        // Z part
        if (dirVec.z != 0) {
            if (!latex.isEmpty()) latex += "=";
            if (posVec.z == 0) {
                latex += "\\frac{z}{" + VectorMath.valueOf(dirVec.z) + "}";
            }
            else {
                String top = "z";
                if (posVec.z > 0) {
                    top += "+";
                }
                top += VectorMath.valueOf(posVec.z);

                if (dirVec.z == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + VectorMath.valueOf(dirVec.z) + "}";
                }
            }
        }

        return latex;
    }
}