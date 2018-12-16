package com.music.puzzle.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.music.puzzle.authorization.SecurityConstants.SECRET;

public class JwtHelper {

    public static String generate(String subject)  {
        return "Bearer " + Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

    public static String parse(String token) {
        String user;
        try {
            user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
        return user;
    }

}
