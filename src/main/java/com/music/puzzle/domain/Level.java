package com.music.puzzle.domain;


import com.music.puzzle.exception.AppException;

public enum Level {

    /* start score, endScore, scorePerWin, pieceCount, number */
    ENTRY1(0, 49, 10, 3, 1),
    ENTRY2(50, 149, 20, 4, 2),
    ENTRY3(150, 299, 30, 5, 3),
    ENTRY4(300, 499, 40, 6, 4),
    ENTRY5(500, 749, 50, 7, 5),
    ENTRY6(750, 1049, 60, 8, 6),

    BASIC1(1050, 1399, 70, 9, 7),
    BASIC2(1400, 1799, 80, 10, 8),
    BASIC3(1800, 2249, 90, 11, 9),
    BASIC4(2250, 2749, 100, 12, 10),
    BASIC5(2750, 3299, 110, 13, 11),
    BASIC6(3300, 3899, 120, 14, 12),

    MASTER1(3900, 4549, 130, 15, 13),
    MASTER2(4550, 5249, 140, 16, 14),
    MASTER3(5250, 5999, 150, 17, 15),
    MASTER4(6000, 6799, 160, 18, 16),
    MASTER5(6800, 7649, 170, 19, 17),
    MASTER6(7650, 8549, 180, 20, 18);

    private int startScore;
    private int endScore;
    private int scorePerWin;
    private int pieceCount;
    private int number;

    Level(int startScore, int endScore, int scorePerWin, int pieceCount, int number) {
        this.startScore = startScore;
        this.endScore = endScore;
        this.scorePerWin = scorePerWin;
        this.pieceCount = pieceCount;
        this.number = number;
    }

    public int getStartScore() {
        return startScore;
    }

    public int getEndScore() {
        return endScore;
    }

    public int getScorePerWin(boolean hint) {
        if(hint) {
            return scorePerWin/2;
        }
        return scorePerWin;
    }

    public int getScorePerWin() {
        return scorePerWin;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public int getNumber() {
        return number;
    }

    public static Level get(int number) throws AppException {
        switch (number) {
            case 1 : return ENTRY1;
            case 2 : return ENTRY2;
            case 3 : return ENTRY3;
            case 4 : return ENTRY4;
            case 5 : return ENTRY5;
            case 6 : return ENTRY6;
            case 7 : return BASIC1;
            case 8 : return BASIC2;
            case 9 : return BASIC3;
            case 10 : return BASIC4;
            case 11 : return BASIC5;
            case 12 : return BASIC6;
            case 13 : return MASTER1;
            case 14 : return MASTER2;
            case 15 : return MASTER3;
            case 16 : return MASTER4;
            case 17 : return MASTER5;
            case 18 : return MASTER6;

            default : throw new AppException("wrong level, level should be < 18");
        }
    }

    public Level nextLevel() throws AppException {
        switch (this) {
            case ENTRY1:
                return ENTRY2;
            case ENTRY2:
                return ENTRY3;
            case ENTRY3:
                return ENTRY4;
            case ENTRY4:
                return ENTRY5;
            case ENTRY5:
                return ENTRY6;
            case ENTRY6:
                return BASIC1;
            case BASIC1:
                return BASIC2;
            case BASIC2:
                return BASIC3;
            case BASIC3:
                return BASIC4;
            case BASIC4:
                return BASIC5;
            case BASIC5:
                return BASIC6;
            case BASIC6:
                return MASTER1;
            case MASTER1:
                return MASTER2;
            case MASTER2:
                return MASTER3;
            case MASTER3:
                return MASTER4;
            case MASTER4:
                return MASTER5;
            case MASTER5:
                return MASTER6;

            case MASTER6:
                throw new AppException("It's last level, End of Game");

            default:return ENTRY1;
        }
    }
}
