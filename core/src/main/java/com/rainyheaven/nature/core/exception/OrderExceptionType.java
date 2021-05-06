package com.rainyheaven.nature.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderExceptionType {

    NOT_EXIST_ORDER("존재하지 않는 주문입니다."),
    ORDER_USER_REQUEST_USER_NOT_MATCHED("주문한 사용자와 요청 사용자가 일치하지 않습니다.");

    private final String message;

    public String message(){ return message; }

}
