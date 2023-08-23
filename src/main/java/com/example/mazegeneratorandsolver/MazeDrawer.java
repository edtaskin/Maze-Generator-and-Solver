package com.example.mazegeneratorandsolver;

import javafx.animation.FadeTransition;
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

    public MazeDrawer(Scene scene, int rowCount, int colCount) {
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
        graph = buildGraph();
        cellAnimator = new CellAnimator();
        openMazeWays();
    }

    public AnchorPane getMaze() { return anchorPane; }

    /*
    Generates an edge weighted graph of randomized edge weights where every cell is connected to all its neighbors.
    Edge weights are randomized in order to get a random MST of the graph.
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
    Removes the walls that intersect with the edges of the MST, resulting in a maze.
    Then visually animates the process by removing the walls sequentially.
     */
    private void openMazeWays() {
        cellAnimator.clearQueue();
        LazyPrimMST mst = new LazyPrimMST(graph);
        for (Edge e : mst.mst()) {
            int v = e.either();
            int w = e.other(v);
            Cell cell1 = getCellByIndex(v);
            Cell cell2 = getCellByIndex(w);
            connectCells(cell1, cell2);
        }
        cellAnimator.playAnimations();
    }

    /*
    Connects the two Cells by removing the wall between them.
     */
    private void connectCells(Cell cell1, Cell cell2) {
        assert cell1 != cell2;
        drawLineBetween(cell1, cell2);
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
            cell1.openCell(direction1);
            cell2.openCell(direction2);
            cellAnimator.enqueueAnimation(CellAnimator.getFadeTransitionInDirection(cell1, direction1));
            cellAnimator.enqueueAnimation(CellAnimator.getFadeTransitionInDirection(cell2, direction2));
        }
    }

    // TODO Can't we use Point2D anywhere else?
    // TODO Set this on GUI later.
    // TODO Comment
    public void solveMaze(Point2D start, Point2D end) {
        // cellAnimator.clearQueue(); // TODO Should be active when GUI is ready

        int startX = (int) start.getX();
        int startY = (int) start.getY();

        int endX = (int) end.getX();
        int endY = (int) end.getY();

        DijkstraSP dijkstraSP = new DijkstraSP(graph, getCellIndexByCoordinate(startX, startY));
        // System.out.println(dijkstraSP.verticesOnPathTo(getCellIndexByCoordinate(endX, endY)));
        for (int index : dijkstraSP.verticesOnPathTo(getCellIndexByCoordinate(endX, endY))) {
            Cell cell = getCellByIndex(index);
            //cell.setStyle("-fx-background-color: blue");
            cellAnimator.enqueueAnimation(CellAnimator.getFillTransition(cell));
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
        if (!assertValidCell(cell.getRow() + deltaRow, cell.getCol() + deltaCol))
            throw new ArrayIndexOutOfBoundsException(String.format("(%d, %d) is not a valid neighbour of (%d, %d).", cell.getRow() + deltaRow, cell.getCol() + deltaCol, cell.getRow(), cell.getCol()));
        else
            return cells[cell.getRow() + deltaRow][cell.getCol() + deltaCol];
    }

    private boolean assertValidCell(int x, int y) {
        return (x >= 0 && x < rowCount && y >= 0 && y < colCount);
    }

}
