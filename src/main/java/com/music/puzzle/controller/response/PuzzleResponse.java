package com.music.puzzle.controller.response;

import java.util.ArrayList;
import java.util.List;

public class PuzzleResponse {

    private List<PieceResponse> pieceList;
    private String name;

    public PuzzleResponse(String name) {
        this.name = name;
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
