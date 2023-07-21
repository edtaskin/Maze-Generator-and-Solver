package com.example.mazegeneratorandsolver;

import javafx.scene.layout.GridPane;

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
                Cell cell = new Cell();
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

    }

    /*
    Helper methods to fetch specific cells from the maze
     */

    //TODO Incorrect name for the function, either change the function or change name to getCellIndexByCoordinate
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

}
