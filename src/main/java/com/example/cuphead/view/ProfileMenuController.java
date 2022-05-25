package com.example.cuphead.view;

import com.example.cuphead.Main;
import com.example.cuphead.controller.LoginController;
import com.example.cuphead.controller.StageController;
import com.example.cuphead.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuController implements Initializable {

    @FXML private Label errorBox;
    @FXML private TextField username;
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;
    @FXML private Button f2;
    @FXML private Button f1;
    @FXML private Button f3;
    @FXML private Button f4;
    @FXML private Button m1;
    @FXML private Button m2;
    @FXML private Button m3;
    @FXML private Button o;
    @FXML
    private Button profile;
    public void initialize(URL location, ResourceBundle resources) {
        changeProfile(false,profile,200,LoginController.getLoggedUser().getIcon());
        changeProfile(false,f1,100,UserIcon.FEMALE1);
        changeProfile(false,f2,100,UserIcon.FEMALE2);
        changeProfile(false,f3,100,UserIcon.FEMALE3);
        changeProfile(false,f4,100,UserIcon.FEMALE4);
        changeProfile(false,m1,100,UserIcon.MALE1);
        changeProfile(false,m2,100,UserIcon.MALE2);
        changeProfile(false,m3,100,UserIcon.MALE3);
        changeProfile(false,o,100,UserIcon.OTHER);

    }



    public void profileGif(MouseEvent mouseEvent) {
        changeProfile(true,profile,200,LoginController.getLoggedUser().getIcon());
    }
    public static void changeProfile(boolean toGif,Button profile,int size,UserIcon icon){
        String Address = Main.class.getResource(icon.image).toExternalForm();
        if(toGif) Address =  Main.class.getResource(icon.gif).toExternalForm();
        Image image = new Image(Address);
        BackgroundImage b = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(size,size,false,false,false,false));
        Background background = new Background(b);
        profile.setBackground( background);
    }

    public void profileImage(MouseEvent mouseEvent) {
        changeProfile(false,profile,200,LoginController.getLoggedUser().getIcon());
    }

    public void setProfile(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == f1) LoginController.getLoggedUser().setIcon(UserIcon.FEMALE1);
        else if(mouseEvent.getSource() == f2) LoginController.getLoggedUser().setIcon(UserIcon.FEMALE2);
        else if(mouseEvent.getSource() == f3) LoginController.getLoggedUser().setIcon(UserIcon.FEMALE3);
        else if(mouseEvent.getSource() == f4) LoginController.getLoggedUser().setIcon(UserIcon.FEMALE4);
        else if(mouseEvent.getSource() == m1) LoginController.getLoggedUser().setIcon(UserIcon.MALE1);
        else if(mouseEvent.getSource() == m2) LoginController.getLoggedUser().setIcon(UserIcon.MALE2);
        else if(mouseEvent.getSource() == m3) LoginController.getLoggedUser().setIcon(UserIcon.MALE3);
        else if(mouseEvent.getSource() == o) LoginController.getLoggedUser().setIcon(UserIcon.OTHER);
        changeProfile(false,profile,200,LoginController.getLoggedUser().getIcon());
    }

    public void changeUserData(MouseEvent mouseEvent) {
        switch (LoginController.changeData(oldPassword.getText(),newPassword.getText(),username.getText())){
            case 0:
                errorBox.setText("changed successfully");
                break;
            case 1:
                errorBox.setText("wrong password");
                break;
            case 2:
                errorBox.setText("not valid password");
                break;
            case 3:
                errorBox.setText("Username already exist");
                break;
        }
    }

    public void delete(MouseEvent mouseEvent) {
        User.deleteUser(LoginController.getLoggedUser());
        try {
            StageController.changeScene(MenuFxml.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.MAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
