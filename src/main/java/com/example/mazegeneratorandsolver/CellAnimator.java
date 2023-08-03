package com.example.mazegeneratorandsolver;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class CellAnimator implements Directions {
    private MyQueue<Animation> animationsQueue;

    /*
    Constructor for maze generation animations
     */
    public CellAnimator() {
        animationsQueue = new MyQueue<>();
    }

    public void enqueueAnimation(Animation animation) {
        if (animation == null) return;
        animationsQueue.enqueue(animation);
    }

    public void playAnimations() {
        if (animationsQueue.isEmpty())
            return;
        Animation animation = animationsQueue.dequeue();
        animation.setOnFinished(e -> playAnimations());
        animation.play();
    }

}
