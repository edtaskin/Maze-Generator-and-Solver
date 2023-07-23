package com.example.mazegeneratorandsolver;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Cell implements Directions {
    private static final int CELL_WIDTH = 30, CELL_HEIGHT = 30;
    private static final Duration FILL_DURATION = Duration.seconds(2);
    private static final HashMap<TranslateTransition, Rectangle> animationToRectangleMap = new HashMap<>();
    private static TranslateTransition upAnimation, downAnimation, leftAnimation, rightAnimation;

    private static final MyQueue<TranslateTransition> animationQueue = new MyQueue<>(); // TODO

    private Map<Short, Boolean> wallsMap;
    private final int row, col;
    private Pane pane;
    private Rectangle fillRectangle;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        wallsMap = new HashMap<>();
        pane = new Pane();
        fillRectangle = new Rectangle();
        pane.setOnMouseClicked(event -> System.out.println(pane.getChildren().toString() + " => " + pane.getStyleClass().toString()));

        for (short side : new short[]{TOP, BOTTOM, RIGHT, LEFT})
            wallsMap.put(side, true); // Initially all walls are placed
        pane.getStyleClass().addAll("cell", "filled", "top", "bottom", "left", "right");
        pane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        initializeAnimations();
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Pane getPane() { return pane; }

    private void initializeAnimations() {
        // Fill upwards
        Rectangle fillRectangle1 = new Rectangle(0, CELL_HEIGHT, CELL_WIDTH, 0);
        upAnimation = new TranslateTransition(FILL_DURATION, fillRectangle1);
        upAnimation.setByY(-CELL_HEIGHT);
        animationToRectangleMap.put(upAnimation, fillRectangle1);
        // Fill downwards
        Rectangle fillRectangle2 = new Rectangle(0, 0, CELL_WIDTH, 0);
        downAnimation = new TranslateTransition(FILL_DURATION, fillRectangle2);
        downAnimation.setByY(CELL_HEIGHT);
        animationToRectangleMap.put(downAnimation, fillRectangle2);
        // Fill leftwards
        Rectangle fillRectangle3 = new Rectangle(CELL_WIDTH, 0, 0, CELL_HEIGHT);
        leftAnimation = new TranslateTransition(FILL_DURATION, fillRectangle3);
        leftAnimation.setByX(-CELL_WIDTH);
        animationToRectangleMap.put(leftAnimation, fillRectangle3);
        // Fill rightwards
        Rectangle fillRectangle4 = new Rectangle(0, 0, 0, CELL_HEIGHT);
        rightAnimation = new TranslateTransition(FILL_DURATION, fillRectangle4);
        rightAnimation.setByX(CELL_WIDTH);
        animationToRectangleMap.put(rightAnimation, fillRectangle4);
    }

    private void enqueueAnimation(short fillTo) {
        Rectangle fillRectangle;
        TranslateTransition fillAnimation = null;
        switch (fillTo) {
            case TOP:
                fillAnimation = upAnimation;
                break;
            case BOTTOM:
                fillAnimation = downAnimation;
                break;
            case LEFT:
                fillAnimation = leftAnimation;
                break;
            case RIGHT:
                fillAnimation = rightAnimation;
                break;
            default:
                throw new IllegalArgumentException("No such direction");
        }
        fillRectangle = animationToRectangleMap.get(fillAnimation);
        fillRectangle.setFill(Color.ORANGE);
        if (!pane.getChildren().contains(fillRectangle))
            pane.getChildren().add(fillRectangle);
        animationQueue.enqueue(fillAnimation);
        //fillAnimation.setOnFinished(event -> this.pane.getChildren().remove(fillRectangle));
    }


    public void openCell(short direction) {
        pane.getStyleClass().remove("filled");
        wallsMap.replace(direction, false);
        enqueueAnimation(direction);
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
        TranslateTransition nextAnimation = animationQueue.dequeue();
        System.out.println(nextAnimation.toString());
        nextAnimation.setOnFinished(event -> playAnimations());
        nextAnimation.play();
    }
}
