package com.rainyheaven.nature.core.validator;

import com.rainyheaven.nature.core.annotation.EmailExist;
import com.rainyheaven.nature.core.domain.user.UserRepository;
import com.rainyheaven.nature.core.domain.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmailExistValidator implements ConstraintValidator<EmailExist, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(EmailExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return userRepository.existsByEmailAndUserStatus(value, UserStatus.ACTIVE);
    }
}
