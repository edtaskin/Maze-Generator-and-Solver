package com.example.mazegeneratorandsolver.ui;

import javafx.scene.paint.Color;

public class Settings {
    private static Settings instance;

    private int rowCount, colCount;
    private Color cellBackgroundColor, wallColor, fillColor;
    private double removeWallAnimationSpeed, fillCellAnimationSpeed;
    private boolean displayMST;

    // Default settings
    private Settings() {
        rowCount = colCount = 10;
        cellBackgroundColor = Color.BLACK;
        wallColor = Color.RED;
        fillColor = Color.BLUE;
        removeWallAnimationSpeed = fillCellAnimationSpeed = 1;
        displayMST = false;
    }

    public static Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

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

}
