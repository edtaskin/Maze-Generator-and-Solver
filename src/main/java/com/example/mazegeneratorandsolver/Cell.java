package com.example.mazegeneratorandsolver;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class Cell extends BorderPane implements Directions {
    public static final int CELL_WIDTH = 30, CELL_HEIGHT = 30, BORDER_WIDTH = 2;
    private static final Color BORDER_COLOR = Color.RED;

    private Map<Short, Boolean> wallsMap;
    private final int row, col;

    public Cell(int row, int col, int rowCount, int colCount) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();

        // All cells have upper walls, last row also has bottom walls
        wallsMap.put(TOP, true);
        wallsMap.put(BOTTOM, row == rowCount - 1);
        // All cells have left walls, last column also has right walls
        wallsMap.put(LEFT, true);
        wallsMap.put(RIGHT, col == colCount - 1);
        displayWalls();

        setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        setStyle("-fx-background-color: black;");
        setOnMouseClicked(e -> {
            System.out.println(this);
        });
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public double[] getTopLeftCoordinates(int rowCount, int colCount) {
        double x = col * CELL_WIDTH;
        double y = row * CELL_HEIGHT;
        return new double[] {x, y};
    }

    public double[] getCenterCoordinates(int rowCount, int colCount) {
        double x = col * CELL_WIDTH + CELL_WIDTH/2;
        double y = row * CELL_HEIGHT + CELL_HEIGHT/2;
        return new double[] {x, y};
    }

    public void openCell(short direction) {
        if (!hasWallInDirection(direction))
            return;
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

    public boolean hasWallInDirection(short direction) {
        return wallsMap.get(direction);
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
