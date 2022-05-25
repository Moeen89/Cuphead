package com.example.cuphead.view;

import com.example.cuphead.controller.LoginController;
import com.example.cuphead.controller.StageController;
import com.example.cuphead.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.cuphead.view.ProfileMenuController.changeProfile;

public class LeaderboardController implements Initializable {

    @FXML private Button normal;
    @FXML private Button devil1;
    @FXML private Button devil2;
    @FXML private Label p1;
    @FXML private Label p2;
    @FXML private Label p3;
    @FXML private Label p4;
    @FXML private Label p5;
    @FXML private Label p6;
    @FXML private Label p7;
    @FXML private Label p8;
    @FXML private Label p9;
    @FXML private Label p10;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        setLeaderboard(0);
    }
    private void setLeaderboard(int i){
        ArrayList<User> users = User.getTopTen(i);
        switch (users.size()){
            default:
            case 10:
                p10.setText(10 + "- " +users.get(9).getUsername() +
                        " , score: " + users.get(9).getScore(i) + " , time: " +users.get(9).getTime(i) );
            case 9:
                p9.setText(9 + "- " +users.get(8).getUsername() +
                        " , score: " + users.get(8).getScore(i) + " , time: " +users.get(8).getTime(i) );
            case 8:
                p8.setText(8 + "- " +users.get(7).getUsername() +
                        " , score: " + users.get(7).getScore(i) + " , time: " +users.get(7).getTime(i) );
            case 7:
                p7.setText(7 + "- " +users.get(6).getUsername() +
                        " , score: " + users.get(6).getScore(i) + " , time: " +users.get(6).getTime(i) );
            case 6:
                p6.setText(6 + "- " +users.get(5).getUsername() +
                        " , score: " + users.get(5).getScore(i) + " , time: " +users.get(5).getTime(i) );
            case 5:
                p5.setText(5 + "- " +users.get(4).getUsername() +
                        " , score: " + users.get(4).getScore(i) + " , time: " +users.get(4).getTime(i) );
            case 4:
                p4.setText(4 + "- " +users.get(3).getUsername() +
                        " , score: " + users.get(3).getScore(i) + " , time: " +users.get(3).getTime(i) );
            case 3:
                p3.setText(3 + "- " +users.get(2).getUsername() +
                        " , score: " + users.get(2).getScore(i) + " , time: " +users.get(2).getTime(i) );
            case 2:
                p2.setText(2 + "- " +users.get(1).getUsername() +
                        " , score: " + users.get(1).getScore(i) + " , time: " +users.get(1).getTime(i) );
            case 1:
                p1.setText(1 + "- " +users.get(0).getUsername() +
                        " , score: " + users.get(0).getScore(i) + " , time: " +users.get(0).getTime(i) );
            case 0:
                break;
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            StageController.changeScene(MenuFxml.MAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDifficulty(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == normal) setLeaderboard(0);
        else if (mouseEvent.getSource() == devil1) setLeaderboard(1);
        else if (mouseEvent.getSource() == devil2) setLeaderboard(2);
    }
}
