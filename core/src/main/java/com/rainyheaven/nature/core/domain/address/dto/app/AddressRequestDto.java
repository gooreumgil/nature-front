package com.rainyheaven.nature.core.domain.address.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {

    private String mainAddress;
    private String detailAddress;
    private String zipCode;
    private boolean registerDefaultAddress;
    private boolean registerNewAddress;
    private boolean useExistingAddress;
    private Long existingAddressId;

}
