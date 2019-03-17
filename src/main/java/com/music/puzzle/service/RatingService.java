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

    public RatingResponse getGlobal(String email) throws AppException {
        User user = userService.getUser(email);

        Iterable<User> users = userRepo.findAll();
        return get(user, users);
    }

    public RatingResponse getLocal(String email) throws AppException {
        User user = userService.getUser(email);

        Iterable<User> users = userRepo.findByLocation(user.getLocation());
        return get(user, users);
    }

    private RatingResponse get(User user, Iterable<User> users) {
        /* Load all users sorted by score and converted to user details */
        Comparator<User> scoreComperator
                = Comparator.comparing(User::getScore);

        List<UserDetails> detailsList =
                StreamSupport.stream(users.spliterator(), false)
                        .sorted(scoreComperator.reversed())
                        .map(this::mapper)
                        .collect(Collectors.toList());

        /* Get top ten users */
        List<UserDetails> leaders = new ArrayList<>();

        /* Get current user position */
        int userPosition = (int) StreamEx.of(detailsList)
                .indexOf(u -> user.getUserName().equals(u.getUserName()))
                .getAsLong();

        int leadersCount = 10;
        if(detailsList.size() < 10) {
            leadersCount = detailsList.size();
        }

        for(int i = 0; i < leadersCount; ++i) {
            leaders.add(userDetail(detailsList.get(i).getUserName(), detailsList.get(i).getLocation(),
                    detailsList.get(i).getScore(), i +1));
        }

        if(userPosition <= 10) {
            return new RatingResponse(leaders, userPosition);
        }

        int allUsersCount = detailsList.size();

        int start;
        int end;
        List<UserDetails> neighbours = new ArrayList<>();

        if (userPosition >= detailsList.size() -10) {
            // User is in worst ten.
            if(userPosition - 5 <= 10) {
                start = 10;
            } else {
                start = allUsersCount - 10;
            }
            end = allUsersCount - 1;
        } else {
            if(userPosition - 5 <= 10) {
                start = 10;
            } else {
                start = userPosition - 5;

            }
            end = userPosition + 5;
        }

        for(int i = start; i < end; ++i) {
            neighbours.add(userDetail(detailsList.get(i).getUserName(), detailsList.get(i).getLocation(),
                    detailsList.get(i).getScore(), i+1));
        }

        ArrayList<UserDetails> all = new ArrayList<>(leaders);
        all.addAll(neighbours);

        return new RatingResponse(all, userPosition);
    }

    private UserDetails mapper(User user) {
        return new UserDetails(user.getUserName(), user.getLocation(), user.getScore());
    }

    private UserDetails userDetail(String name, String location, int score, int position) {
        UserDetails details = new UserDetails(name, location,
                score);
        details.setPosition(position);
        return details;
    }

}
