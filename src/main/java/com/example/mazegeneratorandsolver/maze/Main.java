package com.example.mazegeneratorandsolver.maze;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final int rowCount = 10, colCount = 10; // TODO Make them selectable by the user

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane(); // TODO Use SceneBuilder
        Scene scene = new Scene(root);

//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();

        MazeDrawer mazeDrawer = new MazeDrawer(scene, rowCount, colCount); //TODO Separate scene later
        root.setCenter(mazeDrawer.getMaze());

        mazeDrawer.generateMaze();
        mazeDrawer.solveMaze(new Point2D(0, 0), new Point2D(9, 9)); // TODO Set this on GUI
        stage.setTitle("Maze Generator & Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
