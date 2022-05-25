package com.example.cuphead.controller;

import com.example.cuphead.model.User;

public class LoginController {
    private static User loggedUser;

    public static int createNewUser(String username,
                                    String password) {
        if (User.findUser(username) != null)
            return 1;
        if (!isPasswordValid(password))
            return 3;
        loggedUser = new User(username, password);
        return 0;
    }

    public static int loginUser(String username, String password) {
        User tempUser = User.findUser(username);
        if (tempUser == null)
            return 1;
        if (!tempUser.isPasswordCorrect(password))
            return 2;
        loggedUser = tempUser;
        return 0;
    }


    public static int changeData(String currentPassword,
                                     String newPassword,String username) {
        if(newPassword.length() == 0) newPassword = getLoggedUser().getPassword();
        if(username.length() == 0) username = getLoggedUser().getPassword();
        if (!loggedUser.isPasswordCorrect(currentPassword))
            return 1;
        if (!isPasswordValid(newPassword))
            return 2;
        if(User.findUser(username) != null && User.findUser(username) != LoginController.getLoggedUser())
            return 3;
        loggedUser.changeData(newPassword,username);
        return 0;
    }

    private static boolean isPasswordValid(String password) {
        if (password.length() < 8 ||
                password.length() > 32)
            return false;
        boolean has_small_letter = false,
                has_big_letter = false,
                has_numbers = false;
        for (int i = 0; i < password.length(); i++)
            if (password.charAt(i) >= 'a' &&
                    password.charAt(i) <= 'z') {
                has_small_letter = true;
                break;
            }
        if (!has_small_letter)
            return false;
        for (int i = 0; i < password.length(); i++)
            if (password.charAt(i) >= 'A' &&
                    password.charAt(i) <= 'Z') {
                has_big_letter = true;
                break;
            }
        if (!has_big_letter)
            return false;
        for (int i = 0; i < password.length(); i++)
            if (password.charAt(i) >= '0' &&
                    password.charAt(i) <= '9') {
                has_numbers = true;
                break;
            }
        return has_numbers;
    }
    public static User getLoggedUser() {
        return loggedUser;
    }
}

