package com.rainyheaven.nature.core.common;


import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;
import com.rainyheaven.nature.core.common.dto.PasswordChangeLinkRequestDto;

public interface EmailSender {
    void sendVerifyNum(EmailVerifyNumSendRequestDto emailVerifyNumSendRequestDto);
    void sendPasswordChangeLink(PasswordChangeLinkRequestDto passwordChangeLinkRequestDto);
}
