package com.rainyheaven.nature.app.domain.user;

import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.domain.user.dto.app.UserResponseDto;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AES256Util aes256Util;

    @GetMapping
    public ResponseEntity<UserResponseDto> get(@AuthenticationPrincipal TokenUser tokenUser) {
        User user = userService.findByIdWithAddress(tokenUser.getId());
        return ResponseEntity.ok(new UserResponseDto(user, aes256Util.decode(user.getEmail())));
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        userService.save(userSaveRequestDto);
        return ResponseEntity.ok().build();
    }

}
