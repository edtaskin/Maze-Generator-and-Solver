package com.example.mazegeneratorandsolver;

import javafx.animation.FillTransition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cell implements Directions {
    public static final int CELL_WIDTH = 30, CELL_HEIGHT = 30, BORDER_WIDTH = 2;
    private static final Color OPEN_COLOR = Color.BLUE, WALL_COLOR = Color.BLACK, BORDER_COLOR = Color.RED;

    private Map<Short, Boolean> wallsMap;
    private final int row, col;
    private BorderPane borderPane;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();
        borderPane = new BorderPane();
        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            wallsMap.put(side, true); // Initially all walls are placed
        borderPane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        borderPane.setStyle("-fx-background-color: black;");
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public BorderPane getPane() { return borderPane; }

/*    public int[] getCenterCoordinates(int rowCount, int colCount) {
        int x = col * CELL_WIDTH + CELL_WIDTH / 2;
        int y = row * CELL_HEIGHT + CELL_HEIGHT / 2;
        return new int[] {x, y};
    }*/

    public void openCell(short direction) {
        wallsMap.replace(direction, false);
        displayWalls();
    }


    private void displayWalls() {
        borderPane.getChildren().clear();
        for (short direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                Rectangle wall = null;
                switch (direction) {
                    case TOP:
                        wall = new Rectangle(CELL_WIDTH, BORDER_WIDTH);
                        borderPane.setTop(wall);
                        break;
                    case BOTTOM:
                        wall = new Rectangle(CELL_WIDTH, BORDER_WIDTH);
                        borderPane.setBottom(wall);
                        break;
                    case LEFT:
                        wall = new Rectangle(BORDER_WIDTH, CELL_HEIGHT);
                        borderPane.setLeft(wall);
                        break;
                    case RIGHT:
                        wall = new Rectangle(BORDER_WIDTH, CELL_HEIGHT);
                        borderPane.setRight(wall);
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
