package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingsController {
    @FXML
    private Button backButton;

    private SceneManager sceneManager;
    private Settings settings;

    public SettingsController(SceneManager sceneManager, Settings settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
    }

    // TODO Make the default settings visible in UI. => implement Initializable?

    private void goToPreviousPage() {
        sceneManager.switchScene("title.fxml");
        // TODO Pass the settings to the data
    }


}
