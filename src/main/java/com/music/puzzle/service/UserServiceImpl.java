package com.music.puzzle.service;

import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements  UserService{

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
    public void addProfileInfo(User user) throws AppException {
        User dbUser = new User();
        dbUser.setPhone(user.getPhone());
        dbUser.setName(user.getName());
        dbUser.setSurName(user.getSurName());
        userRepo.save(dbUser);
    }

    @Override
    public User getProfileInfo(String userName) throws AppException {
        List<User> users = userRepo.findByUserName(userName);
        if(users.isEmpty()) {
            throw new AppException("user does not exist");
        }
        return users.get(0);
    }

    @Override
    public void sendCode(String email) throws AppException {
        List<User> userList = userRepo.findByEmail(email);
        if(userList.isEmpty()) {
            throw new AppException("Email does not exist");
        }
        String code = "1111";
        mailService.sendEmail(email, "forgot password", "Your code is " + code);
        // update code in DB
        User user = userList.get(0);
        user.setRecoveryCode(code);
        userRepo.save(user);
    }

    @Override
    public void verifyCode(@NonNull String email, @NonNull String code) throws AppException {
        List<User> user = userRepo.findByEmail(email);
        if(user.isEmpty()) {
            throw new AppException("Email does not exist");
        }
        if (!code.equals(user.get(0).getRecoveryCode())) {
            throw new AppException("Code is not correct");
        }
    }

    @Override
    public void changePassword(String userName, String password) throws AppException {
        List<User> users = userRepo.findByUserName(userName);
        if(users.isEmpty()) {
            throw new AppException("user does not exist");
        }
        User user = users.get(0);
        user.setPassword(password);
        userRepo.save(user);
    }



}
