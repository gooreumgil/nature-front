package com.rainyheaven.nature.core.validator;

import com.rainyheaven.nature.core.annotation.BirthDay;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BirthDayValidator implements ConstraintValidator<BirthDay, String> {

    private String pattern;

    @Override
    public void initialize(BirthDay constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        SimpleDateFormat originalFormat = new SimpleDateFormat(pattern);
        try {
            originalFormat.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
