package com.music.puzzle.controller.response;

public class PieceResponse {

    private byte[] data;
    private int correctPosition;
    private int viewPosition;
    private long puzzleId;

    public PieceResponse(byte[] data, int correctPosition, long puzzleId) {
        this.data = data;
        this.correctPosition = correctPosition;
        this.puzzleId = puzzleId;
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

    public int getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }

    public long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(long puzzleId) {
        this.puzzleId = puzzleId;
    }
}
