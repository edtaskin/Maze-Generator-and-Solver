package com.example.mazegeneratorandsolver.ui;

import com.example.mazegeneratorandsolver.maze.CellAnimator;
import com.example.mazegeneratorandsolver.maze.MazeDrawer;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MazeController extends Controller implements Initializable {
    @FXML
    private BorderPane root;

    @FXML
    private Button backButton, generateButton, solveButton, replayButton;

    @FXML
    private Label messageLabel;

    private MazeDrawer mazeDrawer;
    private CellAnimator cellAnimator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
        this.mazeDrawer = new MazeDrawer(settings.getRowCount(), settings.getColCount());
        this.cellAnimator = mazeDrawer.getCellAnimator();
        root.setCenter(mazeDrawer.getMaze());
        solveButton.setDisable(true);
        replayButton.setDisable(true);
    }
    @FXML
    private void goToPreviousPage() {
        sceneManager.switchScene("title.fxml");
    }

    @FXML
    private void generateMaze() {
        mazeDrawer.generateMaze();
        Animation lastAnimation = cellAnimator.getLastAnimation();
        lastAnimation.setOnFinished(e -> solveButton.setDisable(false));
        cellAnimator.play();
        generateButton.setDisable(true);
    }

    @FXML
    private void solveMaze() {
        // TODO Make endpoints selectable on UI => Add to settings: Maze solver endpoints: Default | Custom
        mazeDrawer.solveMaze(new Point2D(0, 0), new Point2D(-1, -1));
        cellAnimator.getLastAnimation().setOnFinished(e -> replayButton.setDisable(false));
        cellAnimator.play();
        solveButton.setDisable(true);
    }

    @FXML
    private void replayAnimations() {
        // TODO Doesn't work
        mazeDrawer.resetCells();
        mazeDrawer.getCellAnimator().replay();
        // TODO Separate replay for generate and solve
    }

}
