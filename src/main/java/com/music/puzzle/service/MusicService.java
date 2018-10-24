package com.music.puzzle.service;


import com.music.puzzle.controller.response.PuzzleResponse;
import com.music.puzzle.exception.AppException;

import java.io.IOException;

public interface MusicService {

    PuzzleResponse getMusic(String level) throws AppException;
    int getScore(long musicId) throws AppException;

    void save(String path);


}
