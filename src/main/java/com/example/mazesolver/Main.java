package com.example.mazesolver;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {
    private static final int rowCount = 5, colCount = 5;
    private final Rectangle[][] cells = new Rectangle[rowCount][colCount];
//    private final Region[][] regions = new Region[rowCount][colCount];
    private GridPane gridPane;
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        BorderPane root = new BorderPane();
        gridPane = new GridPane();
        root.setCenter(gridPane);
        //gridPane.setGridLinesVisible(true);

        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < colCount; j++) {
                Rectangle cell = new Rectangle(60, 60);
//                Region region = new Region(cell);
                cell.setX((j + (double) 1/2) * cell.getWidth());
                cell.setY((i + (double) 1/2) * cell.getHeight());
                cell.setFill(Color.WHITE);
                cell.setStyle("-fx-border-color: black transparent black transparent");
//                cell.setStroke(Color.BLACK);
//                cell.setStrokeType(StrokeType.INSIDE);
                gridPane.add(cell, i, j);
                cells[i][j] = cell;
//                regions[i][j] = region;
            }

        EdgeWeightedGraph G = new EdgeWeightedGraph(rowCount*colCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
//                ArrayList<Rectangle> targetCells = new ArrayList<>();
                if (i != 0) {
                    Edge e = new Edge(getCellbyCoordinate(i, j), getCellbyCoordinate(i - 1, j), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
//                    targetCells.add(cells[i-1][j]);
                }
                if (j != 0) {
                    Edge e = new Edge(getCellbyCoordinate(i, j), getCellbyCoordinate(i, j-1), ThreadLocalRandom.current().nextDouble(0, 10000));
                    G.addEdge(e);
//                    targetCells.add(cells[i][j-1]);
                }
//                Rectangle sourceCell = cells[i][j];
//                for (Rectangle targetCell : targetCells) {
//                    Line line = new Line(sourceCell.getX() + sourceCell.getWidth()/2, sourceCell.getY() + sourceCell.getHeight()/2, targetCell.getX() + targetCell.getWidth()/2, targetCell.getY() + targetCell.getHeight()/2);
//                    line.setStroke(Color.RED);
//                    gridPane.getChildren().add(line);
//                }
            }
        }

        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.mst()) {
            System.out.println(e.getWeight());
            int v = e.either();
            Point2D p1 = getCellByIndex(e.either());
            Point2D p2 = getCellByIndex(e.other(v));
            Rectangle rect1 = (Rectangle) getNodeByCoordinate(p1.getX(), p1.getY());
            Rectangle rect2 = (Rectangle) getNodeByCoordinate(p2.getX(), p2.getY());
            Line line = new Line(rect1.getX(), rect1.getY(), rect2.getX(), rect2.getY());
            line.setStroke(Color.BLUE);
            line.setStrokeWidth(2);
            root.getChildren().add(line);



//            if (p1.getX() == p2.getX()) { // Same row
//                rect1.setStyle("-fx-border-color: black transparent black transparent");
//                rect2.setStyle("-fx-border-color: black transparent black transparent");
//            }
//            else if (p1.getY() == p2.getY()) { // Same col
//                rect1.setStyle("-fx-border-color: transparent black transparent black");
//                rect2.setStyle("-fx-border-color: transparent black transparent black");
//            }

        }


        Scene scene = new Scene(root);
        stage.setTitle("Maze Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static int getCellbyCoordinate(int x, int y) {
        return x * colCount + y;
    }

    public static Point2D getCellByIndex(int index) {
        int y = index % colCount;
        int x = (index - y) / colCount;
        return new Point2D(x, y);
    }

    private Node getNodeByCoordinate(double row, double col) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                return node;
        return null;
    }
}