package com.music.puzzle.controller;

import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.domain.Genre;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.FileService;
import com.music.puzzle.service.MusicService;
import com.music.puzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public MusicController(MusicService musicService, FileService fileService, UserService userService) {
        this.musicService = musicService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping(path = "/puzzle")
    public ResponseEntity<PuzzleResponse> getPuzzle(@RequestParam("email") String email, @RequestParam("genre")Genre genre) throws AppException {
        User user = userService.getUser(email);
        PuzzleResponse response = musicService.getMusic(user.getLevel(), genre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/guest/puzzle")
    public ResponseEntity<PuzzleResponse> getPuzzleGuest(@RequestParam("genre")Genre genre) throws AppException {
        PuzzleResponse response = musicService.getMusic(1, genre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Resource> getMusic(@RequestParam("id") long id) throws AppException {
        String path = musicService.getMusicPath(id);
        Resource music = fileService.loadFile(path);
        return new ResponseEntity<>(music, HttpStatus.OK);
    }

    @GetMapping(path = "/save")
    public ResponseEntity<String> save(@RequestParam("genre") Genre genre,
                                             @RequestParam("level") int level,
                                             @RequestParam("name") String name
    ) throws AppException {
        List paths = musicService.save(genre, level, name);
        String result = "saved music " + paths.get(0) + " with " + paths.size() + " pieces : " + paths.get(1);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
