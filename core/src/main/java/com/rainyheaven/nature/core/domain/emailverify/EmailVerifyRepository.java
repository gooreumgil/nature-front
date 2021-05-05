package com.rainyheaven.nature.core.domain.emailverify;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {

    Optional<EmailVerify> findByEmail(String email);
    boolean existsByEmailAndAccepted(String email, boolean accepted);

}
