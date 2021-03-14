package com.rainyheaven.nature.core.common;

import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"test", "local"})
@Slf4j
@RequiredArgsConstructor
public class ConsoleEmailSendService implements EmailSender {

    @Override
    public void sendVerifyNum(EmailVerifyNumSendRequestDto emailVerifyNumSendRequestDto) {
        log.info("받는사람: {}", emailVerifyNumSendRequestDto.getEmail());
        log.info("인증번호: {}", emailVerifyNumSendRequestDto.getVerifyNum());
    }

    @Override
    public void sendPasswordChangeLink() {

    }
}
