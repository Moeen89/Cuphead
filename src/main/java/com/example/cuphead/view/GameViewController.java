package com.example.cuphead.view;

import com.example.cuphead.Main;
import com.example.cuphead.controller.GameController;
import com.example.cuphead.controller.StageController;
import com.example.cuphead.model.Boss;
import com.example.cuphead.model.Bullet;
import com.example.cuphead.model.MiniBoss;
import com.example.cuphead.model.Mugman;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    public ImageView background1;
    public ImageView background2;
    public AnchorPane pane;
    public AnchorPane pausePane;
    public ProgressBar progress;
    public Label bossHp;
    private Mugman mugman;
    private Boss boss;
    private boolean keyW;
    private boolean keyA;
    private boolean keyS;
    private boolean keyD;
    private boolean keySpace;
    private boolean shoot;
    private int timer;
    private Random random = new Random();
    ArrayList<Transition> transitions = new ArrayList<>();
    ArrayList<Timeline> timelines = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    ArrayList<Group> enemy = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundTransition();
        mugmanAnimation();
        bossAnimation();
        oneTwentieth();
        GameController.setController(this, pane, transitions, timelines, bullets, progress, bossHp);
        enemy.add(boss.getBoss());
        boss.move(transitions);
        Platform.runLater(() -> background1.requestFocus());
        background1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyPressHandler(keyEvent);
            }
        });
        background1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyReleaseHandler(keyEvent);
            }
        });
        StageController.setMediaPlayer(new MediaPlayer(AudioEnum.MUSIC_TA.getMedia()), true);
        StageController.audioPlay();
        progress.setProgress(1);
        bossHp.setFont(new Font(30));
        progress.setStyle("-fx-border-width: 0");
    }

    private void oneTwentieth() {
        Timeline oneTenth = new Timeline(
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        timer = (timer + 1) % 1000;
                        GameController.checkCollision(bullets, boss, pane, mugman);
                        mugmanMove();
                        if (timer % 10 == 9 && !shoot) shootBullet();
                        if (timer % 10 == 4) {
                            shoot = false;
                        }
                        if( timer % 50 == 1 || timer % 50 == 10) miniBoss();
                        if (timer % 20 == 19) GameController.changeTime(1);
                        if (timer % 20 > 15 + random.nextInt(20)) GameController.shootEgg(boss);
                    }
                }
                )
        );
        oneTenth.setCycleCount(Timeline.INDEFINITE);
        oneTenth.play();
        timelines.add(oneTenth);
    }

    private void backgroundTransition() {
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(3000), background1);
        translateTransition.setFromX(0);
        translateTransition.setToX(-3240);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(3000), background2);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-3240);
        translateTransition2.setInterpolator(Interpolator.LINEAR);
        ParallelTransition parallelTransition =
                new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();
        transitions.add(parallelTransition);

    }

    private void mugmanAnimation() {
        Group mugmanDefault = new Group();
        this.mugman = new Mugman(mugmanDefault);
        ImageView[] imageView = new ImageView[8];
        for (int i = 1; i < 9; i++) {
            imageView[i - 1] = new ImageView
                    (new Image(Main.class.getResource
                            ("images/Plane/default/" + i + ".png").toExternalForm()));
            mugmanDefault.getChildren().add(imageView[i - 1]);
        }
        mugmanDefault.setTranslateX(100);
        mugmanDefault.setTranslateY(100);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70 + 70 * i), actionEvent
                    -> {
                mugmanDefault.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        for (int i = 7; i >= 0; i--) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1050 - 70 * finalI), actionEvent
                    -> {
                mugmanDefault.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        timeline.play();
        pane.getChildren().add(mugmanDefault);
        timelines.add(timeline);
    }

    public ArrayList<MiniBoss> getMiniBosses() {
        return miniBosses;
    }

    private void mugmanMove() {
        if (keyW) mugman.getGroup().setTranslateY(mugman.getGroup().getTranslateY() - 15);
        if (keyA) mugman.getGroup().setTranslateX(mugman.getGroup().getTranslateX() - 15);
        if (keyS) mugman.getGroup().setTranslateY(mugman.getGroup().getTranslateY() + 15);
        if (keyD) mugman.getGroup().setTranslateX(15 + mugman.getGroup().getTranslateX());
        if (mugman.getGroup().getTranslateY() > 645) mugman.getGroup().setTranslateY(645);
        if (mugman.getGroup().getTranslateY() < 0) mugman.getGroup().setTranslateY(0);
        if (mugman.getGroup().getTranslateX() > 1140) mugman.getGroup().setTranslateX(1140);
        if (mugman.getGroup().getTranslateX() < 0) mugman.getGroup().setTranslateX(0);
    }

    public void miniBoss() {
        Group flappy = new Group();
        ImageView[] imageView = new ImageView[9];
        Image[] images = MiniBoss.getImages();
        for (int i = 1; i <= 9; i++) {
            imageView[i - 1] = new ImageView(images[i - 1]);
            flappy.getChildren().add(imageView[i - 1]);
        }
        flappy.setTranslateX(1300);
        flappy.setTranslateY(20 + random.nextInt(500));
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        for (int i = 0; i <= 8; i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70 + 70 * i), actionEvent
                    -> {
                flappy.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        for (int i = 8; i >= 0; i--) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1120 - 70 * finalI), actionEvent
                    -> {
                flappy.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        MiniBoss miniBoss = new MiniBoss(flappy);
        TranslateTransition transition = new TranslateTransition(Duration.millis(2 * boss.getBoss().getTranslateX()),flappy);
        transition.setToX(-100);
        transition.setOnFinished((finish) -> {
                    timelines.remove(timeline);
                    transitions.remove(transition);
                    pane.getChildren().remove(flappy);
                    miniBosses.remove(miniBoss);
                }
        );
        transition.setInterpolator(Interpolator.LINEAR);
        miniBosses.add(miniBoss);
        transition.play();
        timeline.play();
        pane.getChildren().add(flappy);
        timelines.add(timeline);
        transitions.add(transition);

    }


    public void keyPressHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) keyW = true;
        else if (keyEvent.getCode() == KeyCode.A) keyA = true;
        else if (keyEvent.getCode() == KeyCode.S) keyS = true;
        else if (keyEvent.getCode() == KeyCode.D) keyD = true;
        else if (keyEvent.getCode() == KeyCode.SPACE) {
            keySpace = true;
            if (!shoot)
                shootBullet();
        } else if (pausePane == null && keyEvent.getCode() == KeyCode.ESCAPE) pauseGame(true, false);

    }

    public void keyReleaseHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) keyW = false;
        else if (keyEvent.getCode() == KeyCode.A) keyA = false;
        else if (keyEvent.getCode() == KeyCode.S) keyS = false;
        else if (keyEvent.getCode() == KeyCode.D) keyD = false;
        else if (keyEvent.getCode() == KeyCode.SPACE) keySpace = false;
    }

    public void shootBullet() {
        shoot = true;
        if (keySpace) {
            StageController.playBullet();
            Group bullet = new Group();
            ImageView[] imageView = new ImageView[9];
            Image[] images = GameController.getBullet();
            for (int i = 1; i <= 9; i++) {
                imageView[i - 1] = new ImageView(images[i - 1]);
                bullet.getChildren().add(imageView[i - 1]);
            }
            bullet.setTranslateX(mugman.getGroup().getTranslateX());
            bullet.setTranslateY(mugman.getGroup().getTranslateY() + 20);
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            for (int i = 0; i <= 8; i++) {
                int finalI = i;
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70 + 70 * i), actionEvent
                        -> {
                    bullet.getChildren().setAll(imageView[finalI]);
                }
                ));
            }
            for (int i = 8; i >= 0; i--) {
                int finalI = i;
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1190 - 70 * finalI), actionEvent
                        -> {
                    bullet.getChildren().setAll(imageView[finalI]);
                }
                ));
            }
            Bullet b = new Bullet(bullet, true);
            TranslateTransition transition = new TranslateTransition(Duration.millis((1280 - bullet.getTranslateX())), bullet);
            transition.setToX(1280);
            transition.setOnFinished((finish) -> {
                        timelines.remove(timeline);
                        transitions.remove(transition);
                        pane.getChildren().remove(bullet);
                        bullets.remove(b);
                    }
            );
            transition.play();
            timeline.play();
            pane.getChildren().add(bullet);
            timelines.add(timeline);
            transitions.add(transition);
            bullets.add(b);
        }
    }

    private void bossAnimation() {
        Group boss = new Group();
        ImageView[] imageView = new ImageView[8];
        Image[] images = Boss.getImages();
        for (int i = 1; i < 7; i++) {
            imageView[i - 1] = new ImageView(images[i - 1]);
            imageView[i - 1].setFitHeight(313);
            imageView[i - 1].setFitWidth(400);
            boss.getChildren().add(imageView[i - 1]);
        }
        boss.setTranslateX(900);
        boss.setTranslateY(0);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70 + 70 * i), actionEvent
                    -> {
                boss.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        for (int i = 5; i >= 0; i--) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(770 - 70 * finalI), actionEvent
                    -> {
                boss.getChildren().setAll(imageView[finalI]);
            }
            ));
        }
        timeline.play();
        this.boss = new Boss(boss);
        pane.getChildren().add(boss);
        timelines.add(timeline);

    }

    public Mugman getMugman() {
        return mugman;
    }

    public void pauseGame(boolean pause, boolean lose) {
        GameController.pauseAnimations();
        AnchorPane pausePane = new AnchorPane();
        pausePane.setPrefHeight(600);
        pausePane.setPrefWidth(400);
        ImageView imageView = new ImageView(Main.class.getResource
                ("images/x.png").toExternalForm());
        imageView.setFitHeight(600);
        imageView.setFitWidth(400);
        pausePane.setLayoutX(440);
        pausePane.setLayoutY(60);
        Button resume = new Button();
        resume.setText("Resume");
        resume.setLayoutX(40);
        resume.setLayoutY(100);
        resume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resume();
            }
        });
        Button exit = new Button();
        exit.setText("exit");
        exit.setLayoutX(40);
        exit.setLayoutY(160);
        if (pause) exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    StageController.changeScene(MenuFxml.MAIN);
                    StageController.setMediaPlayer(new MediaPlayer(
                            new Media(Main.class.getResource
                                    ("audio/01 Don't Deal With The Devil.mp3").toExternalForm())), false);
                    StageController.audioPlay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        else {
            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        StageController.changeScene(MenuFxml.MAIN);
                        GameController.calculateScore();
                        StageController.setMediaPlayer(new MediaPlayer(
                                new Media(Main.class.getResource
                                        ("audio/01 Don't Deal With The Devil.mp3").toExternalForm())), false);
                        StageController.audioPlay();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Button restart = new Button();
        restart.setText("restart");
        restart.setLayoutX(40);
        restart.setLayoutY(130);
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    StageController.changeScene(MenuFxml.GAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Label labelH = new Label();
        labelH.setText("Hp: " + String.valueOf(mugman.getHp()));
        if (mugman.getHp() < 2) {
            labelH.setTextFill(Paint.valueOf("red"));
        }
        labelH.setFont(new Font(30));
        labelH.setLayoutX(250);
        Label label = new Label();
        label.setText("Score: " + GameController.getScore() +
                "\nTime: " + GameController.getTime() / 60 + ":" + GameController.getTime() % 60);
        label.setFont(new Font(30));
        label.setLayoutX(250);
        label.setLayoutY(40);
        pausePane.getChildren().add(imageView);
        if (pause) {
            pausePane.getChildren().add(resume);
            pausePane.getChildren().add(restart);
        }
        if (lose) pausePane.getChildren().add(restart);
        pausePane.getChildren().add(exit);
        pausePane.getChildren().add(label);
        pausePane.getChildren().add(labelH);
        pane.getChildren().add(pausePane);
        this.pausePane = pausePane;

    }

    public void resume() {
        pane.getChildren().remove(this.pausePane);
        this.pausePane = null;
        GameController.resumeAnimations();
        background1.requestFocus();
    }
}
