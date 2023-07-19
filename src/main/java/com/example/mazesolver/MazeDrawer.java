package com.example.mazesolver;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MazeDrawer {
    private static final int cellWidth = 30, cellHeight = 30;
    private GridPane maze;
    private Pane[][] cells;
    private int rowCount, colCount, openCellsCount;
    //private EdgeWeightedGraph G;
    private EdgeWeightedGraph G;

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
    Generates an edge weighted graph of randomized edge weights where every cell is connected to all its neighbors.
     */
    private EdgeWeightedGraph buildGraph() {
        EdgeWeightedGraph G = new EdgeWeightedGraph(rowCount*colCount);
        for (int v = 0; v < rowCount; v++) {
            for (int w = 0; w < colCount; w++) {
                if (v != 0) {
                    Edge e = new Edge(getCellByCoordinate(v, w), getCellByCoordinate(v - 1, w), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
                }
                if (w != 0) {
                    Edge e = new Edge(getCellByCoordinate(v, w), getCellByCoordinate(v, w-1), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
                }
            }
        }
        return G;
    }

    /*
    Randomly marks the cells as open until the maze becomes connected and the number of opened cells is at least cellsOpened given in the constructor.
     */
    private void openMazeWays() {
        LazyPrimMST mst = new LazyPrimMST(G);
        int cellsOpened = 0;
        List<Edge> randomizedMst = mst.mst();
        Collections.shuffle(randomizedMst);
        for (Edge e : randomizedMst) {
            if (cellsOpened >= openCellsCount) {
                ConnectedComponents CC = new ConnectedComponents(G, true);
                if (CC.isConnected()) break;
            }
            int v = e.either();
            int w = e.other(v);
            getCellByIndex(v).setStyle("-fx-background-color: white");
            getCellByIndex(w).setStyle("-fx-background-color: white");
            cellsOpened += 2;
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
