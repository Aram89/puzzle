package com.music.puzzle.service;


import com.music.puzzle.controller.response.PuzzleResponse;

import java.io.IOException;

public interface MusicService {

    PuzzleResponse getMusic(String level) throws IOException;

}
