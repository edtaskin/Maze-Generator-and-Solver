package com.example.mazegeneratorandsolver.ui;

import javafx.scene.paint.Color;

// Singleton class
public class Settings {
    private static Settings instance;

    private Color cellBackgroundColor, wallColor, fillColor;
    private double removeWallAnimationSpeed, fillCellAnimationSpeed;
    private boolean displayMST;

    public Color getCellBackgroundColor() {
        return cellBackgroundColor;
    }

    public void setCellBackgroundColor(Color cellBackgroundColor) {
        this.cellBackgroundColor = cellBackgroundColor;
    }

    public Color getWallColor() {
        return wallColor;
    }

    public void setWallColor(Color wallColor) {
        this.wallColor = wallColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public double getRemoveWallAnimationSpeed() {
        return removeWallAnimationSpeed;
    }

    public void setRemoveWallAnimationSpeed(double removeWallAnimationSpeed) {
        this.removeWallAnimationSpeed = removeWallAnimationSpeed;
    }

    public double getFillCellAnimationSpeed() {
        return fillCellAnimationSpeed;
    }

    public void setFillCellAnimationSpeed(double fillCellAnimationSpeed) {
        this.fillCellAnimationSpeed = fillCellAnimationSpeed;
    }

    public boolean isDisplayMST() {
        return displayMST;
    }

    public void setDisplayMST(boolean displayMST) {
        this.displayMST = displayMST;
    }

    // Default settings
    private Settings() {
        cellBackgroundColor = Color.BLACK;
        wallColor = Color.RED;
        fillColor = Color.BLUE;
        removeWallAnimationSpeed = fillCellAnimationSpeed = 1;
        displayMST = false;
    }

    public Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }
}
