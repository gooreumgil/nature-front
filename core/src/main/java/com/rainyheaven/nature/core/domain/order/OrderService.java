package com.rainyheaven.nature.core.domain.order;

import com.rainyheaven.nature.core.domain.delivery.Delivery;
import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemService itemService;

    public Order findById(Long id) {
        return orderRepository.findByIdWithOrderItemsAndDelivery(id).orElseThrow(RuntimeException::new);
    }

    public Page<Order> findByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserIdWithOrderItemsAndDelivery(userId, pageable);
    }

    @Transactional
    public Order save(OrderSaveRequestDto orderSaveRequestDto, Long userId) {

        Order order = Order.create(orderSaveRequestDto, userService.findById(userId), Delivery.create(orderSaveRequestDto));

        List<OrderItemSaveRequestDto> orderItemSaveRequestDtos = orderSaveRequestDto.getOrderItemSaveRequestDtos();
        List<Long> itemIds = orderItemSaveRequestDtos
                .stream().map(OrderItemSaveRequestDto::getId).collect(Collectors.toList());
        List<Item> items = itemService.findByIds(itemIds);

        orderItemSaveRequestDtos.forEach(dto -> items.stream().filter(item -> item.getId().equals(dto.getId()))
                .findFirst().ifPresent(item -> OrderItem.create(dto, order, item)));

        return orderRepository.save(order);

    }

    public int countAllByUserAndDeliveryStatus(Long userId, String deliveryStatus) {
        return orderRepository.countAllByUserIdAndDeliveryDeliveryStatus(userId, DeliveryStatus.valueOf(deliveryStatus));
    }

    @Transactional
    public void delete(Long id, Long userId) {
        orderRepository.deleteByIdAndUserId(id, userId);
    }
}
