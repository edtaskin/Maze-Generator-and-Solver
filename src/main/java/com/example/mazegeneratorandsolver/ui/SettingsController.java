package com.example.mazegeneratorandsolver.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends Controller implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private Slider rowCount, colCount, removeWallAnimationSpeed, fillCellAnimationSpeed;

    @FXML
    private ColorPicker cellBackgroundColor, wallColor, fillColor;

    @FXML
    private CheckBox displayMST;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
        rowCount.setValue(settings.getRowCount());
        colCount.setValue(settings.getColCount());
        displayMST.setSelected(settings.isDisplayMST());

        removeWallAnimationSpeed.setValue(settings.getRemoveWallAnimationSpeed());
        fillCellAnimationSpeed.setValue(settings.getFillCellAnimationSpeed());
        cellBackgroundColor.setValue(settings.getCellBackgroundColor());

        wallColor.setValue(settings.getWallColor());
        fillColor.setValue(settings.getFillColor());

    }

    @FXML
    private void goToPreviousPage() {
        applySettings();
        sceneManager.switchScene("title.fxml");
    }

    private void applySettings() {
        settings.setRowCount((int) rowCount.getValue());
        settings.setColCount((int) colCount.getValue());
        settings.setDisplayMST(displayMST.isSelected());

        settings.setRemoveWallAnimationSpeed(removeWallAnimationSpeed.getValue());
        settings.setFillCellAnimationSpeed(fillCellAnimationSpeed.getValue());
        settings.setCellBackgroundColor(cellBackgroundColor.getValue());

        settings.setWallColor(wallColor.getValue());
        settings.setFillColor(fillColor.getValue());
    }
}
