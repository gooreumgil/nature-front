package com.rainyheaven.nature.core.domain.user;

import com.rainyheaven.nature.core.domain.emailverify.EmailVerifyService;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.exception.UserException;
import com.rainyheaven.nature.core.exception.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final EmailVerifyService emailVerifyService;
    private final UserService userService;


    public void registerValidate(UserSaveRequestDto userSaveRequestDto) {
        isEmailDuplicated(userSaveRequestDto.getEmail());
        isValidName(userSaveRequestDto.getName());
        isValidEmail(userSaveRequestDto.getEmail());
        isValidPassword(userSaveRequestDto.getPassword(), userSaveRequestDto.getPasswordConfirm());
        isValidPhoneNumber(userSaveRequestDto.getPhoneNumber());
        isValidBirthDay(userSaveRequestDto.getBirthDay());
        isEmailVerifyAccepted(userSaveRequestDto.getEmail());

    }

    private void isEmailDuplicated(String email) {
        boolean exist = userService.existByEmail(email);
        if (exist) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_EMAIL);
        }
    }

    private void isEmailVerifyAccepted(String email) {
        boolean accepted = emailVerifyService.checkAccepted(email);
        if (!accepted) {
            throw new UserException(UserExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    public void isValidName(String name) {
        int length = name.length();
        if (length < 2 || length > 10) {
            throw new UserException(UserExceptionType.USER_NAME_LENGTH_NOT_MATCHED);
        }

    }

    public void isValidEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(!m.matches()) {
            throw new UserException(UserExceptionType.EMAIL_FORM_NOT_VALID);
        }


    }

    private void isValidPassword(String password, String passwordConfirm) {

        int length = password.length();

        if (length < 6 || length > 14) {
            throw new UserException(UserExceptionType.PASSWORD_LENGTH_NOT_MATCHED);
        }

        if (!password.equals(passwordConfirm)) {
            throw new UserException(UserExceptionType.PASSWORD_NOT_MATCHED);
        }

    }

    public void isValidBirthDay(String birthDay) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            originalFormat.parse(birthDay);

        } catch (ParseException e) {
            throw new UserException(UserExceptionType.BIRTH_DAY_FORM_NOT_VALID);
        }

    }

    private void isValidPhoneNumber(String phoneNumber) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        boolean matches = regEx.matches(phoneNumber);
        if (!matches) {
            throw new UserException(UserExceptionType.PHONE_NUMBER_FORM_NOT_VALID);
        }
    }

}
