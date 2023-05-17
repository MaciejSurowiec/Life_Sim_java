package com.maciejsurowiec.lifesim;

import java.lang.Math;

public class Vector {

    public static final Vector ZERO = new Vector(0,0);
    public static final Vector DOWN = new Vector(0,1);
    public static final Vector UP = new Vector(0,-1);
    public static final Vector LEFT = new Vector(-1,0);
    public static final Vector RIGHT = new Vector(1,0);
    static public final int DIRECTIONS = 8;

    public int x;
    public int y;

    Vector(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector add(Vector one , Vector two) { return new Vector(one.x + two.x,one.y + two.y); }

    public static Vector sub(Vector one , Vector two) { return new Vector(one.x - two.x,one.y - two.y); }

    public static boolean compare(Vector one , Vector two) { return one.x == two.x && one.y == two.y; }

    public static double length(Vector one) { return (Math.sqrt(one.x * one.x + one.y * one.y)); }

    public Vector goToTarget() {
        if (x == 0) {
            if (y < 0) return UP;
            else return DOWN;
        } else if (y == 0) {
            if (x < 0) return LEFT;
            else return RIGHT;
        } else if (x > 0 && y > 0) return add(RIGHT, DOWN);
            else if (x > 0) return add(RIGHT, UP);
                else if (y > 0) {
                    return add(LEFT, DOWN);
                } else return add(LEFT, UP);
    }

    public String toString() { return (x + "," + y); }

    public static Vector fromString(String s) {
        boolean change = true;
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ',') {
                change = false;
            } else if (change) {
                sumX *= 10;
                sumX += Integer.parseInt(String.valueOf(c));
            } else {
                sumY *= 10;
                sumY += Integer.parseInt(String.valueOf(c));
            }
        }

        return new Vector(sumX, sumY);
    }

    public static Vector direction(int n) {
        return switch (n % 8) {
            case 0 -> UP;
            case 1 -> add(UP, LEFT);
            case 2 -> LEFT;
            case 3 -> add(DOWN, LEFT);
            case 4 -> DOWN;
            case 5 -> add(DOWN, RIGHT);
            case 6 -> RIGHT;
            case 7 -> add(UP, RIGHT);
            default -> ZERO;
        };
    }
}