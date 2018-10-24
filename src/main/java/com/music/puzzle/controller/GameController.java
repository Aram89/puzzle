package com.music.puzzle.controller;

import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.MusicService;
import com.music.puzzle.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api
@Controller
@RequestMapping("/game")
public class GameController {

    private final UserService userService;
    private final MusicService musicService;

    @Autowired
    public GameController(MusicService musicService, UserService userService) {
        this.musicService = musicService;
        this.userService = userService;
    }

    @GetMapping(path = "/win")
    public ResponseEntity<PuzzleResponse> getMusic(@RequestParam("email") String email,
                                                   @RequestParam("musicId") int musicId) throws AppException {
        int score = musicService.getScore(musicId);
        userService.addScore(email, score);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
