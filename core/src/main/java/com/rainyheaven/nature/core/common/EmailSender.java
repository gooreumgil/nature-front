package com.rainyheaven.nature.core.common;


import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;

public interface EmailSender {
    void sendVerifyNum(EmailVerifyNumSendRequestDto emailVerifyNumSendRequestDto);
    void sendPasswordChangeLink();
}
