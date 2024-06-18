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
        int pos = (int) posVec.x;
        int dir = (int) dirVec.x;

        if (posVec.x == 0) {
            if (dirVec.x == 0) {
                return "x=0";
            }
            if (dir == dirVec.x) {
                return "x=" + dir + "s";
            }
            return "x=" + dirVec.x + "s";
        }
        if (dirVec.x == 0) {
            if (pos == posVec.x) {
                return "x=" + pos;
            }
            return "x=" + posVec.x;
        }
        if (dirVec.x > 0) {
            if (pos == posVec.x) {
                if (dir == dirVec.x) {
                    return "x=" + pos + "+" + dir + "s";
                }
                return "x=" + pos + "+" + dirVec.x + "s";
            }
            if (dir == dirVec.x) {
                return "x=" + posVec.x + "+" + dir + "s";
            }
            return "x=" + posVec.x + "+" + dirVec.x + "s";
        }
        if (pos == posVec.x) {
            if (dir == dirVec.x) {
                return "x=" + pos + dir + "s";
            }
            return "x=" + pos + dirVec.x + "s";
        }
        if (dir == dirVec.x) {
            return "x=" + posVec.x + dir + "s";
        }
        return "x=" + posVec.x + dirVec.x + "s";
    }

    public String paraYLatex() {
        int pos = (int) posVec.y;
        int dir = (int) dirVec.y;

        if (posVec.y == 0) {
            if (dirVec.y == 0) {
                return "y=0";
            }
            if (dir == dirVec.y) {
                return "y=" + dir + "s";
            }
            return "y=" + dirVec.y + "s";
        }
        if (dirVec.y == 0) {
            if (pos == posVec.y) {
                return "y=" + pos;
            }
            return "y=" + posVec.y;
        }
        if (dirVec.y > 0) {
            if (pos == posVec.y) {
                if (dir == dirVec.y) {
                    return "y=" + pos + "+" + dir + "s";
                }
                return "y=" + pos + "+" + dirVec.y + "s";
            }
            if (dir == dirVec.y) {
                return "y=" + posVec.y + "+" + dir + "s";
            }
            return "y=" + posVec.y + "+" + dirVec.y + "s";
        }
        if (pos == posVec.y) {
            if (dir == dirVec.y) {
                return "y=" + pos + dir + "s";
            }
            return "y=" + pos + dirVec.y + "s";
        }
        if (dir == dirVec.y) {
            return "y=" + posVec.y + dir + "s";
        }
        return "y=" + posVec.y + dirVec.y + "s";
    }

    public String paraZLatex() {
        int pos = (int) posVec.z;
        int dir = (int) dirVec.z;

        if (posVec.z == 0) {
            if (dirVec.z == 0) {
                return "z=0";
            }
            if (dir == dirVec.z) {
                return "z=" + dir + "s";
            }
            return "z=" + dirVec.z + "s";
        }
        if (dirVec.z == 0) {
            if (pos == posVec.z) {
                return "z=" + pos;
            }
            return "z=" + posVec.z;
        }
        if (dirVec.z > 0) {
            if (pos == posVec.z) {
                if (dir == dirVec.z) {
                    return "z=" + pos + "+" + dir + "s";
                }
                return "z=" + pos + "+" + dirVec.z + "s";
            }
            if (dir == dirVec.z) {
                return "z=" + posVec.z + "+" + dir + "s";
            }
            return "z=" + posVec.z + "+" + dirVec.z + "s";
        }
        if (pos == posVec.z) {
            if (dir == dirVec.z) {
                return "z=" + pos + dir + "s";
            }
            return "z=" + pos + dirVec.z + "s";
        }
        if (dir == dirVec.z) {
            return "z=" + posVec.z + dir + "s";
        }
        return "z=" + posVec.z + dirVec.z + "s";
    }

    // TODO: Work on handling signs and division by 1
    // TODO: Either change to integers or check if decimal can be hidden for whole numbers
    public String symmEqnLatex() {
        String latex = "";
        // X part
        if (dirVec.x != 0) {
            if (posVec.x == 0) {
                latex += "\\frac{x}{" + String.valueOf(dirVec.x + "}");
            }
            else {
                String top = "x";
                if (posVec.x > 0) {
                    top += "+";
                }
                top += String.valueOf(posVec.x);

                if (dirVec.x == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + String.valueOf(dirVec.x) + "}";
                }
            }
        }
        // Y part
        if (dirVec.y != 0) {
            if (!latex.isEmpty()) latex += "=";
            if (posVec.y == 0) {
                latex += "\\frac{y}{" + String.valueOf(dirVec.y + "}");
            }
            else {
                String top = "y";
                if (posVec.y > 0) {
                    top += "+";
                }
                top += String.valueOf(posVec.y);

                if (dirVec.y == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + String.valueOf(dirVec.y) + "}";
                }
            }
        }
        // Z part
        if (dirVec.z != 0) {
            if (!latex.isEmpty()) latex += "=";
            if (posVec.z == 0) {
                latex += "\\frac{z}{" + String.valueOf(dirVec.z + "}");
            }
            else {
                String top = "z";
                if (posVec.z > 0) {
                    top += "+";
                }
                top += String.valueOf(posVec.z);

                if (dirVec.z == 1) {
                    latex += top;
                }
                else {
                    latex += "\\frac{" + top + "}{" + String.valueOf(dirVec.z) + "}";
                }
            }
        }

        return latex;
    }
}