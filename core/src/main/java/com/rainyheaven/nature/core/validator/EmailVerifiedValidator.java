package com.rainyheaven.nature.core.validator;

import com.rainyheaven.nature.core.annotation.EmailVerified;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerifyRepository;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmailVerifiedValidator implements ConstraintValidator<EmailVerified, String> {

    private final EmailVerifyRepository emailVerifyRepository;
    private final AES256Util aes256Util;


    @Override
    public void initialize(EmailVerified constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return emailVerifyRepository.existsByEmailAndAccepted(aes256Util.encode(value), true);
    }
}
