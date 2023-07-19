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

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        MazeDrawer mazeDrawer = new MazeDrawer(rowCount, colCount, rowCount*colCount/4);
        root.setCenter(mazeDrawer.getMaze());

        Scene scene = new Scene(root);
        stage.setTitle("Maze Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
