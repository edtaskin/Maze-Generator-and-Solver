package com.example.mazegeneratorandsolver;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Cell implements Directions {
    private static final int CELL_WIDTH = 30, CELL_HEIGHT = 30;
    private static final Duration FILL_DURATION = Duration.millis(500);
    private static final Color OPEN_COLOR = Color.BLUE, WALL_COLOR = Color.BLACK, FILLING_COLOR = Color.ORANGE;

    private static final MyQueue<FillTransition> animationQueue = new MyQueue<>();

    private Map<Short, Boolean> wallsMap;
    private final int row, col;
    private Pane pane;
    private Rectangle fillRectangle;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();
        pane = new Pane();
        fillRectangle = new Rectangle(CELL_WIDTH - 2, CELL_HEIGHT - 2);
        fillRectangle.setFill(WALL_COLOR);
        pane.getChildren().add(fillRectangle);
        pane.setOnMouseClicked(event -> System.out.println(pane.getChildren().toString() + " => " + pane.getStyleClass().toString()));

        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            wallsMap.put(side, true); // Initially all walls are placed

        pane.getStyleClass().addAll("cell", "top", "bottom", "left", "right");
        pane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Pane getPane() { return pane; }


    private void enqueueAnimation() {
        FillTransition fillAnimation = new FillTransition(FILL_DURATION, this.fillRectangle, WALL_COLOR, FILLING_COLOR);
        animationQueue.enqueue(fillAnimation);
        //fillAnimation.setOnFinished(event -> fillRectangle.setFill(OPEN_COLOR));
    }


    public void openCell(short direction) {
        pane.getStyleClass().remove("filled");
        wallsMap.replace(direction, false);
        enqueueAnimation();
        //displayWalls(); // TODO
    }


    private void displayWalls() {
        for (short direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                if (!pane.getStyleClass().contains(DirectionsUtils.toString(direction)))
                    pane.getStyleClass().add(DirectionsUtils.toString(direction));
            }
            else
                pane.getStyleClass().remove(DirectionsUtils.toString(direction));
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("Row: %d, Col: %d, Walls: [", row, col));
        int i = 0;
        for (short direction : wallsMap.keySet()) {
            if (wallsMap.get(direction)) {
                if (i != wallsMap.keySet().size() - 1)
                    res.append(DirectionsUtils.toString(direction)).append(", ");
                else
                    res.append(DirectionsUtils.toString(direction));
            }
            i++;
        }
        res.append("]");
        return String.valueOf(res);
    }

    /*
    Recursively plays all animations on the animationsQueue, i.e. plays the animation sequentially for all cells on the path
     */
    public static void playAnimations() {
        FillTransition nextAnimation = animationQueue.dequeue();
        System.out.println(nextAnimation.toString());
        nextAnimation.setOnFinished(event -> playAnimations());
        nextAnimation.play();
    }
}
