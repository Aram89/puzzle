package com.music.puzzle.service;

import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;


public interface UserService {

    void create(User user) throws AppException;
    void login(User user) throws AppException;
    void checkEmail(String email) throws AppException;
    void checkUserName(String userName) throws AppException;
    void checkPassword(String password, String confirmPassword) throws AppException;
    void addProfileInfo(User user) throws AppException;
    void sendCode(String email) throws AppException;
    void verifyCode(String email, String code) throws AppException;

}
