package com.music.puzzle.controller.response;

public class PieceResponse {

    private byte[] data;
    private int correctPosition;

    public PieceResponse(byte[] bytes, int correctPosition) {
        this.data = bytes;
        this.correctPosition = correctPosition;
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getCorrectPosition() {
        return correctPosition;
    }

    public void setCorrectPosition(int correctPosition) {
        this.correctPosition = correctPosition;
    }

}
