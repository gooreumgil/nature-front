package com.rainyheaven.nature.core.domain.user.dto.app;

import com.rainyheaven.nature.core.annotation.EmailExist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(min = 8, max = 35, message = "이메일은 8 ~ 35자로 입력해주세요.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 6, max = 14, message = "패스워드는 6~14자로 입력해주세요.")
    private String password;

}
