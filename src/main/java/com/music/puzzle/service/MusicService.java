package com.music.puzzle.service;

import com.music.puzzle.domain.*;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.MusicRepo;
import com.music.puzzle.repository.UserPuzzleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class MusicService {

    private final MusicRepo repo;
    private final UserPuzzleRepo userPuzzleRepo;

    @Autowired
    public MusicService(MusicRepo repo, UserPuzzleRepo userPuzzleRepo) {
        this.repo = repo;
        this.userPuzzleRepo = userPuzzleRepo;
    }

    public String getMusic(User user, Integer level, Genre genre) throws AppException {
        String type = getType(level);
        MusicPuzzle puzzle;

        // Get all puzzles for given level, which are not already played by user.
        List<MusicPuzzle> puzzleList = repo.getPuzzle(user.getId(), user.getLevel(), genre.getCode(), type);

        if (!puzzleList.isEmpty()) {
            puzzle = puzzleList.get(0);
            markPuzzleAsPlayed(puzzle, level, user);

            return getMusicPath(puzzle.getMusicId());

        } else {
            // TODO currently win logic was not implemented, if all puzzles are played, just return random puzzle.
            log.info("User  {} plays all puzzles for this level, get random not wined puzzle", user.getEmail());
            return getRandomPuzzle(level, genre);
        }

    }

    public String getRandomPuzzle(Integer level, Genre genre) throws AppException {
        List<MusicPuzzle> puzzleList = repo.findByTypeAndGenre(getType(level), genre);
        if(puzzleList.isEmpty()) {
            throw new AppException("No puzzles found for genre : " + genre + " and level : " + level);
        }

        int random = ThreadLocalRandom.current().nextInt(0, puzzleList.size());
        MusicPuzzle puzzle = puzzleList.get(random);

        return getMusicPath(puzzle.getMusicId());
    }

    public String getMusicPath(long musicId) throws AppException {
        return repo.findById(musicId)
                .map(MusicPuzzle::getPath)
                .orElseThrow(() -> new AppException("Wrong music id " + musicId));
    }

    public void save(Genre genre, String name) throws AppException {
        String path = "Music/" + genre + "/" + name;
        MusicPuzzle first = new MusicPuzzle();
        first.setGenre(genre);
        first.setType("min");
        first.setPath(path + "/" + name + "_min.mp3");

        MusicPuzzle second = new MusicPuzzle();
        second.setGenre(genre);
        second.setType("max");
        second.setPath(path + "/" + name + "_max.mp3");

        repo.save(first);
        repo.save(second);
    }

    public Iterable<MusicPuzzle> getAll() {
        return repo.findAll();
    }

    public MusicPuzzle getById(Long id) throws AppException {
        return repo.findById(id).orElseThrow(() -> new AppException("Music not found +" + id));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(puzzle -> repo.deleteById(id));
    }

    public List<MusicPuzzle> getByGenre(Genre genre) {
        return repo.findByGenre(genre);
    }

    private void markPuzzleAsPlayed(MusicPuzzle puzzle, Integer level, User user) {
        UserPuzzle userPuzzle = new UserPuzzle();
        userPuzzle.setLevel(level);
        userPuzzle.setPuzzle(puzzle);
        userPuzzle.setUser(user);
        userPuzzle.setStatus(PuzzleStatus.PLAYED);
        userPuzzleRepo.save(userPuzzle);
    }

    private String getType(Integer level) {
        if (level > 9) {
            return "max";
        } else {
            return "min";
        }
    }

}