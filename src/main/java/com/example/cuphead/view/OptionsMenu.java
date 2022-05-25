package com.example.cuphead.view;

import com.example.cuphead.controller.GameController;
import com.example.cuphead.controller.StageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsMenu implements Initializable {
    @FXML
    private RadioButton easy;
    @FXML
    private RadioButton hard;
    @FXML
    private RadioButton normal;
    @FXML
    private ToggleButton muteCheckBox;


    public void mute(ActionEvent actionEvent) {
        StageController.setSound(!muteCheckBox.isSelected());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup tg = new ToggleGroup();
        easy.setToggleGroup(tg);
        normal.setToggleGroup(tg);
        hard.setToggleGroup(tg);
        normal.setSelected(true);
        muteCheckBox.setSelected(!StageController.getSound());

    }

    public void difficulty(ActionEvent actionEvent) {
        if(actionEvent.getSource() == easy) GameController.setLevel(1);
        if(actionEvent.getSource() == normal) GameController.setLevel(2);
        if(actionEvent.getSource() == hard) GameController.setLevel(3);
    }

    public void back(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.MAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
