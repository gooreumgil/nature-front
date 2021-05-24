package com.rainyheaven.nature.core.domain.user.dto.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDto {

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 6, max = 14, message = "패스워드는 6~14자로 입력해주세요.")
    private String password;

    @NotBlank(message = "패스워드 확인칸을 입력해주세요.")
    @Size(min = 6, max = 14, message = "패스워드는 6~14자로 입력해주세요.")
    private String passwordConfirm;

    @JsonIgnore
    @AssertTrue(message = "패스워드가 서로 다릅니다.")
    public boolean isPasswordMatchedValidator() {
        if (password == null || passwordConfirm == null) return false;
        return password.equals(passwordConfirm);
    }

}
