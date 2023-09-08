package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {
    private static SceneManager instance;

    private Stage stage;
    private Controller controller;

    private SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static SceneManager getInstance(Stage stage) {
        if (instance == null) instance = new SceneManager(stage);
        return instance;
    }

    public static SceneManager getInstance() {
        if (instance == null) throw new NullPointerException("Scene Manager not initialized");
        return instance;
    }

    public void switchScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource("/fxml/" + fxmlFileName);
            loader.setLocation(fxmlUrl);
            Parent root = loader.load();
            this.controller = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller getController() {return controller;}
}
