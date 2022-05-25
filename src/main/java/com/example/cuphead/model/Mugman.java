package com.example.cuphead.model;

import com.example.cuphead.Main;
import com.example.cuphead.controller.GameController;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class Mugman {

    private double hp;
    private  Group group;
    private boolean isBlink;
    public Mugman(Group group){
        this.group = group;
        switch (GameController.getDifficulty()){
            case 1:
                hp = 10;
                break;
            case 2:
                hp = 5;
                break;
            case 3:
                hp = 2;
                break;
        }
    }
    public void setBlink(boolean blink) {
        isBlink = blink;
    }
    public boolean getBlink() {
        return isBlink;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public double getHp() {
        return hp;
    }
    public void takeDamage(int x){
        hp -= x;
        GameController.blink();
        if(hp <= 0) GameController.loose();
    }
}
