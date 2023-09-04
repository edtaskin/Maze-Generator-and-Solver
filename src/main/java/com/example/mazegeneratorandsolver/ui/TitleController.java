package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TitleController {
    @FXML
    private Button startButton, settingsButton;

    private SceneManager sceneManager;

    public TitleController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private void start() {
        sceneManager.switchScene("maze.fxml");
        MazeController mazeController = new MazeController(sceneManager);
    }

    private void showSettings() {
        sceneManager.switchScene("settings.fxml");
        SettingsController settingsController = new SettingsController(sceneManager);
    }

}
