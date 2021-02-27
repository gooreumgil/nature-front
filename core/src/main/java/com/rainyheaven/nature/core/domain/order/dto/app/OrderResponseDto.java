package com.rainyheaven.nature.core.domain.order.dto.app;

import com.rainyheaven.nature.core.domain.delivery.dto.app.DeliveryResponseDto;
import com.rainyheaven.nature.core.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;
    private int finalDiscountPrice;
    private int finalPrice;
    private int usedPoints;
    private int savedPoints;
    private DeliveryResponseDto deliveryResponseDto;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.finalDiscountPrice = order.getFinalDiscountPrice();
        this.finalPrice = order.getFinalPrice();
        this.usedPoints = order.getUsedPoints();
        this.savedPoints = order.getSavedPoints();
        this.deliveryResponseDto = new DeliveryResponseDto(order.getDelivery());

    }
}
