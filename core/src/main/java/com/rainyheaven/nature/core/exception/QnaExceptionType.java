package com.rainyheaven.nature.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QnaExceptionType {

    CONTENT_NULL("내용을 입력해주세요."),
    CONTENT_LENGTH_NOT_MATCHED("문의내용은 500자 내로 입력해주세요");

    private final String message;

    public String message() {return message;}



}
