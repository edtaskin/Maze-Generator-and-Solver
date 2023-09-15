package com.example.mazegeneratorandsolver.maze;

import com.example.mazegeneratorandsolver.ui.Settings;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CellAnimator {
    private static Settings settings = Settings.getInstance();

    public MyQueue<Animation> animationsQueue; // TODO Make it private
    private MyQueue<Animation> finishedAnimationsQueue;

    public CellAnimator() {
        animationsQueue = new MyQueue<>();
        finishedAnimationsQueue = new MyQueue<>();
    }

    public static FadeTransition getFadeTransitionInDirection(Cell cell, Directions direction) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(cell.getWallInDirection(direction));
        transition.setByValue(-1);
        transition.setDuration(Duration.millis(getFadeDuration()));
        return transition;
    }

    // TODO Consider a more fluid-like fill animation later
    public static FillTransition getFillTransition(Cell cell) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(Cell.CELL_WIDTH);
        rectangle.setHeight(Cell.CELL_HEIGHT);
        cell.setCenter(rectangle);

        FillTransition transition = new FillTransition();
        transition.setFromValue(settings.getCellBackgroundColor());
        transition.setToValue(settings.getFillColor());
        transition.setShape(rectangle);
        transition.setDuration(Duration.millis(getFillDuration()));
        return transition;
    }

    public void enqueueAnimation(Animation animation) {
        if (animation == null) return;
        animationsQueue.enqueue(animation);
    }

    public void play() {
        Animation currentAnimation = animationsQueue.dequeue();
        currentAnimation.play();
        while (!animationsQueue.isEmpty()) { // Was animationsQueue.size() > 1
            finishedAnimationsQueue.enqueue(currentAnimation);
            Animation nextAnimation = animationsQueue.dequeue();
            currentAnimation.setOnFinished(e -> nextAnimation.play());
            currentAnimation = nextAnimation;
        }
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

    public Animation getLastAnimation() {
        return animationsQueue.tail();
    }

    private static int getFadeDuration() {
        return (int) (200 / settings.getRemoveWallAnimationSpeed());
    }

    private static int getFillDuration() {
        return (int) (200 / settings.getFillCellAnimationSpeed());
    }
}
