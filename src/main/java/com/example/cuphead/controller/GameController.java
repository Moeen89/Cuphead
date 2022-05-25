package com.example.cuphead.controller;

import com.example.cuphead.Main;
import com.example.cuphead.model.Boss;
import com.example.cuphead.model.Bullet;
import com.example.cuphead.model.MiniBoss;
import com.example.cuphead.model.Mugman;
import com.example.cuphead.view.GameViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController {
    private static GameViewController gameViewController;
    private static int difficulty = 2;
    private static final Image[] bullet;
    private static Pane pane;
    private static ProgressBar progressBar;
    private static Label bossHp;
    private static ArrayList<Transition> transitions;
    private static ArrayList<Timeline> timelines;
    private static ArrayList<Bullet> bullets;
    private static int score;
    private static int time;

    public static int getTime() {
        return time;
    }

    public static int getScore() {
        return score;
    }

    public static Label getBossHp() {
        return bossHp;
    }

    static {
        bullet = new Image[9];
        for (int i = 1; i <= 9; i++) {
            bullet[i - 1] = new Image(Main.class.getResource
                    ("images/Plane/Bullet/" + i + ".png").toExternalForm());
        }
    }

    public static void blink() {
        Image[] images = new Image[4];
        for (int i = 10; i <= 13; i++) {
            images[i - 10] = new Image(Main.class.getResource
                    ("images/Plane/Bullet/" + i + ".png").toExternalForm());
        }
        Group group = gameViewController.getMugman().getGroup();
        gameViewController.getMugman().setBlink(true);
        timelines.get(0).pause();
        gameViewController.getMugman().getGroup().setOpacity(0);
        Group mugmanBlink = new Group();
        ImageView[] imageView = new ImageView[4];
        for (int i = 1; i <= 4; i++) {
            imageView[i - 1] = new ImageView(images[i - 1]);
            imageView[i - 1].setFitHeight(100);
            imageView[i - 1].setFitWidth(113);
            mugmanBlink.getChildren().add(imageView[i - 1]);
        }
        mugmanBlink.setTranslateX(gameViewController.getMugman().getGroup().getTranslateX());
        mugmanBlink.setTranslateY(gameViewController.getMugman().getGroup().getTranslateY());
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100 + 100 * i), actionEvent
                    -> {
                mugmanBlink.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        for (int i = 0; i < 18; i++) {
            int finalI = 2 + i % 2;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400 + 100 * i), actionEvent
                    -> {
                mugmanBlink.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        timeline.setOnFinished((finish) -> {
            timelines.remove(timeline);
            group.setTranslateY(gameViewController.getMugman().getGroup().getTranslateY());
            group.setTranslateX(gameViewController.getMugman().getGroup().getTranslateX());
            gameViewController.getMugman().setGroup(group);
            pane.getChildren().remove(mugmanBlink);
            timelines.get(0).play();
            gameViewController.getMugman().setBlink(false);
            gameViewController.getMugman().getGroup().setOpacity(1);
        });
        gameViewController.getMugman().setGroup(mugmanBlink);
        timeline.play();
        pane.getChildren().add(mugmanBlink);
        timelines.add(timeline);
    }

    public static void setController(GameViewController gameViewController, Pane pane, ArrayList<Transition> transitions, ArrayList<Timeline> timelines, ArrayList<Bullet> bullets, ProgressBar progressBar, Label label) {
        GameController.pane = pane;
        GameController.transitions = transitions;
        GameController.timelines = timelines;
        GameController.bullets = bullets;
        GameController.progressBar = progressBar;
        GameController.gameViewController = gameViewController;
        bossHp = label;
        score = 0;
        time = 0;
    }

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    public static Pane getPane() {
        return pane;
    }

    public static void setLevel(int level) {
        GameController.difficulty = level;
    }

    public static Image[] getBullet() {
        return bullet;
    }

    public static void checkCollision(ArrayList<Bullet> bullets, Boss boss, Pane pane, Mugman mugman) {
        Group enemyGroup;
        for (Bullet bullet1 : bullets) {
            for (Bullet bullet2 : bullets) {
                if (bullet1.getIsAlive() && bullet2.getIsAlive() && bullet1.getIsMugman() != bullet2.getIsMugman()
                        && bullet1.getGroup().getTranslateX() - bullet2.getGroup().getTranslateX() < 150 &&
                        bullet1.getGroup().getTranslateX() - bullet2.getGroup().getTranslateX() > -150 &&
                        bullet1.getGroup().getTranslateY() - bullet2.getGroup().getTranslateY() < 100 &&
                        bullet1.getGroup().getTranslateY() - bullet2.getGroup().getTranslateY() > -100) {
                    pane.getChildren().remove(bullet1.getGroup());
                    bullet1.setAlive(false);
                    pane.getChildren().remove(bullet2.getGroup());
                    bullet2.setAlive(false);
                    score++;
                }
            }
        }
        for (Bullet bullet : bullets) {
            enemyGroup = boss.getBoss();
            if (bullet.getIsMugman() &&
                    bullet.getIsAlive() && bullet.getGroup().getTranslateX() - enemyGroup.getTranslateX() < 200 &&
                    bullet.getGroup().getTranslateX() - enemyGroup.getTranslateX() > 0 &&
                    bullet.getGroup().getTranslateY() - enemyGroup.getTranslateY() < 300 &&
                    bullet.getGroup().getTranslateY() - enemyGroup.getTranslateY() > 0) {
                pane.getChildren().remove(bullet.getGroup());
                boss.takeDamage(2 - ((double) difficulty) / 2);
                score += 3;
                bullet.setAlive(false);
            } else if (!bullet.getIsMugman() && !mugman.getBlink()) {
                enemyGroup = mugman.getGroup();
                if (bullet.getIsAlive() && bullet.getGroup().getTranslateX() - enemyGroup.getTranslateX() < 150 &&
                        bullet.getGroup().getTranslateX() - enemyGroup.getTranslateX() > -50 &&
                        bullet.getGroup().getTranslateY() - enemyGroup.getTranslateY() < 100 &&
                        bullet.getGroup().getTranslateY() - enemyGroup.getTranslateY() > -100) {
                    pane.getChildren().remove(bullet.getGroup());
                    mugman.takeDamage((int) ((double) difficulty) / 2);
                    bullet.setAlive(false);
                }

            }
        }
        if (!mugman.getBlink() && mugman.getGroup().getTranslateX() - boss.getBoss().getTranslateX() < 200 &&
                mugman.getGroup().getTranslateX() - boss.getBoss().getTranslateX() > 0 &&
                mugman.getGroup().getTranslateY() - boss.getBoss().getTranslateY() < 300 &&
                mugman.getGroup().getTranslateY() - boss.getBoss().getTranslateY() > 0) {
            mugman.takeDamage((int) ((double) difficulty) / 2);
            boss.takeDamage(8 - ((double) difficulty * 2));
        }
        for (MiniBoss miniBoss : gameViewController.getMiniBosses()) {
            for (Bullet bullet1 : bullets) {
                if (bullet1.getIsAlive() && bullet1.getIsMugman() && miniBoss.getHealth() >0) {
                    if (bullet1.getIsAlive()
                            && bullet1.getGroup().getTranslateX() - miniBoss.getGroup().getTranslateX() < 150 &&
                            bullet1.getGroup().getTranslateX() - miniBoss.getGroup().getTranslateX() > -150 &&
                            bullet1.getGroup().getTranslateY() - miniBoss.getGroup().getTranslateY() < 100 &&
                            bullet1.getGroup().getTranslateY() - miniBoss.getGroup().getTranslateY() > -50) {
                        pane.getChildren().remove(bullet1.getGroup());
                        bullet1.setAlive(false);
                        miniBoss.takeDamage((2 -0.5 * difficulty));
                        if(miniBoss.getHealth() <= 0) {
                            pane.getChildren().remove(miniBoss.getGroup());
                            score++;
                        }
                    }
                }
            }
            if(!mugman.getBlink() && miniBoss.getHealth() > 0){
                enemyGroup = mugman.getGroup();
                if (miniBoss.getGroup().getTranslateX() - enemyGroup.getTranslateX() < 150 &&
                        miniBoss.getGroup().getTranslateX() - enemyGroup.getTranslateX() > -50 &&
                        miniBoss.getGroup().getTranslateY() - enemyGroup.getTranslateY() < 100 &&
                        miniBoss.getGroup().getTranslateY() - enemyGroup.getTranslateY() > -100) {
                    miniBoss.takeDamage((2 -0.5 * difficulty));
                    mugman.takeDamage((int) ((double) difficulty) / 2);
                    if(miniBoss.getHealth() <= 0) {
                        pane.getChildren().remove(miniBoss.getGroup());
                        score++;
                    }
                }
            }
        }
    }


    public static void win() {
        score += 40 * gameViewController.getMugman().getHp();
        gameViewController.pauseGame(false, false);
    }

    public static void calculateScore() {
        if (score > LoginController.getLoggedUser().getScore(difficulty - 1)) {
            LoginController.getLoggedUser().setScore(difficulty - 1, score);
            LoginController.getLoggedUser().setTime(difficulty - 1, time);
        } else if (score == LoginController.getLoggedUser().getScore(difficulty - 1)) {
            LoginController.getLoggedUser().setScore(difficulty - 1, score);
            if (time < LoginController.getLoggedUser().getTime(difficulty - 1))
                LoginController.getLoggedUser().setTime(difficulty - 1, time);
        }
    }

    public static void pauseAnimations() {
        for (Transition transition : transitions) transition.pause();
        for (Timeline timeline : timelines) timeline.pause();
        StageController.audioPause();
    }

    public static void resumeAnimations() {
        for (Transition transition : transitions) transition.play();
        for (Timeline timeline : timelines) timeline.play();
        StageController.audioPlay();
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static void changeScore(int score) {
        GameController.score += score;
    }

    public static void changeTime(int time) {
        GameController.time += time;
    }

    public static void shootEgg(Boss boss) {
        ImageView imageView = new ImageView(Boss.getEgg());
        Group egg = new Group(imageView);
        egg.setTranslateX(boss.getBoss().getTranslateX());
        egg.setTranslateY(boss.getBoss().getTranslateY() + 20);
        Bullet b = new Bullet(egg, false);
        TranslateTransition transition = new TranslateTransition(Duration.millis(1.5 * boss.getBoss().getTranslateX()), egg);
        transition.setToX(-100);
        transition.setOnFinished((finish) -> {
                    transitions.remove(transition);
                    pane.getChildren().remove(egg);
                    bullets.remove(b);
                }
        );
        transition.play();
        pane.getChildren().add(egg);
        bullets.add(b);
    }

    public static void loose() {
        gameViewController.pauseGame(false, true);
    }
}
