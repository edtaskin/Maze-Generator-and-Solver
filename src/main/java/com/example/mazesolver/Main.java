package com.example.mazesolver;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
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
    private static final int rowCount = 20, colCount = 20; // TODO Make them selectable by the user

    //private final Region[][] cells = new Region[rowCount][colCount];

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        MazeDrawer mazeDrawer = new MazeDrawer(rowCount, colCount, rowCount*colCount/4);
        root.setCenter(mazeDrawer.getMaze());

        /*

        LazyPrimMST mst = new LazyPrimMST(G);
//        int x = 0;
        for (Edge e : mst.mst()) {
            int v = e.either();
            Point2D p1 = getCellByIndex(e.either());
            Point2D p2 = getCellByIndex(e.other(v));
            Region reg1 = cells[(int) p1.getX()][(int) p1.getY()];
            Region reg2 = cells[(int) p2.getX()][(int) p2.getY()];
            Line line = new Line(reg1.getLayoutX(), reg1.getLayoutY(), reg2.getLayoutX(), reg2.getLayoutY());
            line.setStroke(Color.BLUE);
            line.setStrokeWidth(2);
            root.getChildren().add(line);


            Rectangle mask = null;
//            if (x == 1) break;
            if (reg1.getLayoutX() == reg2.getLayoutX()) { // Same row
                mask = new Rectangle(4, cellHeight - 2);
//                mask.setX((reg1.getLayoutX() + reg2.getLayoutX())/2);
                mask.setX(reg1.getLayoutX() + (double) cellWidth/2 - 1);
                mask.setY(reg1.getLayoutY() - (double) cellHeight/2 + 1);
                mask.setFill(Color.WHITE);
                reg1.setStyle("-fx-background-color: grey");
                reg2.setStyle("-fx-background-color: orange");
//                x = 1;
                System.out.printf("Reg1: %d, Reg2: %d\n", getCellbyCoordinate((int) p1.getX(), (int) p1.getY()), getCellbyCoordinate((int) p2.getX(), (int) p2.getY()));
            }
//            else if (reg1.getLayoutY() == reg2.getLayoutY()) { // Same col
//                mask = new Rectangle(cellWidth, 4);
//                mask.setX(reg1.getLayoutX() - cellWidth/((double)2));
//                mask.setY((reg1.getLayoutY() + reg2.getLayoutY())/2);
//                mask.setFill(Color.YELLOW);
//            }
//            mask.setFill(Color.WHITE);
            if (mask != null)
                root.getChildren().add(mask);
        }

*/

        Scene scene = new Scene(root);
        stage.setTitle("Maze Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
