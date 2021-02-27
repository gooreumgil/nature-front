package com.rainyheaven.nature.core.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o join fetch o.orderItems join fetch o.delivery where o.user.id = :userId",
            countQuery = "select count (o) from Order o where o.user.id = :userId")
    Page<Order> findByUserIdWithOrderItemsAndDelivery(@Param("userId") Long userId, Pageable pageable);

}
