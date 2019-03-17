package com.music.puzzle.repository;

import com.music.puzzle.domain.Genre;
import com.music.puzzle.domain.MusicPuzzle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepo extends CrudRepository<MusicPuzzle, Long> {

    List<MusicPuzzle> findByTypeAndGenre(String type, Genre genre);
    List<MusicPuzzle> findByGenre(Genre genre);

    @Query(
            value = "select * from music_puzzle\n" +
                    "where genre = ?3 AND type = ?4 AND music_puzzle.music_id NOT IN (select music_id from user_puzzle" +
                    " where user_id = ?1 AND level = ?2)\n" +
                    "ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true)
    List<MusicPuzzle> getPuzzle(Long userId, Integer level, Integer genre, String type);

    @Query(
            value = "select * from music_puzzle\n" +
                    "where genre = ?3 AND type = ?4 AND music_puzzle.music_id IN (select music_id from user_puzzle" +
                    " where user_id = ?1 AND level = ?2 AND status = 'PLAYED')\n" +
                    "ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true)
    List<MusicPuzzle> getEldestPlayedPuzzle(Long userId, Integer level, Integer genre, String type);
}
