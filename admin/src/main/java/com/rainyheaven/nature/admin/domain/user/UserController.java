package com.rainyheaven.nature.admin.domain.user;

import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.domain.user.dto.app.UserResponseDto;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AES256Util aes256Util;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAll(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<User> userPage = userService.findAll(pageable);
        Page<UserResponseDto> pageMap = userPage.map(user -> new UserResponseDto(user, aes256Util.decode(user.getEmail())));

        return ResponseEntity.ok(pageMap);


    }

}
