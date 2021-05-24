package com.rainyheaven.nature.core.domain.order.dto.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rainyheaven.nature.core.domain.address.dto.app.AddressRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaveRequestDto {

    @NotBlank(message = "받는사람을 입력해주세요.")
    @Size(max = 20, message = "받는사람은 20자 이하로 입력해주세요.")
    private String receiver;

    private String phoneNum1;
    private String phoneNum2;
    private String phoneNum3;

    private Integer usedPoints;
    private Integer finalDiscountPrice;

    @NotNull
    private Integer finalPrice;
    private Integer deliveryPrice;
    private String paymentMethod;

    @Valid
    private AddressRequestDto addressRequestDto;

    @Valid
    private List<OrderItemSaveRequestDto> orderItemSaveRequestDtos = new ArrayList<>();

    @JsonIgnore
    @AssertTrue(message = "핸드폰 번호를 정확하게 입력해주세요.")
    public boolean isPhoneNumberValid() {

        if (phoneNum1 == null || phoneNum2 == null || phoneNum3 == null) return false;

        String phoneNumber = phoneNum1.trim() + phoneNum2.trim() + phoneNum3.trim();
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

        return Pattern.matches(regEx, phoneNumber);

    }




}
