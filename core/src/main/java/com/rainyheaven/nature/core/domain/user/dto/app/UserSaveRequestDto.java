package com.rainyheaven.nature.core.domain.user.dto.app;

import com.rainyheaven.nature.core.annotation.BirthDay;
import com.rainyheaven.nature.core.annotation.EmailUnique;
import com.rainyheaven.nature.core.annotation.EmailVerified;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Size(min = 8, max = 35, message = "이메일은 8 ~ 35자로 입력해주세요.")
    @EmailUnique
    @EmailVerified
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2 ~ 10자로 입력해주세요.")
    private String name;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 6, max = 14, message = "패스워드는 6~14자로 입력해주세요.")
    private String password;

    @NotBlank(message = "패스워드 확인칸을 입력해주세요.")
    @Size(min = 6, max = 14, message = "패스워드는 6~14자로 입력해주세요.")
    private String passwordConfirm;

    @Pattern(regexp = "(\\d{3})(\\d{3,4})(\\d{4})", message = "핸드폰 형식에 맞지 않습니다.")
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Size(max = 8, min = 8, message = "생년월일은 8자로 입력해주세요")
    @BirthDay
    private String birthDay;

    @AssertTrue(message = "패스워드가 서로 다릅니다.")
    public boolean isPasswordMatched() {
        if (password == null || passwordConfirm == null) return false;
        return password.equals(passwordConfirm);
    }



}
