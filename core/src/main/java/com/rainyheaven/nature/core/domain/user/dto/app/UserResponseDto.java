package com.rainyheaven.nature.core.domain.user.dto.app;

import com.rainyheaven.nature.core.domain.address.Address;
import com.rainyheaven.nature.core.domain.address.dto.app.AddressResponseDto;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String phoneNum1;
    private String phoneNum2;
    private String phoneNum3;
    private String email;
    private Integer ownPoints;
    private List<AddressResponseDto> addressResponseDtos = new ArrayList<>();

    public UserResponseDto(User user, String decodedEmail) {
        this.id = user.getId();
        this.name = user.getName();

        PhoneNumber phoneNumber = user.getPhoneNumber();
        this.phoneNum1 = phoneNumber.getPhoneNum1();
        this.phoneNum2 = phoneNumber.getPhoneNum2();
        this.phoneNum3 = phoneNumber.getPhoneNum3();

        this.email = decodedEmail;
        this.ownPoints = user.getPoints();

        List<Address> addresses = user.getAddresses();
        if (!addresses.isEmpty()) {
            addressResponseDtos.addAll(
                    addresses.stream()
                            .map(AddressResponseDto::new)
                            .collect(Collectors.toList())
            );
        }

    }
}
