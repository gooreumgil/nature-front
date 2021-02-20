package com.rainyheaven.nature.core.utils;

import com.rainyheaven.nature.core.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private Key key;
    public static long PLUS_MILLS = (1000 * 60 * 60 * 24) * 7L;

    public JwtUtil(String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .claim("role", user.getUserRole());

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expireTime())
                .compact();
    }

    public Claims getClaims(String token) {
        return parsing(token);
    }

    public boolean usable(String token) {
        try {
            parsing(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("권한이 유효하지 않습니다.");
        }
    }

    private Claims parsing(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date expireTime() {
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + PLUS_MILLS);
        return expireTime;
    }
}
