package com.example.mazegeneratorandsolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final int rowCount = 20, colCount = 20; // TODO Make them selectable by the user

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        MazeDrawer mazeDrawer = new MazeDrawer(rowCount, colCount);
        root.setCenter(mazeDrawer.getMaze());

        Scene scene = new Scene(root);
        stage.setTitle("Maze Generator & Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
