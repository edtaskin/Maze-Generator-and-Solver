package com.example.mazegeneratorandsolver.ui;

import com.example.mazegeneratorandsolver.maze.MazeDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class MazeController extends Controller {
    @FXML
    private Button backButton, generateButton, solveButton, replayButton;

    @FXML
    private AnchorPane mazeContainer;

    private MazeDrawer mazeDrawer;

    public MazeController(SceneManager sceneManager) {
        super(sceneManager);
        this.mazeDrawer = new MazeDrawer(settings.getRowCount(), settings.getColCount());
    }

    @FXML
    private void goToPreviousPage() {
        sceneManager.switchScene("title.fxml");
    }

    @FXML
    private void generateMaze() {

    }

    @FXML
    private void solveMaze() {

    }

    @FXML
    private void replayAnimations() {

    }
}
