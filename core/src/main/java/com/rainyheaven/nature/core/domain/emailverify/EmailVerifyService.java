package com.rainyheaven.nature.core.domain.emailverify;

import com.rainyheaven.nature.core.common.dto.EmailVerifyNumConfirmRequestDto;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.exception.EmailVerifyException;
import com.rainyheaven.nature.core.exception.EmailVerifyExceptionType;
import com.rainyheaven.nature.core.exception.UserException;
import com.rainyheaven.nature.core.exception.UserExceptionType;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailVerifyService {

    private final EmailVerifyRepository emailVerifyRepository;
    private final UserService userService;

    private final AES256Util aes256Util;

    @Transactional
    public EmailVerify save(String email) {

        if (userService.existByEmail(email)) throw new UserException(UserExceptionType.ALREADY_EXIST_EMAIL);

        Optional<EmailVerify> optionalEmailVerify = emailVerifyRepository.findByEmail(aes256Util.encode(email));
        int verifyNum = getVerifyNum();

        if (optionalEmailVerify.isPresent()) {
            EmailVerify emailVerify = optionalEmailVerify.get();
            emailVerify.updateVerifyNum(verifyNum);
            return emailVerify;
        }

        return emailVerifyRepository.save(EmailVerify.create(aes256Util.encode(email), verifyNum));
    }

    @Transactional
    public void verifyNumConfirm(EmailVerifyNumConfirmRequestDto dto) {

        Optional<EmailVerify> optionalEmailVerify = emailVerifyRepository.findByEmail(aes256Util.encode(dto.getEmail()));
        if (optionalEmailVerify.isEmpty()) throw new EmailVerifyException(EmailVerifyExceptionType.VERIFY_REQUEST_NOT_EXIST);

        EmailVerify emailVerify = optionalEmailVerify.get();
        boolean verifyNumMatch = emailVerify.getVerifyNum().equals(dto.getVerifyNum());
        LocalDateTime expiredTime = emailVerify.getExpiredTime();

        if (expiredTime.isBefore(LocalDateTime.now())) {
            throw new EmailVerifyException(EmailVerifyExceptionType.TIME_OUT);
        } else if (!verifyNumMatch) {
            throw new EmailVerifyException(EmailVerifyExceptionType.VERIFY_NUM_NOT_MATCHED);
        } else {
            emailVerify.accept();
        }

    }

    private int getVerifyNum() {
        Random random = new Random();

        int range = (int) Math.pow(10, 4);
        int trim = (int) Math.pow(10, 3);

        int verifyNum = random.nextInt(range) + trim;

        if (verifyNum > range) {
            verifyNum = verifyNum - trim;
        }
        return verifyNum;
    }


    public boolean checkAccepted(String email) {
        return emailVerifyRepository.existsByEmailAndAccepted(aes256Util.encode(email), true);

    }
}
