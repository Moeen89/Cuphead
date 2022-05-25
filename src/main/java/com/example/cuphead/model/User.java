package com.example.cuphead.model;

import com.example.cuphead.Main;
import com.example.cuphead.view.UserIcon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class User {
    private static ArrayList<User> listOfUsers = new ArrayList<>();
    private String username;
    private String password;
    UserIcon icon;
    int[] score = new int[3];
    int[] time = new int[3];

    public void setScore(int i,int score) {
        this.score[i] = score;
        if(!username.equals("NaserKazemi"))saveData();
    }
    public void setTime(int i,int time){
        this.time[i] = time;
        if(!username.equals("NaserKazemi"))saveData();
    }

    public int getTime(int i) {
        return time[i];
    }

    public int getScore(int i) {
        return score[i];
    }

    public UserIcon getIcon() {
        return icon;
    }

    static {
        try {
            String json = new String(Files.readAllBytes(Paths.get("users.json")));
            listOfUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        } catch (IOException e) {
            File file = new File("users.json");
            try {
                file.createNewFile();
                listOfUsers = new ArrayList<>();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void saveData() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("users.json");
            fileWriter.write(new Gson().toJson(listOfUsers));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static User findUser(String string) {
        for (User listOfUser : listOfUsers)
            if ((Objects.equals(listOfUser.username, string)))
                return listOfUser;
        return null;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        listOfUsers.add(this);
        icon = UserIcon.OTHER;
        icon = UserIcon.randomIcon();
        saveData();
    }

    public void setIcon(UserIcon icon) {
        this.icon = icon;
        saveData();
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }


    public void changeData(String newPassword,String username) {
        this.password = newPassword;
        this.username = username;
        saveData();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static void deleteUser(User user) {
        if (user == null)
            return;
        listOfUsers.remove(user);
        saveData();
    }

    public static ArrayList<User> getTopTen(int i){
        ArrayList<User> top = (ArrayList<User>) listOfUsers.clone();
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(o1.score[i] < o2.score[i]) return 1;
                if(o1.score[i] > o2.score[i]) return -1;
                if(o1.time[i] > o2.time[i]) return -1;
                if(o1.time[i] < o2.time[i]) return -1;
                return 0;
            }
        };
        top.sort(comparator);
        return top;
    }
}
