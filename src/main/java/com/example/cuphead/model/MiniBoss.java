package com.example.cuphead.model;

import com.example.cuphead.Main;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class MiniBoss {
    private double health = 2;
    private Group group;
    private static Image[] images;
    static {
        images = new Image[9];
        for (int i = 0; i <= 8; i++) {
            images[i] = new Image(Main.class.getResource
                    ("images/miniBoss/" + i + ".png").toExternalForm());
        }
    }
    public MiniBoss(Group group){
        this.health = 2;
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public double getHealth() {
        return health;
    }

    public void takeDamage(double health) {
        this.health -= health;
    }

    public static Image[] getImages() {
        return images;
    }
}
