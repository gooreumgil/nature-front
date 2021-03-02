package com.rainyheaven.nature.core.domain.order;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.delivery.Delivery;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private int finalDiscountPrice;
    private int finalPrice;
    private int usedPoints;
    private int savedPoints;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public static Order create(OrderSaveRequestDto dto, User user, Delivery delivery) {

        Order order = new Order();
        order.paymentMethod = PaymentMethod.valueOf(dto.getPaymentMethod());
        order.finalDiscountPrice = dto.getFinalDiscountPrice();
        order.finalPrice = dto.getFinalPrice();
        order.usedPoints = dto.getUsedPoints();
        order.setUser(user);
        order.setDelivery(delivery);
        order.setCreatedDate(LocalDateTime.now());
        order.setLastModifiedDate(LocalDateTime.now());
        return order;
    }
    
    // 연관관계 편의 메소드
    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    // 연관관계 편의 메소드
    private void setUser(User user) {
        this.user = user;
        user.addOrders(this);
    }


    // 연관관계 편의 메소드
    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
