package com.rainyheaven.nature.core.domain.orderitem;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int itemPrice;
    private int itemDiscountPrice;
    private int itemQuantity;

    public static OrderItem create(OrderItemSaveRequestDto dto, Order order, Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.itemPrice = dto.getItemPrice();
        orderItem.itemDiscountPrice = dto.getItemDiscountPrice();
        orderItem.itemQuantity = dto.getItemQuantity();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setCreatedDate(LocalDateTime.now());
        orderItem.setLastModifiedDate(LocalDateTime.now());
        return orderItem;
    }

    // 연관관계 편의 메소드
    private void setItem(Item item) {
        this.item = item;
        item.addOrderItems(this);
    }

    // 연관관계 편의 메소드
    private void setOrder(Order order) {
        this.order = order;
        order.addOrderItems(this);
    }

}
