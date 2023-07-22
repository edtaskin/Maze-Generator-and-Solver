package com.example.mazegeneratorandsolver;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class Cell implements Directions {
    private static final int cellWidth = 30, cellHeight = 30;

    private Map<Short, Boolean> walls; // TODO Is it functional?
    private final int row, col;
    private Pane pane;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        walls = new HashMap<>();
        pane = new Pane();
        pane.setOnMouseClicked(event -> System.out.println(pane.getStyleClass().toString()));
        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            walls.put(side, true); // Initially all walls are placed
        pane.getStyleClass().add("cell");
        pane.getStyleClass().add("filled");
        pane.getStyleClass().add("top");
        pane.getStyleClass().add("bottom");
        pane.getStyleClass().add("left");
        pane.getStyleClass().add("right");
        pane.setPrefSize(cellWidth, cellHeight);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Pane getPane() { return pane; }

    public void openCell(short direction) {
        pane.getStyleClass().remove("filled");
        if (!pane.getStyleClass().contains("opened"))
            pane.getStyleClass().add("opened");
        walls.replace(direction, false);
        displayWalls();
        /*
        System.out.println("-----");
        System.out.println(this.toString());
        System.out.println(pane.getStyleClass().toString());
        */
    }

    /*
    public void putWall(short direction) {
        walls.replace(direction, true);
        displayWalls();
    }

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
    */

    // TODO Are the styles valid?
    private void displayWalls() {
        for (short direction : walls.keySet()) {
            if (walls.get(direction)) {
                if (!pane.getStyleClass().contains(DirectionsUtils.toString(direction)))
                    pane.getStyleClass().add(DirectionsUtils.toString(direction));
            }
            else
                pane.getStyleClass().remove(DirectionsUtils.toString(direction));
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("Row: %d, Col: %d, Walls: [", row, col));
        int i = 0;
        for (short direction : walls.keySet()) {
            if (walls.get(direction)) {
                if (i != walls.keySet().size() - 1)
                    res.append(DirectionsUtils.toString(direction)).append(", ");
                else
                    res.append(DirectionsUtils.toString(direction));
            }
            i++;
        }
        res.append("]");
        return String.valueOf(res);
    }


}
