package com.rainyheaven.nature.core.domain.delivery;

import com.rainyheaven.nature.core.domain.address.dto.app.AddressRequestDto;
import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.embedded.OrderAddress;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
        AddressRequestDto addressRequestDto = dto.getAddressRequestDto();
        delivery.orderAddress = new OrderAddress(addressRequestDto.getMainAddress(), addressRequestDto.getDetailAddress(), addressRequestDto.getZipCode());
        delivery.phoneNumber = new PhoneNumber(dto.getPhoneNum1(), dto.getPhoneNum2(), dto.getPhoneNum3());
        delivery.deliveryStatus = DeliveryStatus.READY;
        delivery.setCreatedDate(LocalDateTime.now());
        delivery.setLastModifiedDate(LocalDateTime.now());
        return delivery;
    }

    // 구매 확정시 배송 완료로 수정
    public void deliveryComp() {
        this.deliveryStatus = DeliveryStatus.COMP;
        setLastModifiedDate(LocalDateTime.now());
    }
}
