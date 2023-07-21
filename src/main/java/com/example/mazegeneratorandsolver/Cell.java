package com.example.mazegeneratorandsolver;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class Cell implements Directions {
    private static final int cellWidth = 30, cellHeight = 30;

    private Map<Short, Boolean> walls;
    //private final int x, y;
    private Pane pane;

    public Cell(/*int x, int y*/) {
        //this.x = x;
        //this.y = y;
        walls = new HashMap<>();
        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            walls.put(side, false);
        pane.setStyle("-fx-border-color: blue"); // Initially all walls are placed
        pane.setPrefSize(cellWidth, cellHeight);
    }

    public Pane getPane() { return pane; }

    public void putWall(short direction) { walls.replace(direction, true); }

    public void putWall(short[] directions) {
        for (short direction : directions)
            walls.replace(direction, true);
    }

    public void removeWall(short direction) { walls.replace(direction, false); }

    public void removeWall(short[] directions) {
        for (short direction : directions)
            walls.replace(direction, false);
    }

    // TODO
    private void displayWalls() {
        this.pane.setStyle("-fx-border-color: none");
        for (short direction : walls.keySet()) {
            if (walls.get(direction))
                this.pane.setStyle("-fx-border-");
        }
    }


}
