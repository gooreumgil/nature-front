package com.rainyheaven.nature.core.interceptor;

import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private static final String HEADER_AUTH = "Authorization";
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String token = request.getHeader(HEADER_AUTH);
        System.out.println(token);

//        userService.existCheck(tokenUser.getId());
//        String token = request.getHeader("Authorization").substring("Bearer ".length());
//        Claims claims = jwtUtil.getClaims(token);
//
//        if (userService.checkTokenExpiredTime(claims.getExpiration())) {
//            User user = userService.findById(tokenUser.getId());
//            String accessToken = jwtUtil.createToken(user);
//            return ResponseEntity.ok(accessToken);
//        }
//
//        return ResponseEntity.ok(token);

        if (token != null && jwtUtil.usable(token)) {
            return true;
        } else {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }

    }
}
