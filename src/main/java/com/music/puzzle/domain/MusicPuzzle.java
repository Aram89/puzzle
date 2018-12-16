package com.music.puzzle.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "music_puzzle")
public class MusicPuzzle {

    @Id
    @GeneratedValue
    private long musicId;

    @OneToMany(mappedBy = "musicPuzzle", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<MusicPiece> pieces;

    @Column
    private Integer level;

    @Column
    private String name;

    @Column
    private String path;

    @Column
    private Genre genre;

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public List<MusicPiece> getPieces() {
        return pieces;
    }

    public void setPieces(List<MusicPiece> pieces) {
        this.pieces = pieces;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
