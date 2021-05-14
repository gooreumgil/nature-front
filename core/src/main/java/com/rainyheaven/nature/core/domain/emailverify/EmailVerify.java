package com.rainyheaven.nature.core.domain.emailverify;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerify extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_verify_id")
    private Long id;

    private String email;
    private Integer verifyNum;
    private LocalDateTime expiredTime;
    private boolean accepted;

    public static EmailVerify create(String email, Integer verifyNum) {
        EmailVerify emailVerify = new EmailVerify();
        emailVerify.email = email;
        emailVerify.verifyNum = verifyNum;
        emailVerify.expiredTime = LocalDateTime.now().plusMinutes(10);

        return emailVerify;
    }


    public void updateVerifyNum(int verifyNum) {
        this.verifyNum = verifyNum;
        this.accepted = false;
        this.expiredTime = LocalDateTime.now().plusMinutes(10);
    }

    public void accept() {
        this.accepted = true;
    }
}
