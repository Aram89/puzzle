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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;

@Controller
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

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.create(user);
        //String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity<String> signIn(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.login(user);
        String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @RequestMapping(path = "/user/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<String> forgotPassword(@RequestBody @NonNull User user) throws AppException, UnsupportedEncodingException {
        userService.login(user);
        String jwt = generateJwt(user.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> test() throws AppException, UnsupportedEncodingException {
        userService.sendCode("aramkirakosyan89@gmail.com");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String generateJwt(String userName) throws UnsupportedEncodingException {
        String jwt = Jwts.builder()
                .setSubject(userName)
                .setPayload(userName)
                .claim("userName", userName)
                .signWith(
                        SignatureAlgorithm.HS256,
                        secret.getBytes("UTF-8")
                )
                .compact();
        return jwt;
    }

    @RequestMapping(path = "check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Server running", HttpStatus.OK);
    }

}
