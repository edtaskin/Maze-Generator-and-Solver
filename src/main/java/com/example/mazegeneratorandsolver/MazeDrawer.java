package com.example.mazegeneratorandsolver;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MazeDrawer implements Directions {
    private AnchorPane anchorPane; // TODO
    private Cell[][] cells;
    private int rowCount, colCount;
    private EdgeWeightedGraph G;

    public MazeDrawer(Scene scene, int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        anchorPane = new AnchorPane();
        cells = new Cell[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Cell cell = new Cell(i, j);
                anchorPane.getChildren().add(cell);
                cells[i][j] = cell;
                double[] topLeftCoordinates = cell.getTopLeftCoordinates(rowCount, colCount);
                AnchorPane.setLeftAnchor(cell, topLeftCoordinates[0]);
                AnchorPane.setTopAnchor(cell, topLeftCoordinates[1]);
            }
        }
        this.G = buildGraph();
        openMazeWays();
    }

    public AnchorPane getMaze() { return anchorPane; }

    /*
    Generates an edge weighted graph of randomized edge weights where every cell is connected to all its neighbors.
     */
    private EdgeWeightedGraph buildGraph() {
        EdgeWeightedGraph G = new EdgeWeightedGraph(rowCount*colCount);
        for (int v = 0; v < rowCount; v++) {
            for (int w = 0; w < colCount; w++) {
                if (v != 0) {
                    Edge e = new Edge(getCellIndexByCoordinate(v, w), getCellIndexByCoordinate(v - 1, w), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
                }
                if (w != 0) {
                    Edge e = new Edge(getCellIndexByCoordinate(v, w), getCellIndexByCoordinate(v, w-1), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
                }
            }
        }
        return G;
    }

    /*
    Randomly marks the cells as open until the start and end of the maze is connected.
     */
    private void openMazeWays() {
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.mst()) {
            int v = e.either();
            int w = e.other(v);
            Cell cell1 = getCellByIndex(v);
            Cell cell2 = getCellByIndex(w);
            connectCells(cell1, cell2);
        }
    }

    /*
    Connects the two input Cells by removing the wall between them.
     */
    private void connectCells(Cell cell1, Cell cell2) {
        assert cell1 != cell2;
        drawLineBetween(cell1, cell2);
        if (cell1.getRow() == cell2.getRow()) {
            if (cell1.getCol() > cell2.getCol()) {
                cell1.openCell(LEFT);
                cell2.openCell(RIGHT);
            }
            else {
                cell1.openCell(RIGHT);
                cell2.openCell(LEFT);
            }
        }
        else if (cell1.getCol() == cell2.getCol()) {
            if (cell1.getRow() > cell2.getRow()) {
                cell1.openCell(TOP);
                cell2.openCell(BOTTOM);
            }
            else {
                cell1.openCell(BOTTOM);
                cell2.openCell(TOP);
            }
        }
    }

    /*
    Draws an edge in the MST over the maze for debugging
     */
    private void drawLineBetween(Cell cell1, Cell cell2) {
        double[] cell1CenterCoordinates = cell1.getCenterCoordinates(rowCount, colCount);
        double[] cell2CenterCoordinates = cell2.getCenterCoordinates(rowCount, colCount);

        double cell1CenterX = cell1CenterCoordinates[0];
        double cell1CenterY = cell1CenterCoordinates[1];
        double cell2CenterX = cell2CenterCoordinates[0];
        double cell2CenterY = cell2CenterCoordinates[1];

        Line mstEdge = new Line(cell1CenterX, cell1CenterY, cell2CenterX, cell2CenterY);
        mstEdge.setStroke(Color.LIGHTBLUE);

        anchorPane.getChildren().add(mstEdge);
    }

    /*
    Helper methods to fetch specific cells from the maze
     */

    private int getCellIndexByCoordinate(int x, int y) {
        return x * colCount + y;
    }

    private Cell getCellByIndex(int index) {
        int y = index % colCount;
        int x = (index - y) / colCount;
        return cells[x][y];
    }

    private Cell getRandomCell() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, rowCount*colCount);
        return getCellByIndex(randomIndex);
    }

    private Cell getRandomNeighbouringCell(Cell cell) {
        List<Short> validDirections = new ArrayList<>();
        if (cell.getRow() != 0)
            validDirections.add(TOP);
        if (cell.getRow() != rowCount - 1)
            validDirections.add(BOTTOM);
        if (cell.getCol() != 0)
            validDirections.add(LEFT);
        if (cell.getCol() != colCount - 1)
            validDirections.add(RIGHT);
        int randomIndex = ThreadLocalRandom.current().nextInt(0, validDirections.size());
        short randomDirection = validDirections.get(randomIndex);
        return getNeighbourByDirection(cell, randomDirection);
        /*
        TODO Alternative:
        - Hold a static variable of directions [TOP, BOTTOM, RIGHT, LEFT]
        - Randomly select one direction
        - Create an assertion method to check if direction is valid (0 <= x < rowCount, 0 <= y < colCount)
             private boolean assertValidNeighbour(int x, int y) {
                    return (x >= 0 && x < rowCount && y >= 0 && y < colCount);
            }
        - Randomly select another direction until a valid one is found.
        Is it more efficient?
        - Memory-wise, YES.
        - Time-wise ???
        Does it matter?
        - ???
         */

    }

    private Cell getNeighbourByDirection(Cell cell, short direction) {
        assert direction < 4;
        int deltaRow = 0;
        int deltaCol = 0;
        switch (direction) {
            case TOP:
                deltaRow = -1;
                break;
            case BOTTOM:
                deltaRow = 1;
                break;
            case RIGHT:
                deltaCol = 1;
                break;
            case LEFT:
                deltaCol = -1;
                break;
        }
        if (!assertValidCell(cell.getRow() + deltaRow, cell.getCol() + deltaCol))
            throw new ArrayIndexOutOfBoundsException(String.format("(%d, %d) is not a valid neighbour of (%d, %d).", cell.getRow() + deltaRow, cell.getCol() + deltaCol, cell.getRow(), cell.getCol()));
        else
            return cells[cell.getRow() + deltaRow][cell.getCol() + deltaCol];
    }

    private boolean assertValidCell(int x, int y) {
        return (x >= 0 && x < rowCount && y >= 0 && y < colCount);
    }

}
