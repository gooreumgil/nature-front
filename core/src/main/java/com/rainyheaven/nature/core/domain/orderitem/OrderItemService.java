package com.rainyheaven.nature.core.domain.orderitem;

import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
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

    public Page<OrderItem> pageByUserIdAndDeliveryStatus(Long userId, DeliveryStatus deliveryStatus, Pageable pageable) {
        return orderItemRepository.findAllByUserIdAndDeliveryStatus(userId, deliveryStatus, pageable);
    }

}
