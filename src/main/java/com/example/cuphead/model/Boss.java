package com.example.cuphead.model;

import com.example.cuphead.Main;
import com.example.cuphead.controller.GameController;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

public class Boss {
    private double health = 100;
    private final Random random = new Random();
    private Transition transition;
    private ArrayList<Transition> transitions;
    private static Image[] images;
    private static Image egg;
    private Group boss;
    static {
        images = new Image[6];
        egg =  new Image(Main.class.getResource
                ("images/boss/egg.png").toExternalForm());
        for (int i = 1; i < 7; i++) {
            images[i-1] = new Image(Main.class.getResource
                    ("images/boss/" + i + ".png").toExternalForm());
        }
    }
    public static Image getEgg() {
        return egg;
    }

    public Boss(Group group){
        boss = group;
    }
    public void move(ArrayList<Transition> transitions){
        this.transitions = transitions;
        transitions.remove(transition);
        transition = new TranslateTransition(Duration.millis((1280 - boss.getTranslateX())), boss);
        ((TranslateTransition)transition).setToY(random.nextInt(500));
        transition.setOnFinished((finish) -> {
            move(transitions);
        });
        ((TranslateTransition)transition).setDuration(Duration.millis(1100));
        transition.play();
        transitions.add(transition);

    }

    public Group getBoss() {
        return boss;
    }

    public static Image[] getImages() {
        return images;
    }
    public void takeDamage(double x){
        health -= x;
        GameController.getProgressBar().setProgress(health/100);
        GameController.getBossHp().setText(String.valueOf(((int)health)));
        if (health <=0 ) {
            GameController.getPane().getChildren().remove(boss);
            GameController.win();
        }

    }



}
