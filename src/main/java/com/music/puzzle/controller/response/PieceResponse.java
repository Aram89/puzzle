package com.music.puzzle.controller.response;

import org.springframework.core.io.Resource;

public class PieceResponse {

    private byte[] data;
    private int correctPosition;
    private int viewPosition;
    private long puzzleId;
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public PieceResponse(Resource resource, int correctPosition, long puzzleId) {
        this.resource = resource;
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
