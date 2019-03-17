package com.music.puzzle.controller;

import com.music.puzzle.controller.response.WinResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

@Api
@Controller
@RequestMapping("/game")
public class GameController {

    private final UserService userService;

    @Autowired
    public GameController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/win")
    public ResponseEntity<WinResponse> win(@RequestParam("email") String email,
                                                   @RequestParam("hint") boolean hint) throws AppException {
        WinResponse response = userService.addScore(email, hint);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
