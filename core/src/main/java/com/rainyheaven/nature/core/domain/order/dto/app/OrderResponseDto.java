package com.rainyheaven.nature.core.domain.order.dto.app;

import com.rainyheaven.nature.core.domain.delivery.dto.app.DeliveryResponseDto;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private String status;
    private LocalDateTime orderAt;
    private DeliveryResponseDto deliveryResponseDto;
    private List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.finalDiscountPrice = order.getFinalDiscountPrice();
        this.finalPrice = order.getFinalPrice();
        this.usedPoints = order.getUsedPoints();
        this.savedPoints = order.getSavedPoints();
        this.status = order.getOrderStatus().name();
        this.orderAt = order.getCreatedDate();
        this.deliveryResponseDto = new DeliveryResponseDto(order.getDelivery());

    }

    public void addAllOrderItems(List<OrderItem> orderItems) {
        this.orderItemResponseDtos.addAll(
                orderItems.stream()
                .map(orderItem -> new OrderItemResponseDto(orderItem))
                .collect(Collectors.toList()));
    }
}
