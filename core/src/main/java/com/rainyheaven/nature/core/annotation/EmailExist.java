package com.rainyheaven.nature.core.annotation;

import com.rainyheaven.nature.core.validator.EmailExistValidator;
import com.rainyheaven.nature.core.validator.EmailVerifiedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {EmailExistValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface EmailExist {

    String message() default "해당 이메일의 사용자가 존재하지 않습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
