package com.music.puzzle.controller;

import com.music.puzzle.domain.Genre;
import com.music.puzzle.domain.MusicPuzzle;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.FileService;
import com.music.puzzle.service.MusicService;
import com.music.puzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Resource> getPuzzle(@RequestParam("email") String email, @RequestParam("genre") Genre genre) throws AppException {
        User user = userService.getUser(email);
        String path = musicService.getMusic(user, user.getLevel(), genre);
        Resource music = fileService.loadFile(path);

        return new ResponseEntity<>(music, HttpStatus.OK);
    }

    @GetMapping(path = "/guest/puzzle")
    public ResponseEntity<Resource> getPuzzleGuest(@RequestParam("genre") Genre genre) throws AppException {
        String path = musicService.getRandomPuzzle(1, genre);
        Resource music = fileService.loadFile(path);
        return new ResponseEntity<>(music, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Resource> getMusic(@RequestParam("id") long id) throws AppException {
        String path = musicService.getMusicPath(id);
        Resource music = fileService.loadFile(path);
        return new ResponseEntity<>(music, HttpStatus.OK);
    }

    @GetMapping(path = "/save")
    public ResponseEntity save(@RequestParam("genre") Genre genre,
                               @RequestParam("name") String name) throws AppException {
        musicService.save(genre, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
