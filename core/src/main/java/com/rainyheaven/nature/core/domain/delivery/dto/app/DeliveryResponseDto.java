package com.rainyheaven.nature.core.domain.delivery.dto.app;

import com.rainyheaven.nature.core.domain.delivery.Delivery;
import com.rainyheaven.nature.core.domain.embedded.OrderAddress;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {

    private Long id;
    private String receiver;
    private int deliveryPrice;
    private String memo;
    private String phoneNum1;
    private String phoneNum2;
    private String phoneNum3;
    private String mainAddress;
    private String detailAddress;
    private String zipCode;
    private String status;

    public DeliveryResponseDto(Delivery delivery) {
        this.id = delivery.getId();
        this.receiver = delivery.getReceiver();
        this.deliveryPrice = delivery.getDeliveryPrice();
        this.memo = delivery.getMemo();

        PhoneNumber phoneNumber = delivery.getPhoneNumber();
        this.phoneNum1 = phoneNumber.getPhoneNum1();
        this.phoneNum2 = phoneNumber.getPhoneNum2();
        this.phoneNum3 = phoneNumber.getPhoneNum3();

        OrderAddress orderAddress = delivery.getOrderAddress();
        this.mainAddress = orderAddress.getMain();
        this.detailAddress = orderAddress.getDetail();
        this.zipCode = orderAddress.getZipCode();

        this.status = delivery.getDeliveryStatus().name();
    }
}
