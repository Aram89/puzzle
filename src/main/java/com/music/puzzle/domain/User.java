package com.music.puzzle.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    @NonNull
    private String userName;

    @Column
    private String nickName;

    @Column
    private String phone;

    @Column
    private Integer score;

    @Column(unique = true)
    @NonNull
    private String email;

    @Column
    private Integer level;

    @Column
    private String password;

    @Column
    private Integer recoveryCode;

    @Column
    private String location;

}
