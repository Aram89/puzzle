package com.music.puzzle.service;

import com.music.puzzle.controller.response.PieceResponse;
import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.domain.MusicPiece;
import com.music.puzzle.domain.MusicPuzzle;
import com.music.puzzle.repository.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class MusicServiceImpl implements MusicService {

    private final MusicRepo repo;

    @Autowired
    public MusicServiceImpl(MusicRepo repo) {
        this.repo = repo;
    }

    @Override
    public PuzzleResponse getMusic(String level) throws IOException {
        // Get all puzzles for given level.
        List<MusicPuzzle> puzzleList = repo.findByLevel(level);

        // TODO change with more relevant for user.
        int randomNum = ThreadLocalRandom.current().nextInt(0, puzzleList.size());

        MusicPuzzle puzzle = puzzleList.get(randomNum);

        List<MusicPiece> pieces = puzzle.getPieces();
        PuzzleResponse response = new PuzzleResponse(puzzle.getName());

        for(MusicPiece piece : pieces) {
            response.addPiece(convert(piece));
        }

        List<PieceResponse> pieceList = response.getPieceList();

        List<PieceResponse> sorted = pieceList.stream()
                .sorted(Comparator.comparing(PieceResponse::getCorrectPosition))
                .collect(Collectors.toList());

        Collections.shuffle(sorted);

        for(int i = 0; i < pieceList.size(); i++) {
            sorted.get(0).setViewPosition(i);
        }

        response.setPieceList(sorted);
        return response;
    }

    private PieceResponse convert(MusicPiece piece) throws IOException {
        byte[] data = readBytes(piece.getPath());
        int order = piece.getPosition();
        return new PieceResponse(data, order, piece.getMusicPuzzle().getMusicId());
    }

    private byte[] readBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

}