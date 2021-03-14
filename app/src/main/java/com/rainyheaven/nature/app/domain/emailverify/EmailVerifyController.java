package com.rainyheaven.nature.app.domain.emailverify;

import com.rainyheaven.nature.core.common.EmailSender;
import com.rainyheaven.nature.core.common.dto.EmailVerifyNumConfirmRequestDto;
import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerify;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerifyService;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/email-verify")
@RequiredArgsConstructor
public class EmailVerifyController {

    private final EmailVerifyService emailVerifyService;
    private final EmailSender emailSender;
    private final AES256Util aes256Util;

    @PostMapping
    public ResponseEntity<Void> save(@RequestParam("email") String email) {
        EmailVerify savedEmailVerify = emailVerifyService.save(email);
        emailSender.sendVerifyNum(new EmailVerifyNumSendRequestDto(aes256Util.decode(savedEmailVerify.getEmail()), savedEmailVerify.getVerifyNum()));
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> confirm(@RequestBody EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto) {

        emailVerifyService.verifyNumConfirm(emailVerifyNumConfirmRequestDto);
        return ResponseEntity.ok().build();

    }

}
