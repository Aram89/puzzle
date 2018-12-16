package com.music.puzzle.repository;

import com.music.puzzle.domain.Genre;
import com.music.puzzle.domain.MusicPuzzle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepo extends CrudRepository<MusicPuzzle, Long> {

    List<MusicPuzzle> findByLevelAndGenre(Integer level, Genre genre);
}
