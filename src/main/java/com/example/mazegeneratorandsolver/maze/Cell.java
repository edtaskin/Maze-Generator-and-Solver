package com.example.mazegeneratorandsolver.maze;

import com.example.mazegeneratorandsolver.ui.Settings;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class Cell extends BorderPane {
    public static final int BORDER_WIDTH = 2;
    private static final Color BORDER_COLOR = Color.RED;

    private Map<Directions, Boolean> wallsMap;
    private final int row, col;
    private double cellSize;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();
        cellSize = Settings.getInstance().getCellSize();

        setWallsMap();
        displayWalls();
        setCellStyling();
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public double getCellSize() { return cellSize; }

    public int getIndex() {
        Settings settings = Settings.getInstance();
        return row * settings.getColCount() + col;
    }

    public double[] getTopLeftCoordinates(int rowCount, int colCount) {
        double x = col * cellSize;
        double y = row * cellSize;
        return new double[] {x, y};
    }

    public double[] getCenterCoordinates(int rowCount, int colCount) {
        double x = col * cellSize + cellSize /2;
        double y = row * cellSize + cellSize/2;
        return new double[] {x, y};
    }

    public boolean hasWallInDirection(Directions direction) {
        return wallsMap.get(direction);
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

    private void setWallsMap() {
        Settings settings = Settings.getInstance();
        int rowCount = settings.getRowCount();
        int colCount = settings.getColCount();
        // All cells have upper walls, last row also has bottom walls
        wallsMap.put(Directions.TOP, true);
        wallsMap.put(Directions.BOTTOM, row == rowCount - 1);
        // All cells have left walls, last column also has right walls
        wallsMap.put(Directions.LEFT, true);
        wallsMap.put(Directions.RIGHT, col == colCount - 1);
    }

    private void setCellStyling() {
        setPrefSize(cellSize, cellSize);
        Color cellBackgroundColor =  Settings.getInstance().getCellBackgroundColor();
        BackgroundFill fill = new BackgroundFill(cellBackgroundColor, null, null);
        setBackground(new Background(fill));
    }

    private void displayWalls() {
        getChildren().clear();

        Rectangle center = new Rectangle(cellSize - 2 * BORDER_WIDTH, cellSize - 2 * BORDER_WIDTH);
        center.setFill(Settings.getInstance().getCellBackgroundColor());
        setCenter(center);

        for (Directions direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                Rectangle wall = null;
                switch (direction) {
                    case TOP:
                        wall = new Rectangle(cellSize, BORDER_WIDTH);
                        setTop(wall);
                        break;
                    case BOTTOM:
                        wall = new Rectangle(cellSize, BORDER_WIDTH);
                        setBottom(wall);
                        break;
                    case LEFT:
                        wall = new Rectangle(BORDER_WIDTH, cellSize);
                        setLeft(wall);
                        break;
                    case RIGHT:
                        wall = new Rectangle(BORDER_WIDTH, cellSize);
                        setRight(wall);
                        break;
                }
                if (wallsMap.get(direction))
                    wall.setFill(Settings.getInstance().getWallColor());
                else
                    wall.setFill(Settings.getInstance().getCellBackgroundColor());
            }
        }
    }

    public void resetCell() {
        setWallsMap();
        displayWalls();
    }

    // TODO Delete if won't be used
    public Point2D getCellShape() {
        double width = cellSize;
        if (wallsMap.get(Directions.LEFT))      width -= Cell.BORDER_WIDTH;
        if (wallsMap.get(Directions.RIGHT))     width -= Cell.BORDER_WIDTH;

        double height = cellSize;
        if (wallsMap.get(Directions.TOP))       height -= Cell.BORDER_WIDTH;
        if (wallsMap.get(Directions.BOTTOM))    height -= Cell.BORDER_WIDTH;

        return new Point2D(width, height);
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
