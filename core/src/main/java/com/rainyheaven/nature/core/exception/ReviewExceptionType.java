package com.rainyheaven.nature.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewExceptionType {

    CONTENT_NULL("내용을 입력해주세요."),
    CONTENT_LENGTH_NOT_MATCHED("리뷰 내용은 500자까지 가능합니다."),
    RATING_NULL("별점을 입력해주세요."),
    IMAGE_FILE_LENGTH_NOT_MATCHED("이미지는 최대 3장까지 가능합니다.");

    private final String message;

    public String message() {return message;}



}
