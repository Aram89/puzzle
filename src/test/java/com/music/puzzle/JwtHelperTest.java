package com.music.puzzle;

import com.music.puzzle.domain.MusicPuzzle;
import com.music.puzzle.util.JwtHelper;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtHelperTest {

    private static final String EMAIL = "b";

    @org.junit.Test
    public void verify() {
        // Generate jwt at first
        String token = JwtHelper.generate(EMAIL);
        System.out.println(token);
        // Parse token
        String expectedEmail = JwtHelper.parse(token);
        // Assert
        Assert.assertEquals(expectedEmail, EMAIL);
    }



}
