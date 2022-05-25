package com.example.cuphead.view;

public enum MenuFxml {
    LOGIN("fxml/login-menu.fxml"),
    MAIN("fxml/main-menu.fxml"),
    PROFILE("fxml/profile-menu.fxml"),
    OPTIONS("fxml/options-menu.fxml"),
    GAME("fxml/Game.fxml"),
    LEADERBOARD("fxml/leaderboard.fxml");

    private final String address;
    MenuFxml(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
