package com.music.puzzle.controller.response;


import java.util.List;

public class RatingResponse {

    private int userPosition;
    private List<UserDetails> details;


    public RatingResponse(List<UserDetails> details, int userPosition) {
        this.details = details;
        this.userPosition = userPosition;
    }

    public List<UserDetails> getDetails() {
        return details;
    }

    public void setDetails(List<UserDetails> details) {
        this.details = details;
    }
    
    public int getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(int userPosition) {
        this.userPosition = userPosition;
    }
}
