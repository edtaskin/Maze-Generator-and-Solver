package com.example.mazegeneratorandsolver;

public interface Directions {
    short TOP = 0;
    short BOTTOM = 1;
    short RIGHT = 2;
    short LEFT = 3;

    public static String toString(short direction) {
        switch (direction) {
            case TOP:
                return "top";
            case BOTTOM:
                return "bottom";
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
        }
        return null;
    }
}
