package com.music.puzzle.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.music.puzzle.authorization.SecurityConstants.EXPIRATION_TIME;
import static com.music.puzzle.authorization.SecurityConstants.SECRET;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * JWT secret key, value injected from auth0.properties file.
     */
    @Value("${auth0.clientSecret}")
    private String secret;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.create(user);
        String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @RequestMapping(path = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<String> signIn(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.login(user);
        String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @RequestMapping(path = "/forgot-password", method = RequestMethod.GET)
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) throws AppException, UnsupportedEncodingException {
        userService.sendCode(email);
        String jwt = generateJwt(email);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @RequestMapping(path = "/verify-code", method = RequestMethod.GET)
    public ResponseEntity<String> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) throws AppException, UnsupportedEncodingException {
        userService.verifyCode(email, code);
        String jwt = generateJwt(email);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @RequestMapping(path = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.changePassword(user.getUserName(), user.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/add-profile-info", method = RequestMethod.POST)
    public ResponseEntity<String> addProfileData(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.addProfileInfo(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/get-profile-info", method = RequestMethod.GET)
    public ResponseEntity<User> getProfileData(@RequestParam @NonNull String userName) throws AppException, UnsupportedEncodingException {
        User user = userService.getProfileInfo(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private String generateJwt(String email) throws UnsupportedEncodingException {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

}
