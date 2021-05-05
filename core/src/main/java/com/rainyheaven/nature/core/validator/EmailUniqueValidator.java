package com.rainyheaven.nature.core.validator;

import com.rainyheaven.nature.core.annotation.EmailUnique;
import com.rainyheaven.nature.core.domain.user.UserRepository;
import com.rainyheaven.nature.core.domain.user.UserStatus;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    private final UserRepository userRepository;
    private final AES256Util aes256Util;

    @Override
    public void initialize(EmailUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !userRepository.existsByEmailAndUserStatus(aes256Util.encode(value), UserStatus.ACTIVE);
    }
}
