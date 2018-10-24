package com.music.puzzle.controller;


import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.FileService;
import com.music.puzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final UserService userService;

    private final FileService fileService;

    @Autowired
    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping(path = "/user/avatar")
    public ResponseEntity<String> saveAvatar(@RequestParam("email") String email, MultipartFile file) throws AppException {
        User user = userService.getUser(email);
        fileService.storeFile(user.getNickName(), file);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path = "/user/avatar")
    public ResponseEntity<Resource> getAvatar(@RequestParam("email") String email) throws AppException {
        User user = userService.getUser(email);

        // Load file as Resource
        Resource resource = fileService.loadFile(user.getEmail());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
