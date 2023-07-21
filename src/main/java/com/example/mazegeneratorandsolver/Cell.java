package com.example.mazegeneratorandsolver;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class Cell implements Directions {
    private static final int cellWidth = 30, cellHeight = 30;

    private Map<Short, Boolean> walls;
    private final int x, y;
    private Pane pane;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        walls = new HashMap<>();
        pane = new Pane();
        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            walls.put(side, false);
        pane.setStyle("-fx-border-color: black"); // Initially all walls are placed
        pane.setPrefSize(cellWidth, cellHeight);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Pane getPane() { return pane; }

    public void putWall(short direction) {
        walls.replace(direction, true);
        displayWalls();
    }

    // TODO Is it possible to replace short with the interface type, Directions?
    public void putWall(short[] directions) {
        for (short direction : directions)
            walls.replace(direction, true);
        displayWalls();
    }

    public void removeWall(short direction) {
        walls.replace(direction, false);
        displayWalls();
    }

    public void removeWall(short[] directions) {
        for (short direction : directions)
            walls.replace(direction, false);
        displayWalls();
    }

    // TODO Are the styles valid?
    private void displayWalls() {
        this.pane.setStyle("-fx-border-color: none");
        for (short direction : walls.keySet()) {
            switch (direction) {
                case TOP:
                    this.pane.setStyle("-fx-border-top: 1px solid black");
                    break;
                case BOTTOM:
                    this.pane.setStyle("-fx-border-bottom: 1px solid black");
                    break;
                case RIGHT:
                    this.pane.setStyle("-fx-border-right: 1px solid black");
                    break;
                case LEFT:
                    this.pane.setStyle("-fx-border-left: 1px solid black");
                    break;
            }
        }
    }


}
