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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicPiece piece = (MusicPiece) o;

        if (id != piece.id) return false;
        if (position != piece.position) return false;
        if (musicPuzzle != null ? !musicPuzzle.equals(piece.musicPuzzle) : piece.musicPuzzle != null) return false;
        return path != null ? path.equals(piece.path) : piece.path == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (musicPuzzle != null ? musicPuzzle.hashCode() : 0);
        result = 31 * result + position;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
