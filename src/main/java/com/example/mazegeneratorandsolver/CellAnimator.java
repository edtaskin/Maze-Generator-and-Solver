package com.example.mazegeneratorandsolver;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CellAnimator {
    private static final int FADE_DURATION = 200;

    private MyQueue<Animation> animationsQueue;
    private MyQueue<Animation> finishedAnimationsQueue;

    public CellAnimator() {
        animationsQueue = new MyQueue<>();
        finishedAnimationsQueue = new MyQueue<>();
    }

    public static FadeTransition getFadeTransitionInDirection(Cell cell, Directions direction) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(cell.getWallInDirection(direction));
        transition.setByValue(-1);
        transition.setDuration(Duration.millis(FADE_DURATION));
        return transition;
    }

    // TODO Consider a more fluid-like fill animation later
    public static FillTransition getFillTransition(Cell cell) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(Cell.CELL_WIDTH);
        rectangle.setHeight(Cell.CELL_HEIGHT);
        cell.setCenter(rectangle);

        FillTransition transition = new FillTransition();
        transition.setFromValue(Color.BLACK);
        transition.setToValue(Color.BLUE);
        transition.setShape(rectangle);
        transition.setDuration(Duration.millis(FADE_DURATION));
        return transition;
    }

    public void enqueueAnimation(Animation animation) {
        if (animation == null) return;
        animationsQueue.enqueue(animation);
    }

    public void play() {
        if (animationsQueue.isEmpty())
            return;
        Animation animation = animationsQueue.dequeue();
        animation.setOnFinished(e -> play());
        animation.play();
        finishedAnimationsQueue.enqueue(animation);
    }

    public void replay() {
        animationsQueue = finishedAnimationsQueue;
        finishedAnimationsQueue.clear();
        play();
    }

    public void clearQueue() {
        animationsQueue.clear();
        finishedAnimationsQueue.clear();
    }



}
