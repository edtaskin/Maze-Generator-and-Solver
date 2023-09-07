package com.example.mazegeneratorandsolver.ui;

import com.example.mazegeneratorandsolver.maze.MazeDrawer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MazeController extends Controller implements Initializable {
    @FXML
    private BorderPane root;

    @FXML
    private Button backButton, generateButton, solveButton, replayButton;

    private MazeDrawer mazeDrawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
        this.mazeDrawer = new MazeDrawer(settings.getRowCount(), settings.getColCount());
        root.setCenter(mazeDrawer.getMaze());
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
