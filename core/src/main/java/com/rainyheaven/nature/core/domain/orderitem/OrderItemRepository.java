package com.rainyheaven.nature.core.domain.orderitem;

import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "select oi from OrderItem oi join fetch oi.item where oi.order.user.id = :userId and oi.order.delivery.deliveryStatus = :deliveryStatus and oi.leaveReview = false",
            countQuery = "select count (oi) from OrderItem oi where oi.order.user.id = :userId and oi.order.delivery.deliveryStatus = :deliveryStatus and oi.leaveReview = false")
    Page<OrderItem> findAllByUserIdAndDeliveryStatus(@Param("userId") Long userId, @Param("deliveryStatus") DeliveryStatus deliveryStatus, Pageable pageable);

}
