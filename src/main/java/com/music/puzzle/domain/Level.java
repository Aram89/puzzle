package com.music.puzzle.domain;


import com.music.puzzle.exception.AppException;

public enum Level {

    ENTRY(0, 49, 10),
    BASIC(50, 149, 20),
    MASTER(150, 299, 30);

    private int startScore;
    private int endScore;
    private int scorePerWin;

    Level(int startScore, int endScore, int scorePerWin) {
        this.startScore = startScore;
        this.endScore = endScore;
        this.scorePerWin = scorePerWin;
    }

    public int getStartScore() {
        return startScore;
    }

    public int getEndScore() {
        return endScore;
    }

    public int getScorePerWin() {
        return scorePerWin;
    }

    public Level nextLevel() throws AppException {
        switch (this) {
            case ENTRY:
                return BASIC;
            case BASIC:
                return MASTER;
            case MASTER:
                throw new AppException("It's last level, End of Game");

            default:return ENTRY;
        }
    }
}
