package com.rainyheaven.nature.core.annotation;

import com.rainyheaven.nature.core.validator.BirthDayValidator;
import com.rainyheaven.nature.core.validator.EmailUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {EmailUniqueValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface EmailUnique {

    String message() default "이미 가입된 이메일입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
