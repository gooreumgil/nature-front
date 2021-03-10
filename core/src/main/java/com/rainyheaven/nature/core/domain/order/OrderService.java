package com.rainyheaven.nature.core.domain.order;

import com.rainyheaven.nature.core.domain.address.Address;
import com.rainyheaven.nature.core.domain.delivery.Delivery;
import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Order findByIdWithUserAndDelivery(Long id) {
        return orderRepository.findByIdWithUserAndDelivery(id).orElseThrow(RuntimeException::new);
    }

    public Order findByIdWithUserAndDeliveryAndOrderItems(Long id) {
        return orderRepository.findByIdWithUserAndDeliveryAndOrderItems(id).orElseThrow(RuntimeException::new);
    }

    public Page<Order> findByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserIdWithOrderItemsAndDelivery(userId, pageable);
    }

    @Transactional
    public Order save(OrderSaveRequestDto orderSaveRequestDto, Long userId) {
        User user = userService.findById(userId);
        Order order = Order.create(orderSaveRequestDto, user, Delivery.create(orderSaveRequestDto));

        List<OrderItemSaveRequestDto> orderItemSaveRequestDtos = orderSaveRequestDto.getOrderItemSaveRequestDtos();
        List<Long> itemIds = orderItemSaveRequestDtos
                .stream().map(OrderItemSaveRequestDto::getId).collect(Collectors.toList());
        List<Item> items = itemService.findByIds(itemIds);

        orderItemSaveRequestDtos.forEach(dto -> items.stream().filter(item -> item.getId().equals(dto.getId()))
                .findFirst().ifPresent(item -> OrderItem.create(dto, order, item)));

        // 사용한 적립금이 있다면 그만큼 minus
        if (!ObjectUtils.isEmpty(orderSaveRequestDto.getUsedPoints())) {
            user.minusPoints(orderSaveRequestDto.getUsedPoints());
        }

        // 주문한 상품의 갯수만큼 item에서 minus
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> items.stream().filter(item -> orderItem.getItem().getId().equals(item.getId()))
                .findFirst().ifPresent(item -> item.minusStockQuantity(orderItem.getItemQuantity())));
        
        // 기본 주소로 등록하기를 체크 했을 시
        if (orderSaveRequestDto.isRegisterDefaultAddress()) {
            registerDefaultAddress(orderSaveRequestDto, user);
        }
        
        // 신규 배송지를 선택했을 시
        if (orderSaveRequestDto.isRegisterNewAddress()) {
            registerNewAddress(orderSaveRequestDto, user);
        }

        return orderRepository.save(order);

    }

    public int countAllByUserAndDeliveryStatus(Long userId, String deliveryStatus) {
        return orderRepository.countAllByUserIdAndDeliveryDeliveryStatus(userId, DeliveryStatus.valueOf(deliveryStatus));
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Order order = findByIdWithUserAndDeliveryAndOrderItems(id);
        User user = order.getUser();
        if (!user.getId().equals(userId)) {
            throw new RuntimeException();
        }


        if (order.getOrderStatus().equals(OrderStatus.COMP)) {
            int savedPoints = order.getSavedPoints();
            user.minusPoints(savedPoints);
        }

        int usedPoints = order.getUsedPoints();
        if (usedPoints > 0) {
            user.plusPoints(usedPoints);
        }

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            Item item = orderItem.getItem();
            item.plusStockQuantity(orderItem.getItemQuantity());
        });

        order.setOrderStatus(OrderStatus.CANCEL);

    }

    @Transactional
    public void confirm(Long id, Long userId) {
        Order order = findByIdWithUserAndDelivery(id);
        User user = order.getUser();

        // order confirm을 하는 유저가 실제 주문을 한 유저와 같은지 체크
        if (!user.getId().equals(userId)) {
            throw new RuntimeException();
        }

        Delivery delivery = order.getDelivery();

        // 포인트 적립
        int points = Math.toIntExact(Math.round(order.getFinalPrice() * 0.03));
        order.confirm(points);
        user.savePoints(points);

        // deliveryStatus comp로 업데이트
        delivery.deliveryComp();

    }

    private void registerDefaultAddress(OrderSaveRequestDto orderSaveRequestDto, User user) {
        String mainAddress = orderSaveRequestDto.getMainAddress();
        String detailAddress = orderSaveRequestDto.getDetailAddress();
        Integer zipCode = Integer.valueOf(orderSaveRequestDto.getZipCode());

        List<Address> addresses = user.getAddresses();

        // 유저의 주소들 중에서 기본 주소로 등록되어 있는 주소가 있는지 필터링
        Optional<Address> userDefaultAddress = addresses.stream().filter(Address::isDefault).findFirst();

        // 기본 주소가 이미 있는 경우 -> 업데이트 else 메인 주소를 생성한후 user에 add
        userDefaultAddress.ifPresentOrElse(address -> address.update(mainAddress, detailAddress, zipCode), () -> {
            Address defaultAddress = Address.create(mainAddress, detailAddress, zipCode, true);
            user.addAddress(defaultAddress);
        });
    }

    private void registerNewAddress(OrderSaveRequestDto orderSaveRequestDto, User user) {
        String mainAddress = orderSaveRequestDto.getMainAddress();
        String detailAddress = orderSaveRequestDto.getDetailAddress();
        Integer zipCode = Integer.valueOf(orderSaveRequestDto.getZipCode());

        Address newAddress = Address.create(mainAddress, detailAddress, zipCode, false);
        user.addAddress(newAddress);

    }
}
