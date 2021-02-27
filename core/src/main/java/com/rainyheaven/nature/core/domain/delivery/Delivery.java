package com.rainyheaven.nature.core.domain.delivery;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.embedded.OrderAddress;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    private String receiver;
    private int deliveryPrice;
    private String memo;

    @Embedded
    private OrderAddress orderAddress;

    @Embedded
    private PhoneNumber phoneNumber;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public static Delivery create(OrderSaveRequestDto dto) {
        Delivery delivery = new Delivery();
        delivery.receiver = dto.getReceiver();
        delivery.deliveryPrice = dto.getDeliveryPrice();
        delivery.orderAddress = new OrderAddress(dto.getMainAddress(), dto.getDetailAddress(), dto.getZipCode());
        delivery.phoneNumber = new PhoneNumber(dto.getPhoneNum1(), dto.getPhoneNum2(), dto.getPhoneNum3());
        delivery.deliveryStatus = DeliveryStatus.READY;
        delivery.setCreatedDate(LocalDateTime.now());
        delivery.setLastModifiedDate(LocalDateTime.now());
        return delivery;
    }

}