package com.music.puzzle.controller.response;

import java.util.ArrayList;
import java.util.List;

public class PuzzleResponse {

    private List<PieceResponse> pieceList;
    private String name;
    private long musicId;

    public PuzzleResponse(String name, long musicId) {
        this.name = name;
        this.musicId = musicId;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public List<PieceResponse> getPieceList() {
        return pieceList;
    }

    public void setPieceList(List<PieceResponse> pieceList) {
        this.pieceList = pieceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPiece(PieceResponse pieceResponse) {
        if(pieceList == null) {
            pieceList = new ArrayList<>();
        }
        pieceList.add(pieceResponse);
    }
}
