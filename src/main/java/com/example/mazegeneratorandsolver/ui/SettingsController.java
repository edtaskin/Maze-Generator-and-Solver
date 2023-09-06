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
    private Slider removeWallAnimationSpeed, fillCellAnimationSpeed;

    @FXML
    private ColorPicker cellBackgroundColor, wallColor, fillColor;

    @FXML
    private CheckBox displayMST;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
        removeWallAnimationSpeed.setValue(settings.getRemoveWallAnimationSpeed());
        fillCellAnimationSpeed.setValue(settings.getFillCellAnimationSpeed());
        cellBackgroundColor.setValue(settings.getCellBackgroundColor());
        wallColor.setValue(settings.getWallColor());
        fillColor.setValue(settings.getFillColor());
        displayMST.setSelected(settings.isDisplayMST());
    }

    @FXML
    private void goToPreviousPage() {
        sceneManager.switchScene("title.fxml");
        applySettings();
    }

    @FXML
    private void applySettings() {
        settings.setRemoveWallAnimationSpeed(removeWallAnimationSpeed.getValue());
        settings.setFillCellAnimationSpeed(fillCellAnimationSpeed.getValue());
        settings.setCellBackgroundColor(cellBackgroundColor.getValue());
        settings.setWallColor(wallColor.getValue());
        settings.setFillColor(fillColor.getValue());
        settings.setDisplayMST(displayMST.isSelected());
    }
}
