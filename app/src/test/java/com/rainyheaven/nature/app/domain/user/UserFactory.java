package com.rainyheaven.nature.app.domain.user;

import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserRepository;
import com.rainyheaven.nature.core.domain.user.UserStatus;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.exception.UserException;
import com.rainyheaven.nature.core.exception.UserExceptionType;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AES256Util aes256Util;

    public User createUser(String email, String name, String password, String passwordConfirm, String phoneNumber, String birthDay) {
        UserSaveRequestDto dto = getUserSaveRequestDto(email, name, password, passwordConfirm, phoneNumber, birthDay);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setEmail(aes256Util.encode(dto.getEmail().trim()));

        User user = User.create(dto);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailAndUserStatus(email, UserStatus.ACTIVE).orElseThrow(() -> new UserException(UserExceptionType.NOT_EXIST_USER));
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(aes256Util.encode(email));
    }

    public UserSaveRequestDto getUserSaveRequestDto(String email, String name, String password, String passwordConfirm, String phoneNumber, String birthDay) {
        return new UserSaveRequestDto(email, name, password, passwordConfirm, phoneNumber, birthDay);
    }

}
