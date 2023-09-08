package com.example.mazegeneratorandsolver.ui;

import com.example.mazegeneratorandsolver.maze.MazeDrawer;
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
        mazeDrawer.generateMaze();
    }

    @FXML
    private void solveMaze() {
/*        // TODO Make endpoints selectable on UI
        double startX, startY, endX, endY;
        messageLabel.setText("Select a starting cell on the maze for the maze solver.");
        messageLabel.setVisible(true);

        messageLabel.setText("Select an ending cell on the maze for the maze solver.");

        mazeDrawer.solveMaze(new Point2D(x1, y1), new Point2D(x2, y2)); // TODO Doesn't work!*/

        // Default // TODO Add to settings: Maze solver endpoints: Default | Custom
        mazeDrawer.solveMaze(new Point2D(0, 0), new Point2D(-1, -1));
    }

    @FXML
    private void replayAnimations() {

    }

}
