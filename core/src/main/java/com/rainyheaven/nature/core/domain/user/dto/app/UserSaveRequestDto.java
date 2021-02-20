package com.rainyheaven.nature.core.domain.user.dto.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String name;
    private String password;

}
