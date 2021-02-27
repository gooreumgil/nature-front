package com.rainyheaven.nature.core.domain.order;

import com.rainyheaven.nature.core.domain.delivery.Delivery;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int finalDiscountPrice;
    private int finalPrice;
    private int usedPoints;
    private int savedPoints;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public static Order create(OrderSaveRequestDto dto, User user) {

        Order order = new Order();
        order.finalDiscountPrice = dto.getFinalDiscountPrice();
        order.finalPrice = dto.getFinalPrice();
        order.usedPoints = dto.getUsedPoints();
        order.setUser(user);
        return order;
    }

    // 연관관계 편의 메소드
    private void setUser(User user) {
        this.user = user;
        user.addOrders(this);
    }


}
