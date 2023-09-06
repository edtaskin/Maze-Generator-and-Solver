package com.example.mazegeneratorandsolver.ui;

import com.example.mazegeneratorandsolver.maze.MazeDrawer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MazeController extends Controller implements Initializable {
    @FXML
    private Button backButton, generateButton, solveButton, replayButton;

    @FXML
    private AnchorPane mazeContainer;

    private MazeDrawer mazeDrawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
        this.mazeDrawer = new MazeDrawer(settings.getRowCount(), settings.getColCount()); // TODO
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
