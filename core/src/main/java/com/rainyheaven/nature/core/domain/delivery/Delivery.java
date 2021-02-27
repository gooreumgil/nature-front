package com.rainyheaven.nature.core.domain.delivery;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.embedded.OrderAddress;
import com.rainyheaven.nature.core.domain.embedded.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
