package com.music.puzzle.controller;

import com.music.puzzle.controller.response.SignUpResponse;
import com.music.puzzle.controller.response.UserInfo;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.FileService;
import com.music.puzzle.service.UserService;
import com.music.puzzle.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @NonNull User user) throws AppException {
        log.info("Received request for SignUp {}", user);
        userService.create(user);
        String location = user.getLocation();
        String jwt = JwtHelper.generate(user.getEmail());
        SignUpResponse response = new SignUpResponse(jwt, location);

        log.info("User successfully registered in {}", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<SignUpResponse> signIn(@RequestBody @NonNull User user) throws AppException {
        log.info("Received request for SignIn {}", user);
        String location = userService.login(user);
        String jwt = JwtHelper.generate(user.getEmail());
        SignUpResponse response = new SignUpResponse(jwt, location);

        log.info("User successfully logged in {}", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) throws AppException {
        log.info("Received request for forgot password {}", email);
        userService.sendCode(email);
        String jwt = JwtHelper.generate(email);

        log.info("Email with recovery code was sent {}", email);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @GetMapping(path = "/verify-code")
    public ResponseEntity<String> verifyCode(@RequestParam("email") String email,
                                             @RequestParam("code") int code) throws AppException {
        log.info("Received request for code verification {},{}", email, code);
        userService.verifyCode(email, code);
        String jwt = JwtHelper.generate(email);

        log.info("Code verified {}, {}", email, code);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @NonNull User user) throws AppException {
        log.info("Received request for changing password {}", user);
        userService.changePassword(user.getEmail(), user.getPassword());

        log.info("User's password has been changed {}", user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<UserInfo> getProfileData(@RequestParam @NonNull String email) throws AppException {
        log.info("Received request for getting info {}", email);
        UserInfo info = userService.getProfileInfo(email);

        log.info("Get info request successfully processed {}", info);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PostMapping(path = "/avatar")
    public ResponseEntity<String> saveAvatar(@RequestParam("email") String email, MultipartFile file) throws AppException {
        User user = userService.getUser(email);
        fileService.storeFile(user.getEmail(), file);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/avatar")
    public ResponseEntity<Resource> getAvatar(@RequestParam("email") String email) throws AppException {
        User user = userService.getUser(email);
        Resource resource = fileService.loadFile(user.getEmail());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(path = "/count")
    public ResponseEntity getCount() throws AppException {
        Long count = userService.getCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
