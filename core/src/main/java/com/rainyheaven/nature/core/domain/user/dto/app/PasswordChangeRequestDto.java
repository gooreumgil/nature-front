package com.rainyheaven.nature.core.domain.user.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDto {

    private String password;
    private String passwordConfirm;

}
