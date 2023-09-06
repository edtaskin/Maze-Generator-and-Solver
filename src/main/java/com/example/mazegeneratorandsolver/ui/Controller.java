package com.example.mazegeneratorandsolver.ui;

public abstract class Controller {
    protected SceneManager sceneManager;
    protected Settings settings;

    protected void initializeController() {
        sceneManager = SceneManager.getInstance();
        settings = Settings.getInstance();
    }
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
