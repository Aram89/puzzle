package com.music.puzzle.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "music_puzzle")
@Data
@NoArgsConstructor
public class MusicPuzzle {

    @Id
    @GeneratedValue
    private long musicId;

    @Column
    private String name;

    @Column
    private String path;

    @Column
    private Genre genre;

    @Column
    private String type;

}
