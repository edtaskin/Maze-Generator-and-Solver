package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class MazeController{
    @FXML
    private Button backButton, generateButton, solveButton, replayButton;

    @FXML
    private AnchorPane mazeContainer;

    private SceneManager sceneManager;

    public MazeController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private void goToPreviousPage() {
        sceneManager.switchScene("title.fxml");
    }
}
