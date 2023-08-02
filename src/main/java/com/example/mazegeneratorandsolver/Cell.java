package com.example.mazegeneratorandsolver;

import javafx.animation.FillTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cell extends BorderPane implements Directions {
    public static final int CELL_WIDTH = 30, CELL_HEIGHT = 30, BORDER_WIDTH = 2;
    private static final Color BORDER_COLOR = Color.RED;

    private Map<Short, Boolean> wallsMap;
    private final int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();
        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            wallsMap.put(side, true); // Initially all walls are placed
        setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        setStyle("-fx-background-color: black;");
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public double[] getTopLeftCoordinates(int rowCount, int colCount) {
        double x = col * CELL_WIDTH;
        double y = row * CELL_HEIGHT;
        return new double[] {x, y};
    }

    public void openCell(short direction) {
        wallsMap.replace(direction, false);
        displayWalls();
    }


    private void displayWalls() {
        getChildren().clear();
        for (short direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                Rectangle wall = null;
                switch (direction) {
                    case TOP:
                        wall = new Rectangle(CELL_WIDTH, BORDER_WIDTH);
                        setTop(wall);
                        break;
                    case BOTTOM:
                        wall = new Rectangle(CELL_WIDTH, BORDER_WIDTH);
                        setBottom(wall);
                        break;
                    case LEFT:
                        wall = new Rectangle(BORDER_WIDTH, CELL_HEIGHT);
                        setLeft(wall);
                        break;
                    case RIGHT:
                        wall = new Rectangle(BORDER_WIDTH, CELL_HEIGHT);
                        setRight(wall);
                        break;
                }
                wall.setFill(BORDER_COLOR);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("Row: %d, Col: %d, Walls: [", row, col));
        int i = 0;
        for (short direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                if (i != wallsMap.keySet().size() - 1)
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
