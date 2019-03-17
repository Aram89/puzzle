package com.music.puzzle.repository;

import com.music.puzzle.domain.UserPuzzle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPuzzleRepo extends CrudRepository <UserPuzzle, Long> {

    @Modifying
    @Query(value = "update user_puzzle set status = ?3 where user_id = ?1 " +
            "and music_id = ?2", nativeQuery = true)
    void updateStatus(Long userId, Long musicId, String status);
}
