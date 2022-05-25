package com.example.cuphead.view;

import com.example.cuphead.Main;
import javafx.scene.media.Media;

public enum AudioEnum {
    MUSIC_DDWTD(new javafx.scene.media
            .Media(Main.class.getResource("audio/01 Don't Deal With The Devil.mp3").toExternalForm())),
    MUSIC_DDWTDI(new javafx.scene.media
            .Media(Main.class.getResource("audio/02 Don't Deal With The Devil (Instrumental).mp3").toExternalForm())),
    MUSIC_TA(new javafx.scene.media
            .Media(Main.class.getResource("audio/45 The Airship.mp3").toExternalForm())),
    BULLET(new javafx.scene.media
            .Media(Main.class.getResource("audio/45 The Airship.mp3").toExternalForm()));

    private final Media media;
    public Media getMedia(){
        return media;
    }
    AudioEnum(Media media) {
        this.media = media;
    }
}
