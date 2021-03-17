package com.rainyheaven.nature.core.domain.user;

import com.rainyheaven.nature.core.domain.emailverify.EmailVerifyService;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.exception.UserException;
import com.rainyheaven.nature.core.exception.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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
        isValidName(userSaveRequestDto.getName());
        isEmailDuplicated(userSaveRequestDto.getEmail());
        isValidEmail(userSaveRequestDto.getEmail());
        isValidPassword(userSaveRequestDto.getPassword(), userSaveRequestDto.getPasswordConfirm());
        isValidPhoneNumber(userSaveRequestDto.getPhoneNumber());
        isValidBirthDay(userSaveRequestDto.getBirthDay());
        isEmailVerifyAccepted(userSaveRequestDto.getEmail());

    }

    private void isEmailDuplicated(String email) {
        if (checkNull(email)) {
            throw new UserException(UserExceptionType.EMAIL_NULL);
        }
        boolean exist = userService.existByEmail(email.trim());
        if (exist) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_EMAIL);
        }
    }

    private void isEmailVerifyAccepted(String email) {
        if (checkNull(email)) {
            throw new UserException(UserExceptionType.EMAIL_NULL);
        }
        boolean accepted = emailVerifyService.checkAccepted(email.trim());
        if (!accepted) {
            throw new UserException(UserExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    public void isValidName(String name) {
        if (checkNull(name)) {
            throw new UserException(UserExceptionType.USER_NAME_NULL);
        }

        String trim = name.trim();
        int length = trim.length();

        if (length < 2 || length > 10) {
            throw new UserException(UserExceptionType.USER_NAME_LENGTH_NOT_MATCHED);
        }

    }

    public void isValidEmail(String email) {
        if (checkNull(email)) {
            throw new UserException(UserExceptionType.EMAIL_NULL);
        }
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email.trim());
        if(!m.matches()) {
            throw new UserException(UserExceptionType.EMAIL_FORM_NOT_VALID);
        }


    }

    private void isValidPassword(String password, String passwordConfirm) {

        if (checkNull(password) || checkNull(passwordConfirm)) {
            throw new UserException(UserExceptionType.PASSWORD_NULL);
        }

        String trim = password.trim();
        int length = trim.length();

        if (length < 6 || length > 14) {
            throw new UserException(UserExceptionType.PASSWORD_LENGTH_NOT_MATCHED);
        }

        if (!password.equals(passwordConfirm)) {
            throw new UserException(UserExceptionType.PASSWORD_NOT_MATCHED);
        }

    }

    public void isValidBirthDay(String birthDay) {
        if (checkNull(birthDay)) {
            throw new UserException(UserExceptionType.BIRTH_DAY_NULL);
        }

        String trim = birthDay.trim();
        int length = trim.length();

        if (length != 8) {
            throw new UserException(UserExceptionType.BIRTH_DAY_LENGTH_NOT_MATCHED);
        }

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            originalFormat.parse(birthDay);

        } catch (ParseException e) {
            throw new UserException(UserExceptionType.BIRTH_DAY_FORM_NOT_VALID);
        }

    }

    private void isValidPhoneNumber(String phoneNumber) {
        if (checkNull(phoneNumber)) {
            throw new UserException(UserExceptionType.PHONE_NUMBER_NULL);
        }

        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        boolean matches = Pattern.matches(regEx, phoneNumber);

        if (!matches) {
            throw new UserException(UserExceptionType.PHONE_NUMBER_FORM_NOT_VALID);
        }
    }

    private boolean checkNull(String val) {
        if (ObjectUtils.isEmpty(val)) return true;
        else return ObjectUtils.isEmpty(val.trim());
    }

}
