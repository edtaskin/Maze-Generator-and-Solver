package com.example.mazegeneratorandsolver.maze;

public enum Directions {
    TOP("top"),
    BOTTOM("bottom"),
    LEFT("left"),
    RIGHT("right");

    private final String text;

    Directions(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
