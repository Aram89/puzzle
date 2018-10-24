package com.music.puzzle.service.impl;

import com.music.puzzle.controller.response.PieceResponse;
import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.domain.Level;
import com.music.puzzle.domain.MusicPiece;
import com.music.puzzle.domain.MusicPuzzle;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.MusicRepo;
import com.music.puzzle.service.FileService;
import com.music.puzzle.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class MusicServiceImpl implements MusicService {

    private final MusicRepo repo;

    private final FileService fileService;

    @Autowired
    public MusicServiceImpl(MusicRepo repo, FileService fileService) {
        this.repo = repo;
        this.fileService = fileService;
    }

    @Override
    public PuzzleResponse getMusic(String level) throws AppException {
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

    @Override
    public int getScore(long musicId) throws AppException {
        return repo.findById(musicId)
                .map(musicPuzzle -> musicPuzzle.getLevel().getScorePerWin())
                .orElseThrow(() -> new AppException("Wrong music id +" + musicId));
    }


    private PieceResponse convert(MusicPiece piece) throws AppException {
        byte[] data = readBytes(piece.getPath());
        int order = piece.getPosition();
        Resource resource = fileService.loadFile(piece.getPath());
        return new PieceResponse(resource, order, piece.getMusicPuzzle().getMusicId());
    }

    private byte[] readBytes(String path) throws AppException {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new AppException("music not found wit path : " + path);
        }

    }


    @Override
    public void save(String path) {
        MusicPuzzle puzzle = new MusicPuzzle();

        MusicPiece piece = new MusicPiece();

        piece.setPosition(1);
        piece.setPath(path);
        piece.setMusicPuzzle(puzzle);

        MusicPiece piece1 = new MusicPiece();

        piece1.setPosition(2);
        piece1.setPath(path);
        piece1.setMusicPuzzle(puzzle);

        MusicPiece piece2 = new MusicPiece();
        piece2.setPosition(3);
        piece2.setPath(path);
        piece2.setMusicPuzzle(puzzle);

        List<MusicPiece> pieces = new ArrayList<>();
        pieces.add(piece);
        pieces.add(piece1);
        pieces.add(piece2);

        puzzle.setPieces(pieces);
        puzzle.setLevel(Level.ENTRY);
        puzzle.setPath(path);

        repo.save(puzzle);
    }

}