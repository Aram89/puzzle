package com.music.puzzle.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class UserPuzzle {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "musicId")
    private MusicPuzzle puzzle;

    @Column
    private int level;

    @Enumerated(EnumType.STRING)
    private PuzzleStatus status;
}
