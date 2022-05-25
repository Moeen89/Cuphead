package com.example.cuphead;

import com.example.cuphead.controller.StageController;
import com.example.cuphead.view.MenuFxml;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageController.setStage(stage);
        Media media = new Media(getClass().getResource("audio/01 Don't Deal With The Devil.mp3").toExternalForm());
        StageController.setMediaPlayer(new MediaPlayer(media),false);
        StageController.audioPlay();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(MenuFxml.LOGIN.getAddress()));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 730);
        stage.setTitle("Cuphead!");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResource("icon/icon.jpg").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}