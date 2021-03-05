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

    public Page<OrderItem> pageByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus, Pageable pageable) {
        return orderItemRepository.findAllByUserIdAndOrderStatus(userId, orderStatus, pageable);
    }

}
