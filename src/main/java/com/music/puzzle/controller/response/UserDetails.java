package com.music.puzzle.controller.response;

/**
 * Created by Aram on 11/11/18.
 */
public class UserDetails {
    private String userName;
    private String location;
    private int score;
    private int position;

    public UserDetails(String userName, String location, int score) {
        this.userName = userName;
        this.location = location;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
