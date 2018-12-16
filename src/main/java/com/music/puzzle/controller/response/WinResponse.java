package com.music.puzzle.controller.response;

public class WinResponse {

    private boolean levelChanged = false;
    private int level;
    private int score;

    public WinResponse() {
    }

    public boolean isLevelChanged() {
        return levelChanged;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevelChanged(boolean levelChanged) {
        this.levelChanged = levelChanged;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
