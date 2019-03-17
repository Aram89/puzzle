package com.music.puzzle.controller;

import com.music.puzzle.domain.Genre;
import com.music.puzzle.domain.MusicPuzzle;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.MusicService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api
@Controller
@RequestMapping("/puzzle")
public class PuzzleController {

    private final MusicService musicService;

    @Autowired
    public PuzzleController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity getAll() throws AppException {
        Iterable all = musicService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping(path = "/genre")
    public ResponseEntity getByGenre(@RequestParam("genre") Genre genre) throws AppException {
        Iterable all = musicService.getByGenre(genre);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getById(@RequestParam("id") Long id) throws AppException {
        MusicPuzzle puzzle = musicService.getById(id);
        return new ResponseEntity<>(puzzle, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteById(@RequestParam("id") Long id) throws AppException {
        musicService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
