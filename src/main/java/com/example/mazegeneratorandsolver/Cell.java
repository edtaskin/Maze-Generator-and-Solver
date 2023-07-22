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
            walls.put(side, true); // Initially all walls are placed
        pane.setStyle("-fx-border-color: black");
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
        String borderStyle = "-fx-border-color: red;"; // top, right, bottom, left
        for (short direction : walls.keySet()) {
            switch (direction) {
                case TOP:
                    this.pane.setStyle(borderStyle + "-fx-border-width: 1px 0px 0px 0px;");
                    break;
                case BOTTOM:
                    this.pane.setStyle(borderStyle + "-fx-border-width: 0px 0px 1px 0px;");
                    break;
                case RIGHT:
                    this.pane.setStyle(borderStyle + "-fx-border-width: 0px 1px 0px 0px;");
                    break;
                case LEFT:
                    this.pane.setStyle(borderStyle + "-fx-border-width: 0px 0px 0px 1px;");
                    break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("x: %d, y: %d, Walls: [", x, y));
        for (short direction : walls.keySet())
            if (walls.get(direction)) {
                res.append(direction).append(" ");
            }
        res.append("]");
        return String.valueOf(res);
    }


}
