package com.music.puzzle.domain;


public enum Genre {

    Rock(0),
    Rap(1),
    Funk(2),
    Electronic(3),
    Country(4),
    Classic(5),
    Jazz(6),
    Pop(7),
    Blues(8),
    Hip_Hop(9);

    int code;

    Genre(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
