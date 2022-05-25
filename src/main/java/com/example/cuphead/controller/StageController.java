package com.example.cuphead.controller;

import com.example.cuphead.Main;
import com.example.cuphead.view.AudioEnum;
import com.example.cuphead.view.MediaContoller;
import com.example.cuphead.view.MenuFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController {
    private static Stage stage;
    private static Scene scene;
    private static MediaPlayer mediaPlayer;
    private static MediaContoller mediaContoller;
    private static boolean sound =true;
    private static AudioClip audioClip = new AudioClip(Main.class.getResource("audio/bullet.mp3").toExternalForm());
    public static boolean getSound() {
        return sound;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer, boolean game) {
        if (mediaContoller == null) mediaContoller = new MediaContoller();
        StageController.mediaPlayer = mediaPlayer;
        if(!game) mediaPlayer.setOnEndOfMedia(mediaContoller);
        else mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
    public static void playBullet(){
        if(sound) audioClip.play();
    }
    public static void audioPlay(){
        if(sound) mediaPlayer.play();
    }
    public static void audioPause(){
        mediaPlayer.pause();
    }
    public static void audioStop(){
        mediaPlayer.stop();
    }

    public static void setSound(boolean sound) {
        StageController.sound = sound;
        if(sound) mediaPlayer.play();
        else mediaPlayer.stop();
    }

    public static void setStage(Stage stageIn) {
        stage = stageIn;
    }

    public static void changeScene(MenuFxml menuFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(menuFxml.getAddress()));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 730);
        stage.setScene(scene);
        StageController.scene = scene;
        stage.show();
    }

    public static Scene getScene() {
        return scene;
    }
}
