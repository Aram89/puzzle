package com.music.puzzle.controller;

import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    @Autowired
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping(path = "/puzzle")
    public ResponseEntity<PuzzleResponse> getMusic(@RequestParam("level") String level) throws AppException {
        PuzzleResponse response = musicService.getMusic(level);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/save")
    public ResponseEntity<String> save(@RequestParam("path") String path) {
        musicService.save(path);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

}
