package com.music.puzzle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping(path = "/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Sax lav a", HttpStatus.OK);
    }

}
