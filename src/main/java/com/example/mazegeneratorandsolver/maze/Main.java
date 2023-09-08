package com.example.mazegeneratorandsolver.maze;

import com.example.mazegeneratorandsolver.ui.SceneManager;
import com.example.mazegeneratorandsolver.ui.TitleController;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Maze Generator & Solver");
        SceneManager sceneManager = SceneManager.getInstance(stage);
        sceneManager.switchScene("title.fxml");
        stage.show();
        TitleController titleController = (TitleController) sceneManager.getController();
    }

    public static void main(String[] args) {
        launch();
    }

}
