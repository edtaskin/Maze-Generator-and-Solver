package com.example.mazesolver;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.concurrent.ThreadLocalRandom;

public class MazeDrawer {
    private static final int cellWidth = 30, cellHeight = 30;
    private GridPane maze;
    private Pane[][] cells;
    private int rowCount, colCount, openCellsCount;
    //private EdgeWeightedGraph G;
    private Graph G;

    public MazeDrawer(int rowCount, int colCount, int openCellsCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.openCellsCount = openCellsCount;
        maze = new GridPane();
        cells = new Pane[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j =0; j < colCount; j++) {
                Pane cell = new Pane();
                cell.setStyle("-fx-background-color: black"); // Initially all cells are walls
                //cell.setStyle("-fx-border-color: blue");
                cell.setPrefSize(cellWidth, cellHeight);
                maze.add(cell, i, j);
                cells[i][j] = cell;
            }
        }
        this.G = buildGraph();
        openMazeWays();
    }

    public GridPane getMaze() { return maze; }

    /*
    Generates a edge weighted graph of randomized edge weights where every cell is connected to all its neighbors.
     */
    private Graph buildGraph() {
        Graph G = new Graph(rowCount*colCount);
        for (int v = 0; v < rowCount; v++) {
            for (int w = 0; w < colCount; w++) {
                if (v != 0) {
                    G.addEdge(getCellByCoordinate(v, w), getCellByCoordinate(v - 1, w));
                    //Edge e = new Edge(getCellByCoordinate(i, j), getCellByCoordinate(i - 1, j), ThreadLocalRandom.current().nextDouble(0, 10000));
                    //G.addEdge(e);
                }
                if (w != 0) {
                    G.addEdge(getCellByCoordinate(v, w), getCellByCoordinate(v, w-1));
                    //Edge e = new Edge(getCellByCoordinate(i, j), getCellByCoordinate(i, j-1), ThreadLocalRandom.current().nextDouble(0, 10000));
                    //G.addEdge(e);
                }
            }
        }
        return G;
    }

    /*
    Randomly marks the cells as open while the total open cells are less than the openCellsCount.
     */
    private void openMazeWays() {
        /*LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.mst()) {
            int v = e.either();
            int w = e.other(v);
            getCellByIndex(v).setStyle("-fx-background-color: white");
            getCellByIndex(w).setStyle("-fx-background-color: white");
        }*/
        int divisor = 5;
        for (int i = 0; i < openCellsCount/divisor; i++) {
            int randomStartCell = getRandomCellIndex();
            System.out.println(randomStartCell);
            RandomizedDFS dfs = new RandomizedDFS(this.G, randomStartCell);
            int cellsOpened = 0;
            for (int v : dfs.getVisitedVertices()) {
                if (cellsOpened < 10) {
                    getCellByIndex(v).setStyle("-fx-background-color: white");
                    cellsOpened++;
                }
            }
        }

    }

    private int getCellByCoordinate(int x, int y) {
        return x * colCount + y;
    }

    // TODO Test correctness
    private Pane getCellByIndex(int index) {
        int y = index % colCount;
        int x = (index - y) / colCount;
        return cells[x][y];
    }

//    private Node getNodeByCoordinate(double row, double col) {
//        for (Node node : gridPane.getChildren())
//            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
//                return node;
//        return null;
//    }

    private int getRandomCellIndex() {
        return ThreadLocalRandom.current().nextInt(0, rowCount*colCount);
    }

}
