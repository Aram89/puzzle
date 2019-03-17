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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetails details = (UserDetails) o;

        if (score != details.score) return false;
        if (position != details.position) return false;
        if (userName != null ? !userName.equals(details.userName) : details.userName != null) return false;
        return location != null ? location.equals(details.location) : details.location == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + position;
        return result;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userName='" + userName + '\'' +
                ", location='" + location + '\'' +
                ", score=" + score +
                ", position=" + position +
                '}';
    }
}
