package com.example.mazegeneratorandsolver.ui;

public abstract class Controller {
    protected SceneManager sceneManager;
    protected Settings settings;

    public Controller(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.settings = Settings.getInstance();
    }
}
