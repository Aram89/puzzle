package com.music.puzzle.service;

import com.music.puzzle.controller.response.RatingResponse;
import com.music.puzzle.controller.response.UserDetails;
import com.music.puzzle.domain.User;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.UserRepo;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RatingService {

    private final UserService userService;

    private final UserRepo userRepo;

    @Autowired
    public RatingService(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    public RatingResponse getGlobal(String email, int count, int offset) throws AppException {
        User user = userService.getUser(email);

        Iterable<User> users = userRepo.findAll();
        return get(user, users, count, offset);
    }

    public RatingResponse getLocal(String email, int count, int offset) throws AppException {
        User user = userService.getUser(email);

        Iterable<User> users = userRepo.findByLocation(user.getLocation());
        return get(user, users, count, offset);
    }

    private RatingResponse get(User user, Iterable<User> users, int count, int offset) {
        List<UserDetails> detailsList =
                StreamSupport.stream(users.spliterator(), false)
                        .sorted(Comparator.comparing(User::getScore))
                        .map(this::mapper)
                        .collect(Collectors.toList());

        List<UserDetails> leaders = detailsList.subList(0, min(count, detailsList.size() -1));
        int userPosition = (int) StreamEx.of(detailsList)
                .indexOf(u -> user.getUserName().equals(u.getUserName()))
                .getAsLong();

        if (userPosition < count) {
            // User is in leaders, return only leaders.
            return new RatingResponse(leaders, userPosition);
        }

        int start;
        int end;

        if (userPosition + offset > detailsList.size()) {
            start = detailsList.size() - 2 * offset;
            end = detailsList.size() - 1;
        } else {
            start = userPosition - offset;
            end = userPosition + offset;
        }
        List<UserDetails> neighbours = detailsList.subList(start, end);

        for(int i  = 0; i < neighbours.size(); ++i) {
            neighbours.get(i).setPosition(start + i);
        };

        ArrayList<UserDetails> all = new ArrayList<>(leaders);
        all.addAll(neighbours);

        return new RatingResponse(all, userPosition);
    }

    private UserDetails mapper(User user) {
        return new UserDetails(user.getUserName(), user.getLocation(), user.getScore());
    }

    private int min(int number1, int number2) {
        if (number1 > number2) {
            return number2;
        }
        return number1;
    }

}
