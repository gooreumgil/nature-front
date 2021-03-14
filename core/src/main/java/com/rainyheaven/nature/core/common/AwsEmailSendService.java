package com.rainyheaven.nature.core.common;

import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "prod"})
@Slf4j
@RequiredArgsConstructor
public class AwsEmailSendService implements EmailSender {
    @Override
    public void sendVerifyNum(EmailVerifyNumSendRequestDto emailVerifyNumSendRequestDto) {

    }

    @Override
    public void sendPasswordChangeLink() {

    }
}
