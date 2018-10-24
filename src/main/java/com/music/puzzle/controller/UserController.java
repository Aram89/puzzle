package com.music.puzzle.controller;

import com.music.puzzle.controller.response.UserInfo;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.music.puzzle.authorization.SecurityConstants.EXPIRATION_TIME;
import static com.music.puzzle.authorization.SecurityConstants.SECRET;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    /**
     * JWT secret key, value injected from auth0.properties file.
     */
    @Value("${auth0.clientSecret}")
    private String secret;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @NonNull User user) throws AppException {
        userService.create(user);
        String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<String> signIn(@RequestBody @NonNull User user) throws AppException {
        userService.login(user);
        String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) throws AppException {
        userService.sendCode(email);
        String jwt = generateJwt(email);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @GetMapping(path = "/verify-code")
    public ResponseEntity<String> verifyCode(@RequestParam("email") String email,
                                             @RequestParam("code") String code) throws AppException {
        userService.verifyCode(email, code);
        String jwt = generateJwt(email);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @NonNull User user) throws AppException {
        userService.changePassword(user.getUserName(), user.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/info")
    public ResponseEntity<String> addProfileData(@RequestBody @NonNull UserInfo info) throws AppException,
            UnsupportedEncodingException {
        userService.addProfileInfo(info);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<UserInfo> getProfileData(@RequestParam @NonNull String email) throws AppException {
        UserInfo info = userService.getProfileInfo(email);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    private String generateJwt(String email)  {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

}
