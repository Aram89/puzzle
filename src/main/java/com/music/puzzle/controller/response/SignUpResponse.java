package com.music.puzzle.controller.response;


public class SignUpResponse {

    private String token;
    private String location;

    public SignUpResponse(String token, String location) {
        this.token = token;
        this.location = location;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
