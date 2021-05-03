package com.rainyheaven.nature.app.utils;

import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final JwtUtil jwtUtil;

    public String getToken(User user) {
        return "Bearer " + jwtUtil.createToken(user);
    }

}
