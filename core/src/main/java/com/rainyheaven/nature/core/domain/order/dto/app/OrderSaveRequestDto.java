package com.rainyheaven.nature.core.domain.order.dto.app;

import com.rainyheaven.nature.core.domain.address.dto.app.AddressRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaveRequestDto {

    private String receiver;
    private String phoneNum1;
    private String phoneNum2;
    private String phoneNum3;
//    private String zipCode;
//    private String mainAddress;
//    private String detailAddress;
    private Integer usedPoints;
    private Integer finalDiscountPrice;
    private Integer finalPrice;
    private Integer deliveryPrice;
    private String paymentMethod;
    private AddressRequestDto addressRequestDto;
    private List<OrderItemSaveRequestDto> orderItemSaveRequestDtos = new ArrayList<>();
//    private boolean registerDefaultAddress;
//    private boolean registerNewAddress;


}
