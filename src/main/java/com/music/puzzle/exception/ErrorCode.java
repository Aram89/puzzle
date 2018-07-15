package com.music.puzzle.exception;

/**
 * Created by Aram on 6/11/18.
 */
public class ErrorCode {
    public ErrorCode(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
