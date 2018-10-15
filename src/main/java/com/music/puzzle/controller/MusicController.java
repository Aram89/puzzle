package com.music.puzzle.controller;

import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    @Autowired
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @RequestMapping(path = "/puzzle", method = RequestMethod.GET)
    public ResponseEntity<PuzzleResponse> getMusic(@RequestParam("level") String level) throws IOException {
        PuzzleResponse response = musicService.getMusic(level);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/save")
    public ResponseEntity<String> healthCheck() {
        musicService.save();
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

}
