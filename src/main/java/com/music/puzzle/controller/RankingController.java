package com.music.puzzle.controller;

import com.music.puzzle.controller.response.RatingResponse;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("rating")
public class RankingController {

    private final RatingService ratingService;

    @Autowired
    public RankingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(path = "/global")
    public ResponseEntity<RatingResponse> getGlobal(@RequestParam("email") @NonNull String email) throws AppException {
        RatingResponse ratingResponse = ratingService.getGlobal(email);

        return new ResponseEntity<>(ratingResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/local")
    public ResponseEntity<RatingResponse> getLocal(@RequestParam("email") @NonNull String email) throws AppException,
            UnsupportedEncodingException {
        RatingResponse ratingResponse = ratingService.getLocal(email);

        return new ResponseEntity<>(ratingResponse, HttpStatus.OK);
    }
}
