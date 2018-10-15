package com.music.puzzle.domain;

import javax.persistence.*;

@Entity
@Table(name = "music_piece")
public class MusicPiece {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="musicId")
    private MusicPuzzle musicPuzzle;

    @Column
    private int position;

    @Column
    private String path;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MusicPuzzle getMusicPuzzle() {
        return musicPuzzle;
    }

    public void setMusicPuzzle(MusicPuzzle musicPuzzle) {
        this.musicPuzzle = musicPuzzle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
