package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleController extends Controller implements Initializable {
    @FXML
    private Button startButton, settingsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
    }

    @FXML
    private void start() {
        sceneManager.switchScene("maze.fxml");
    }

    @FXML
    private void showSettings() {
        sceneManager.switchScene("settings.fxml");
    }
}
