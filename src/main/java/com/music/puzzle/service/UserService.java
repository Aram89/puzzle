package com.music.puzzle.service;

import com.music.puzzle.controller.response.UserInfo;
import com.music.puzzle.controller.response.WinResponse;
import com.music.puzzle.domain.Level;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;

    private final MailService mailService;

    @Autowired
    public UserService(UserRepo userRepo, MailService mailService) {
        this.userRepo = userRepo;
        this.mailService = mailService;
    }

    public void create(User user) throws AppException {
        if (user.getEmail() == null) {
            throw new AppException("Email is empty :" + user);
        }
        if (user.getUserName() == null) {
            throw new AppException("Username is empty :" + user);
        }
        checkEmail(user.getEmail());
        checkUserName(user.getUserName());

        // Entry level
        user.setLevel(1);
        user.setScore(0);

        userRepo.save(user);
    }

    public String login(User user) throws AppException {
        List<User> users;
        if (user.getEmail() == null) {
            throw new AppException("email is empty :" + user);
        }
        users = userRepo.findByEmail(user.getEmail());

        if (users.isEmpty()) {
            throw new AppException("email or password is wrong :" + user);
        }
        User dbUSer = users.get(0);
        if (!user.getPassword().equals(dbUSer.getPassword())) {
            throw new AppException("email or password is wrong : " + user);
        }
        return dbUSer.getLocation();

    }

    private void checkEmail(String email) throws AppException {
        if (!userRepo.findByEmail(email).isEmpty()) {
            throw new AppException("Email was already used " + email);
        }
    }

    private void checkUserName(String userName) throws AppException {
        if (!userRepo.findByUserName(userName).isEmpty()) {
            throw new AppException("UserName was already used" + userName);
        }
    }

    public UserInfo getProfileInfo(String email) throws AppException {
        User user = getUser(email);
        UserInfo info = new UserInfo();
        info.setLocation(user.getLocation());
        info.setUserName(user.getUserName());
        info.setEmail(email);
        info.setScore(user.getScore());
        info.setLevel(user.getLevel());

        return info;
    }

    public User getUser(String email) throws AppException {
        List<User> userList = userRepo.findByEmail(email);
        if (userList.isEmpty()) {
            throw new AppException("Email does not exist : " + email);
        }
        return userList.get(0);
    }

    public void sendCode(String email) throws AppException {
        User user = getUser(email);
        int code = generateCode();
        mailService.sendEmail(email, "forgot password", "Your code is " + code);
        // update code in DB
        user.setRecoveryCode(code);
        userRepo.save(user);
    }

    public void verifyCode(@NonNull String email, @NonNull int code) throws AppException {
        User user = getUser(email);
        if (code != user.getRecoveryCode()) {
            throw new AppException("Code is not correct :" + code);
        }
    }

    public void changePassword(String email, String password) throws AppException {
        User user = getUser(email);
        user.setPassword(password);
        userRepo.save(user);
    }

    public WinResponse addScore(String email, boolean hint) throws AppException {
        User user = getUser(email);
        Level level = Level.get(user.getLevel());
        int score = level.getScorePerWin(hint);

        int newScore = user.getScore() + score;

        WinResponse response = new WinResponse();
        // Check and update level if needed.
        if (nextLevel(level, newScore)) {
            user.setLevel(level.nextLevel().getNumber());
            response.setLevelChanged(true);
            response.setLevel(level.nextLevel().getNumber());
            log.info("Users level has been changed {}", email);
        } else {
            response.setLevel(user.getLevel());
        }
        response.setScore(newScore);

        user.setScore(newScore);

        //userPuzzleRepo.updateStatus(user.getId(), musicId, PuzzleStatus.WIN.name());

        userRepo.save(user);

        return response;
    }

    public Long getCount() {
        return userRepo.count();
    }

    private boolean nextLevel(Level level, int score) throws AppException {
        Level nextLevel = level.nextLevel();
        return score > nextLevel.getStartScore();
    }

    private int generateCode() {
        Random rnd = new Random();
        return 1000 + rnd.nextInt(9000);
    }

}
