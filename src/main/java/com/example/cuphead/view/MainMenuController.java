package com.example.cuphead.view;

import com.example.cuphead.Main;
import com.example.cuphead.controller.LoginController;
import com.example.cuphead.controller.StageController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import com.example.cuphead.view.ProfileMenuController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.cuphead.view.ProfileMenuController.changeProfile;

public class MainMenuController implements Initializable {
    @FXML
    private Button profile;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        changeProfile(false,profile,100,LoginController.getLoggedUser().getIcon());
    }

    public void play(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.GAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void options(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void profile(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.PROFILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void leaderBoard(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.LEADERBOARD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void profileGif(MouseEvent mouseEvent) {
        changeProfile(true,profile,100,LoginController.getLoggedUser().getIcon());
    }

    public void profileImage(MouseEvent mouseEvent) {
        changeProfile(false,profile,100,LoginController.getLoggedUser().getIcon());
    }
}
