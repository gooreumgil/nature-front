package com.rainyheaven.nature.app.domain.emailverify;

import com.rainyheaven.nature.core.common.EmailSender;
import com.rainyheaven.nature.core.common.dto.EmailVerifyNumConfirmRequestDto;
import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerify;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerifyService;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@RestController
@RequestMapping("/v1/email-verify")
@RequiredArgsConstructor
@Validated
public class EmailVerifyController {

    private final EmailVerifyService emailVerifyService;
    private final EmailSender emailSender;
    private final AES256Util aes256Util;

    @PostMapping
    public ResponseEntity<Void> save(@RequestParam @Email(message = "이메일 형식에 맞게 입력해주세요.") String email) {
        EmailVerify savedEmailVerify = emailVerifyService.save(email);
        emailSender.sendVerifyNum(new EmailVerifyNumSendRequestDto(aes256Util.decode(savedEmailVerify.getEmail()), savedEmailVerify.getVerifyNum()));
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> confirm(@RequestBody @Valid EmailVerifyNumConfirmRequestDto emailVerifyNumConfirmRequestDto) {

        emailVerifyService.verifyNumConfirm(emailVerifyNumConfirmRequestDto);
        return ResponseEntity.ok().build();

    }

}
