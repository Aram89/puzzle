package com.music.puzzle.service;

import com.music.puzzle.controller.response.PieceResponse;
import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.domain.Genre;
import com.music.puzzle.domain.Level;
import com.music.puzzle.domain.MusicPiece;
import com.music.puzzle.domain.MusicPuzzle;
import com.music.puzzle.exception.AppException;
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
public class MusicService {

    private final MusicRepo repo;


    @Autowired
    public MusicService(MusicRepo repo) {
        this.repo = repo;
    }

    public PuzzleResponse getMusic(Integer level, Genre genre) throws AppException {

        // Get all puzzles for given level.
        List<MusicPuzzle> puzzleList = repo.findByLevelAndGenre(level, genre);

        if (puzzleList == null || puzzleList.isEmpty()) {
            throw new AppException("There are no puzzles");
        }

        // TODO change with more relevant for user.
        int randomNum = ThreadLocalRandom.current().nextInt(0, puzzleList.size());
        MusicPuzzle puzzle = puzzleList.get(randomNum);

        List<MusicPiece> pieces = puzzle.getPieces();
        PuzzleResponse response = new PuzzleResponse(puzzle.getName(), puzzle.getMusicId());

        for(MusicPiece piece : pieces) {
            response.addPiece(convert(piece));
        }

        List<PieceResponse> pieceList = response.getPieceList();

        List<PieceResponse> sorted = pieceList.stream()
                .sorted(Comparator.comparing(PieceResponse::getCorrectPosition))
                .collect(Collectors.toList());

        Collections.shuffle(sorted);

        response.setPieceList(sorted);
        return response;
    }

    public int getScore(long musicId, boolean hint) throws AppException {
        return repo.findById(musicId)
                .map(musicPuzzle -> {
                    try {
                        return Level.get(musicPuzzle.getLevel()).getScorePerWin(hint);
                    } catch (AppException e) {
                        // TODO change it.
                        e.printStackTrace();
                        return 0;
                    }
                })
                .orElseThrow(() -> new AppException("Wrong music id +" + musicId));
    }

    public String getMusicPath(long musicId) throws AppException {
        return repo.findById(musicId)
                .map(MusicPuzzle::getPath)
                .orElseThrow(() -> new AppException("Wrong music id " + musicId));
    }


    private PieceResponse convert(MusicPiece piece) throws AppException {
        int correctPosition = piece.getPosition();
        byte[] data = readBytes(piece.getPath());
        return new PieceResponse(data, correctPosition);
    }

    private byte[] readBytes(String path) throws AppException {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new AppException("music not found wit path : " + path);
        }

    }

    public List<String> save(Genre genre, int level, String name) throws AppException {
        MusicPuzzle puzzle = new MusicPuzzle();

        List paths = parsePath(name, genre,level);

        List<MusicPiece> pieces = new ArrayList<>();
        for (int i = 1; i <= Level.get(level).getPieceCount(); ++i) {
            MusicPiece piece = new MusicPiece();
            piece.setPath((String) paths.get(i));
            piece.setMusicPuzzle(puzzle);
            piece.setPosition(i - 1);
            pieces.add(piece);
        }

        puzzle.setPieces(pieces);
        puzzle.setLevel(level);
        puzzle.setGenre(genre);
        puzzle.setPath((String) paths.get(0));

        repo.save(puzzle);

        return paths;

    }

    /**
     * Retruns list of parsed paths.
     * 0 element is main music, then pieces.
     */
    private List parsePath(String name, Genre genre, int level) throws AppException {
        List<String> paths = new ArrayList<>();
        // Music / Genre / MusicNameLevel / MusicNameLevel.mp3 , MusicNameLevel_piceNumber.mp3, .
        String fullPath = "Music/" + genre.toString() + "/" + name  + level + "/" + name + level;
        paths.add(fullPath + ".mp3");
        for (int i = 1; i <+ Level.get(level).getPieceCount(); ++i) {
            paths.add(fullPath + "_" + i + ".mp3");
        }
        return paths;
    }

}