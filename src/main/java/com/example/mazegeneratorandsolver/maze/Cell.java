package com.example.mazegeneratorandsolver.maze;

import com.example.mazegeneratorandsolver.ui.Settings;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class Cell extends BorderPane {
    public static final int CELL_WIDTH = 30, CELL_HEIGHT = 30, BORDER_WIDTH = 2;
    private static final Color BORDER_COLOR = Color.RED;

    private Map<Directions, Boolean> wallsMap;
    private final int row, col;

    public Cell(int row, int col, int rowCount, int colCount) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();

        // All cells have upper walls, last row also has bottom walls
        wallsMap.put(Directions.TOP, true);
        wallsMap.put(Directions.BOTTOM, row == rowCount - 1);
        // All cells have left walls, last column also has right walls
        wallsMap.put(Directions.LEFT, true);
        wallsMap.put(Directions.RIGHT, col == colCount - 1);
        displayWalls();

        setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        setStyle(String.format("-fx-background-color: %s", Settings.getInstance().getCellBackgroundColor().toString())); // TODO Test
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

    public Node getWallInDirection(Directions direction) {
        switch (direction) {
            case TOP:
                return getTop();
            case BOTTOM:
                return getBottom();
            case LEFT:
                return getLeft();
            case RIGHT:
                return getRight();
        }
        return null;
    }

    public void openCell(Directions direction) {
        wallsMap.replace(direction, false);
    }

    private void displayWalls() {
        getChildren().clear();
        for (Directions direction : wallsMap.keySet()) {
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
                wall.setFill(Settings.getInstance().getWallColor());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("Row: %d, Col: %d, Walls: [", row, col));
        int i = 0;
        for (Directions direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                if (i != wallsMap.keySet().size() - 1)
                    res.append(direction.getText()).append(", ");
                else
                    res.append(direction.getText());
            }
            i++;
        }
        res.append("]");
        return String.valueOf(res);
    }

}
