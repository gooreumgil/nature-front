package com.rainyheaven.nature.app.domain.emailverify;

import com.rainyheaven.nature.core.domain.emailverify.EmailVerify;
import com.rainyheaven.nature.core.domain.emailverify.EmailVerifyRepository;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerifyFactory {

    private final EmailVerifyRepository emailVerifyRepository;
    private final AES256Util aes256Util;

    public void save(String email) {
        EmailVerify emailVerify = EmailVerify.create(aes256Util.encode(email), 1234);
        emailVerify.accept();
        emailVerifyRepository.save(emailVerify);

    }

}
