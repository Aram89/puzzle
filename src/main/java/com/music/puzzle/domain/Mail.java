package com.music.puzzle.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@NoArgsConstructor
public class Mail {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String email;

    @Column
    private String subject;

    @Column
    private String text;

}
