package com.rainyheaven.nature.core.domain.orderitem;

import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
import com.rainyheaven.nature.core.domain.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "select oi from OrderItem oi join fetch oi.item join fetch oi.order where oi.order.orderStatus = :orderStatus and oi.leaveReview = false and oi.order.user.id = :userId",
            countQuery = "select count (oi) from OrderItem oi where oi.order.orderStatus = :orderStatus and oi.leaveReview = false and oi.order.user.id = :userId")
    Page<OrderItem> findAllByUserIdAndOrderStatus(@Param("userId") Long userId, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

}
