package com.rainyheaven.nature.app.session;

import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.domain.user.dto.app.LoginRequestDto;
import com.rainyheaven.nature.core.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<String> verify(@AuthenticationPrincipal TokenUser tokenUser, HttpServletRequest request) {

        userService.existCheck(tokenUser.getId());
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Claims claims = jwtUtil.getClaims(token);

        if (userService.checkTokenExpiredTime(claims.getExpiration())) {
            User user = userService.findById(tokenUser.getId());
            String accessToken = jwtUtil.createToken(user);
            return ResponseEntity.ok(accessToken);
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userService.authenticate(email, password);

        return ResponseEntity.ok(jwtUtil.createToken(user));
    }

}
