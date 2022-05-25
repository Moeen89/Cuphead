package com.example.cuphead.model;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;

public class Bullet {
    private Group group;
    private boolean isAlive;
    private boolean isMugman;
    public Bullet(Group group,boolean isMugman){
        this.group = group;
        isAlive =true;
        this.isMugman = isMugman;
    }


    public Group getGroup() {
        return group;
    }
    public boolean getIsAlive() {
        return isAlive;
    }
    public boolean getIsMugman() {
        return isMugman;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


}
