package com.rainyheaven.nature.core.domain.order.dto.app;

import com.rainyheaven.nature.core.domain.delivery.dto.app.DeliveryResponseDto;
import com.rainyheaven.nature.core.domain.order.Order;
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
public class OrderResponseDto {

    private Long id;
    private int finalDiscountPrice;
    private int finalPrice;
    private int usedPoints;
    private int savedPoints;
    private DeliveryResponseDto deliveryResponseDto;
    private List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.finalDiscountPrice = order.getFinalDiscountPrice();
        this.finalPrice = order.getFinalPrice();
        this.usedPoints = order.getUsedPoints();
        this.savedPoints = order.getSavedPoints();
        this.deliveryResponseDto = new DeliveryResponseDto(order.getDelivery());
        this.orderItemResponseDtos.addAll(getOrderItemsDtos(order));

    }

    private List<OrderItemResponseDto> getOrderItemsDtos(Order order) {
        return order.getOrderItems().stream()
        .map(OrderItemResponseDto::new)
        .collect(Collectors.toList());
    }
}
