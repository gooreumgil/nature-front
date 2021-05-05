package com.rainyheaven.nature.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailVerifyExceptionType {

    TIME_OUT("인증 유효 시간이 초과되었습니다. 인증 요청을 다시 해주세요."),
    VERIFY_REQUEST_NOT_EXIST("인증 요청을 한 적이 업습니다. 인증 요청을 다시 해주세요."),
    VERIFY_NUM_NOT_MATCHED("인증번호가 일치하지 않습니다.");

    private final String message;

    public String message(){ return message; }

}
