package com.rainyheaven.nature.core.annotation;

import com.rainyheaven.nature.core.validator.EmailVerifiedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {EmailVerifiedValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface EmailVerified {

    String message() default "이메일 인증을 완료해주세요.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
