package com.rainyheaven.nature.core.domain.orderitem;

import com.rainyheaven.nature.core.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Page<OrderItem> pageByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus, Pageable pageable) {
        return orderItemRepository.findAllByUserIdAndOrderStatus(userId, orderStatus, pageable);
    }

    @Transactional
    public void updateLeaveReview(Long id) {
        OrderItem orderItem = findById(id);
        orderItem.setLeaveReview(true);
    }

}
