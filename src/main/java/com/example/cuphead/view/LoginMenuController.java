package com.example.cuphead.view;

import com.example.cuphead.Main;
import com.example.cuphead.controller.LoginController;
import com.example.cuphead.controller.StageController;
import com.example.cuphead.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static com.example.cuphead.controller.StageController.changeScene;

public class LoginMenuController {
    @FXML
    private Label errorBox;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;



    public void register(MouseEvent mouseEvent) {
        if(username.getText().length()>0 && password.getText().length()>0)
            switch (LoginController.createNewUser(username.getText(), password.getText())) {
                case 0:
                    try {
                        changeScene(MenuFxml.MAIN);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    errorBox.setText("username already exists");
                    break;
                case 3:
                    errorBox.setText("password is not valid");
                    break;
            }
    }

    public void login(MouseEvent mouseEvent) {
        if (LoginController.loginUser(username.getText(), password.getText()) == 0) {
            try {
                changeScene(MenuFxml.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorBox.setText("Username or password is wrong");
        }
    }

    public void guestUser(MouseEvent mouseEvent) {
        LoginController.createNewUser("NaserKazemi","Kazemtopia13");
        User.deleteUser(LoginController.getLoggedUser());
        try {
            changeScene(MenuFxml.MAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
