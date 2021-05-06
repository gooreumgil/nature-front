package com.rainyheaven.nature.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerifyNumConfirmRequestDto {

    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @Min(value = 1000, message = "인증번호는 4자리입니다.")
    @Max(value = 9999, message = "인증번호는 4자리입니다.")
    private int verifyNum;

}
