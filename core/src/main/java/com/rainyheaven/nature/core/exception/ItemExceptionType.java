package com.rainyheaven.nature.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemExceptionType {

    NOT_EXIST_ITEM("존재하지 않는 상품입니다.");

    private final String message;

    public String message() {return message;}

}
