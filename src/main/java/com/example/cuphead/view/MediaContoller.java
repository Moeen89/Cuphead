package com.example.cuphead.view;

import com.example.cuphead.controller.StageController;
import javafx.scene.media.MediaPlayer;

public class MediaContoller implements Runnable{
    private static int number;
    @Override
    public void run() {
        number += 1;
        number %=2;
        if(number == 0) StageController.setMediaPlayer(new MediaPlayer(AudioEnum.MUSIC_DDWTD.getMedia()),false);
        else StageController.setMediaPlayer(new MediaPlayer(AudioEnum.MUSIC_DDWTDI.getMedia()),true);
        StageController.audioPlay();
    }
}
