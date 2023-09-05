package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TitleController extends Controller {
    @FXML
    private Button startButton, settingsButton;

    public TitleController(SceneManager sceneManager) {
        super(sceneManager);
    }

    @FXML
    private void start() {
        sceneManager.switchScene("maze.fxml");
        MazeController mazeController = new MazeController(sceneManager);
    }

    @FXML
    private void showSettings() {
        sceneManager.switchScene("settings.fxml");
        SettingsController settingsController = new SettingsController(sceneManager);
    }

}
