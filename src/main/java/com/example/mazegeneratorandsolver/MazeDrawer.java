package com.example.mazegeneratorandsolver;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MazeDrawer implements Directions {
    private GridPane maze;
    private Cell[][] cells;
    private int rowCount, colCount;
    private EdgeWeightedGraph G;

    public MazeDrawer(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        maze = new GridPane();
        cells = new Cell[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j =0; j < colCount; j++) {
                Cell cell = new Cell(i, j);
                maze.add(cell.getPane(), i, j);
                cells[i][j] = cell;
            }
        }
        this.G = buildGraph();
        openMazeWays();
    }

    public GridPane getMaze() { return maze; }

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
                    //TODO Alttaki linelar yanlış yerde
                    //cells[v][w].removeWall(LEFT);
                    //cells[v-1][w].removeWall(RIGHT);
                }
                if (w != 0) {
                    Edge e = new Edge(getCellIndexByCoordinate(v, w), getCellIndexByCoordinate(v, w-1), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
                    //TODO Alttaki linelar yanlış yerde
                    //cells[v][w].removeWall(BOTTOM);
                    //cells[v][w-1].removeWall(TOP);
                }
            }
        }
        return G;
    }

    /*
    Randomly marks the cells as open until TODO
     */
    private void openMazeWays() {
        // TEST
        for (int i = 0; i < 20; i++) {
            Cell randomCell = getRandomCell();
        }
    }

    /*
    Helper methods to fetch specific cells from the maze
     */

    private int getCellIndexByCoordinate(int x, int y) {
        return x * colCount + y;
    }

    // TODO Test correctness
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
        // TODO
        List<Short> validDirections = new ArrayList<>();
        if (cell.getX() != 0)
            validDirections.add(TOP);
        if (cell.getX() != rowCount)
            validDirections.add(BOTTOM);
        if (cell.getY() != 0)
            validDirections.add(RIGHT);
        if (cell.getY() != colCount)
            validDirections.add(LEFT);
        int randomIndex = ThreadLocalRandom.current().nextInt(0, validDirections.size());
        return getCellByIndex(randomIndex);
        /*
        TODO Alternative:
        - Hold a static variable of directions [TOP, BOTTOM, RIGHT, LEFT]
        - Randomly select one direction
        - Create an assertion method to check if direction is valid (0 <= x < rowCount, 0 <= y < colCount)
        - Randomly select another direction until a valid one is found.
        Is it more efficient?
        - Memory-wise, YES.
        Does it matter?
        - ???
         */

    }


}
