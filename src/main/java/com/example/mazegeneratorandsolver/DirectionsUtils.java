package com.example.mazegeneratorandsolver;

public class DirectionsUtils implements Directions {

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
