package com.rainyheaven.nature.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserExceptionType {

    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),
    NOT_EXIST_USER("존재하지 않는 유저입니다."),
    EMAIL_NOT_VERIFIED("이메일 인증이 완료되지 않았습니다."),
    EMAIL_FORM_NOT_VALID("형식에 맞지 않는 이메일입니다."),
    EMAIL_NULL("이메일을 입력해주세요."),
    USER_NAME_NULL("닉네임을 입력해주세요."),
    USER_NAME_LENGTH_NOT_MATCHED("이름은 2 ~ 10자로 입력해주세요."),
    PASSWORD_NULL("비밀번호를 입력해주세요."),
    PASSWORD_LENGTH_NOT_MATCHED("비밀번호는 6 ~ 14자로 입력해주세요."),
    PASSWORD_NOT_MATCHED("비밀번호가 서로 일치하지 않습니다."),
    BIRTH_DAY_NULL("생년월일을 입력해주세요."),
    BIRTH_DAY_FORM_NOT_VALID("생년월일이 형식에 맞지 않습니다."),
    BIRTH_DAY_LENGTH_NOT_MATCHED("생년월일은 8자로 입력해주세요."),
    PHONE_NUMBER_NULL("연락처를 입력해주세요."),
    PHONE_NUMBER_FORM_NOT_VALID("연락처가 형식에 맞지 않습니다."),
    EMAIL_SEND_FAILED("이메일 전송에 실패하였습니다.");

    private final String message;

    public String message() {
        return message;
    }


}
