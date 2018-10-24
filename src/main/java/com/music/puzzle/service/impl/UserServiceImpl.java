package com.music.puzzle.service.impl;

import com.music.puzzle.controller.response.UserInfo;
import com.music.puzzle.domain.Level;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.UserRepo;
import com.music.puzzle.service.MailService;
import com.music.puzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, MailService mailService) {
        this.userRepo = userRepo;
        this.mailService = mailService;
    }

    @Override
    public void create(User user) throws AppException {
        checkEmail(user.getEmail());
        checkUserName(user.getUserName());
        userRepo.save(user);
    }

    @Override
    public void login(User user) throws AppException {
        List<User> users;
        if (user.getEmail() != null) {
            users = userRepo.findByEmail(user.getEmail());
        } else {
            users = userRepo.findByUserName(user.getUserName());
        }
        if (users.isEmpty()) {
            throw new AppException("Username or password is wrong");
        }
        User dbUSer = users.get(0);
        if (!user.getPassword().equals(dbUSer.getPassword())) {
            throw new AppException("Username or password is wrong");
        }

    }

    @Override
    public void checkEmail(String email) throws AppException {
        if(!userRepo.findByEmail(email).isEmpty()) {
            throw new AppException("Email was already used");
        }
    }

    @Override
    public void checkUserName(String userName) throws AppException {
        if(!userRepo.findByUserName(userName).isEmpty()) {
            throw new AppException("UserName was already used");
        }

    }

    @Override
    public void checkPassword(String password, String confirmPassword) throws AppException {
        if (!password.equals(confirmPassword)) {
            throw new AppException("Passwords does not match");
        }
    }

    @Override
    public void addProfileInfo(UserInfo userInfo) throws AppException {
        String email = userInfo.getEmail();
        User dbUser = getUser(email);

        // check mandatory fields,
        // either in db or in request all fileds shoulb be present.
        if (dbUser.getName() == null || userInfo.getName() == null) {
            throw new AppException("Name is empty!");
        }

        if (dbUser.getSurName() == null || userInfo.getSurName() == null) {
            throw new AppException("Surname is empty!");
        }

        if (dbUser.getLocation() == null || userInfo.getLocation() == null) {
            throw new AppException("location is empty!");
        }
        // end of validation.

        dbUser.setName(userInfo.getName());
        dbUser.setSurName(userInfo.getSurName());
        dbUser.setLocation(userInfo.getLocation());

        userRepo.save(dbUser);
    }

    @Override
    public UserInfo getProfileInfo(String email) throws AppException {
        User user = getUser(email);
        UserInfo info = new UserInfo();
        info.setLocation(user.getLocation());
        info.setName(user.getName());
        info.setSurName(user.getSurName());

        return info;
    }

    @Override
    public User getUser(String email) throws AppException {
        List<User> userList = userRepo.findByEmail(email);
        if(userList.isEmpty()) {
            throw new AppException("Email does not exist");
        }
        return userList.get(0);
    }

    @Override
    public void sendCode(String email) throws AppException {
        User user = getUser(email);
        String code = "1111";
        mailService.sendEmail(email, "forgot password", "Your code is " + code);
        // update code in DB
        user.setRecoveryCode(code);
        userRepo.save(user);
    }

    @Override
    public void verifyCode(@NonNull String email, @NonNull String code) throws AppException {
        User user = getUser(email);
        if (!code.equals(user.getRecoveryCode())) {
            throw new AppException("Code is not correct");
        }
    }

    @Override
    public void changePassword(String email, String password) throws AppException {
        User user = getUser(email);
        user.setPassword(password);
        userRepo.save(user);
    }

    @Override
    public void addScore(String email, int score) throws AppException {
        User user = getUser(email);
        int newScore = user.getScore() + score;
        Level level = user.getLevel();

        // Check and update level if needed.
        if(nextLevel(level, newScore)) {
            user.setLevel(level.nextLevel());
        }
        user.setScore(newScore);

        userRepo.save(user);
    }

    private boolean nextLevel(Level level, int score) throws AppException {
        Level nextLevel = level.nextLevel();
        return score > nextLevel.getStartScore();
    }

}
