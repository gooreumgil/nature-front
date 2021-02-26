package com.rainyheaven.nature.core.domain.address.dto.app;

import com.rainyheaven.nature.core.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    private Long id;
    private String main;
    private String detail;
    private Integer zipCode;

    public AddressResponseDto(Address address) {
        this.id = address.getId();
        this.main = address.getMain();
        this.detail = address.getDetail();
        this.zipCode = address.getZipCode();
    }
}
