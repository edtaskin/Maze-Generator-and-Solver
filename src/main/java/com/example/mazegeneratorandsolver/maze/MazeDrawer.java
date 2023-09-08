package com.example.mazegeneratorandsolver.maze;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
// TODO Consider moving MazeSolver functionality to a separate class
public class MazeDrawer {
    private AnchorPane anchorPane; // TODO May convert back to GridPane once maze generation is completely debugged, unless the MST is needed for demonstration purposes
    private Cell[][] cells;
    private int rowCount, colCount;
    private EdgeWeightedGraph graph;
    private CellAnimator cellAnimator;
    private boolean displayMST = false;

    public MazeDrawer(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        anchorPane = new AnchorPane();
        cells = new Cell[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Cell cell = new Cell(i, j, rowCount, colCount);
                anchorPane.getChildren().add(cell);
                cells[i][j] = cell;
                double[] topLeftCoordinates = cell.getTopLeftCoordinates(rowCount, colCount);
                AnchorPane.setLeftAnchor(cell, topLeftCoordinates[0]);
                AnchorPane.setTopAnchor(cell, topLeftCoordinates[1]);
            }
        }
        cellAnimator = new CellAnimator();
    }

    public AnchorPane getMaze() { return anchorPane; }

    public void generateMaze() {
        graph = buildGraph();
        openMazeWays();
        cellAnimator.play();
//        cellAnimator.getLastAnimation().setOnFinished(// TODO);
    }

    /*
    Generates an edge weighted graph of randomized edge weights where every cell is connected to all its neighbors.
    Edge weights are randomized in order to get a random MST of the graph.
     */
    private EdgeWeightedGraph buildGraph() {
        EdgeWeightedGraph G = new EdgeWeightedGraph(rowCount*colCount);
        for (int v = 0; v < rowCount; v++) {
            for (int w = 0; w < colCount; w++) {
                if (v != 0) {
                    // TODO Adding an edge between cells should be easier!!!
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
    Removes the walls that intersect with the edges of the MST, resulting in a maze.
    Then visually animates the process by removing the walls sequentially.
     */
    private void openMazeWays() {
//        cellAnimator.clearQueue();
        LazyPrimMST mst = new LazyPrimMST(graph);

        graph = new EdgeWeightedGraph(rowCount*colCount); // Reset edges

        for (Edge e : mst.mst()) {
            int v = e.either();
            int w = e.other(v);
            Cell cell1 = getCellByIndex(v);
            Cell cell2 = getCellByIndex(w);
            connectCells(cell1, cell2);
        }
    }


    // Connects the two Cells by removing the wall between them.
    private void connectCells(Cell cell1, Cell cell2) {
        assert cell1 != cell2;
        if (displayMST) drawLineBetween(cell1, cell2); // Prints MST for debugging
        Directions direction1 = null;
        Directions direction2 = null;
        if (cell1.getRow() == cell2.getRow()) {
            if (cell1.getCol() > cell2.getCol()) {
                direction1 = Directions.LEFT;
                direction2 = Directions.RIGHT;
            }
            else {
                direction1 = Directions.RIGHT;
                direction2 = Directions.LEFT;
            }
        }
        else if (cell1.getCol() == cell2.getCol()) {
            if (cell1.getRow() > cell2.getRow()) {
                direction1 = Directions.TOP;
                direction2 = Directions.BOTTOM;
            }
            else {
                direction1 = Directions.BOTTOM;
                direction2 = Directions.TOP;
            }
        }
        if (direction1 != null && direction2 != null) {
            graph.addEdge(new Edge(getCellIndex(cell1), getCellIndex(cell2), 0)); // TODO Weight? => Weights don't matter for dijkstra since there is only 1 valid way?
            cell1.openCell(direction1);
            cell2.openCell(direction2);
            cellAnimator.enqueueAnimation(CellAnimator.getFadeTransitionInDirection(cell1, direction1));
            cellAnimator.enqueueAnimation(CellAnimator.getFadeTransitionInDirection(cell2, direction2));
        }
    }

    // Finds and displays the path from start to end using Dijkstra's Algorithm
    public void solveMaze(Point2D start, Point2D end) {
        // cellAnimator.clearQueue(); // TODO
        int startX = (int) start.getX();
        int startY = (int) start.getY();

        int endX = (int) end.getX();
        int endY = (int) end.getY();

        DijkstraSP dijkstraSP = new DijkstraSP(graph, getCellIndexByCoordinate(startX, startY));
        for (int index : dijkstraSP.verticesOnPathTo(getCellIndexByCoordinate(endX, endY))) {
            Cell cell = getCellByIndex(index);
            cellAnimator.enqueueAnimation(CellAnimator.getFillTransition(cell));
        }
    }


   // Draws an edge in the MST over the maze for debugging
    private void drawLineBetween(Cell cell1, Cell cell2) {
        double[] cell1CenterCoordinates = cell1.getCenterCoordinates(rowCount, colCount);
        double[] cell2CenterCoordinates = cell2.getCenterCoordinates(rowCount, colCount);

        double cell1CenterX = cell1CenterCoordinates[0];
        double cell1CenterY = cell1CenterCoordinates[1];
        double cell2CenterX = cell2CenterCoordinates[0];
        double cell2CenterY = cell2CenterCoordinates[1];

        Line mstEdge = new Line(cell1CenterX, cell1CenterY, cell2CenterX, cell2CenterY);
        mstEdge.setStroke(Color.LIGHTBLUE);
        mstEdge.setOpacity(0.5);

        anchorPane.getChildren().add(mstEdge);
    }

    // Helper methods to fetch specific cells from the maze

    /*
    Returns the index of a cell given its (x, y) coordinates.
    For convenience returns the index of the last element for (-1, -1) input.
     */
    private int getCellIndexByCoordinate(int x, int y) {
        if (x == -1 && y == -1) {
            x = rowCount - 1;
            y = colCount - 1;
        }
        return x * colCount + y;
    }

    private Cell getCellByIndex(int index) {
        int y = index % colCount;
        int x = (index - y) / colCount;
        return cells[x][y];
    }

    private int getCellIndex(Cell cell) {
        return cell.getRow() * colCount + cell.getCol();
    }

    private Cell getRandomCell() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, rowCount*colCount);
        return getCellByIndex(randomIndex);
    }

    private Cell getRandomNeighbouringCell(Cell cell) {
        List<Directions> validDirections = new ArrayList<>();
        if (cell.getRow() != 0)
            validDirections.add(Directions.TOP);
        if (cell.getRow() != rowCount - 1)
            validDirections.add(Directions.BOTTOM);
        if (cell.getCol() != 0)
            validDirections.add(Directions.LEFT);
        if (cell.getCol() != colCount - 1)
            validDirections.add(Directions.RIGHT);
        int randomIndex = ThreadLocalRandom.current().nextInt(0, validDirections.size());
        Directions randomDirection = validDirections.get(randomIndex);
        return getNeighbourByDirection(cell, randomDirection);
    }

    private Cell getNeighbourByDirection(Cell cell, Directions direction) {
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
        assert isValidCell(cell.getRow() + deltaRow, cell.getCol() + deltaCol);
        return cells[cell.getRow() + deltaRow][cell.getCol() + deltaCol];
    }

    private boolean isValidCell(int x, int y) {
        return (x >= 0 && x < rowCount && y >= 0 && y < colCount);
    }

}

