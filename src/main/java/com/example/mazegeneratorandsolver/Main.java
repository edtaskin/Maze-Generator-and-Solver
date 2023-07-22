package com.example.mazegeneratorandsolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final int rowCount = 5, colCount = 5; // TODO Make them selectable by the user

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        MazeDrawer mazeDrawer = new MazeDrawer(scene, rowCount, colCount); //TODO Separate scene later
        root.setCenter(mazeDrawer.getMaze());


        stage.setTitle("Maze Generator & Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
