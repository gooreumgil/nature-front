package com.rainyheaven.nature.core.domain.user;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser {

    private Long id;
    private String name;
    private String role;

    public TokenUser(Claims claims) {
        int userId = (Integer) claims.get("userId");
        this.id = (long) userId;
        this.name = (String) claims.get("name");
        this.role = (String) claims.get("role");
    }
}
