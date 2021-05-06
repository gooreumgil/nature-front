package com.rainyheaven.nature.core.domain.address.dto.app;

import com.rainyheaven.nature.core.annotation.BirthDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {

    @NotBlank(message = "메인주소를 입력해주세요.")
    @Size(min = 5, max = 35, message = "메인주소는 5 ~ 35자로 입력해주세요.")
    private String mainAddress;

    @NotBlank(message = "상세주소를 입력해주세요.")
    @Size(min = 3, max = 50, message = "상세주소는 3 ~ 50자로 입력해주세요.")
    private String detailAddress;

    @NotBlank(message = "우편번호를 입력해주세요.")
    @Pattern(regexp = "[0-6][0-3]\\d{3}", message = "우편번호 형식에 맞게 입력해주세요.")
    private String zipCode;

    private boolean registerDefaultAddress;
    private boolean registerNewAddress;
    private boolean useExistingAddress;
    private Long existingAddressId;

}
