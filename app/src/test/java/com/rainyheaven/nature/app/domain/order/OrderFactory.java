package com.rainyheaven.nature.app.domain.order;

import com.rainyheaven.nature.core.domain.address.dto.app.AddressRequestDto;
import com.rainyheaven.nature.core.domain.delivery.Delivery;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.OrderRepository;
import com.rainyheaven.nature.core.domain.order.PaymentMethod;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final OrderRepository orderRepository;

    public Order save(User user, List<Item> items) {
        OrderSaveRequestDto saveRequestDto = getOrderSaveRequestDto(user, items);
        Order order = Order.create(saveRequestDto, user, Delivery.create(saveRequestDto));
        List<OrderItemSaveRequestDto> orderItemSaveRequestDtos = saveRequestDto.getOrderItemSaveRequestDtos();
        items.forEach(item -> orderItemSaveRequestDtos.stream().filter(dto -> dto.getId().equals(item.getId()))
                .findFirst().ifPresent(dto -> OrderItem.create(dto, order, item)));

        return orderRepository.save(order);

    }

    public Page<Order> findByUser(User user) {
        return orderRepository.findByUserIdWithOrderItemsAndDelivery(user.getId(), PageRequest.of(0, 5));
    }

    public OrderSaveRequestDto getOrderSaveRequestDto(User user, List<Item> items) {
        OrderSaveRequestDto dto = new OrderSaveRequestDto();
        dto.setReceiver(user.getName());
        dto.setPhoneNum1(user.getPhoneNumber().getPhoneNum1());
        dto.setPhoneNum1(user.getPhoneNumber().getPhoneNum2());
        dto.setPhoneNum1(user.getPhoneNumber().getPhoneNum3());
        dto.setUsedPoints(0);
        dto.setDeliveryPrice(0);
        dto.setFinalDiscountPrice(calcFinalDiscountPrice(items, dto.getUsedPoints()));
        dto.setFinalPrice(calcFinalPrice(items, dto.getUsedPoints(), dto.getDeliveryPrice()));
        dto.setPaymentMethod(PaymentMethod.CREDIT_CARD.name());
        List<OrderItemSaveRequestDto> orderItemSaveRequestDtos = items.stream().map(item -> {
            OrderItemSaveRequestDto itemSaveRequestDto = new OrderItemSaveRequestDto();
            itemSaveRequestDto.setId(item.getId());
            itemSaveRequestDto.setItemPrice(item.getPrice());
            itemSaveRequestDto.setItemDiscountPrice(item.getDiscountPrice());
            itemSaveRequestDto.setItemQuantity(1);
            return itemSaveRequestDto;
        }).collect(Collectors.toList());

        AddressRequestDto addressRequestDto = new AddressRequestDto();
        addressRequestDto.setMainAddress("mainAddress");
        addressRequestDto.setDetailAddress("detailAddress");
        addressRequestDto.setZipCode("12345");
        addressRequestDto.setRegisterDefaultAddress(false);
        addressRequestDto.setRegisterNewAddress(false);
        addressRequestDto.setUseExistingAddress(false);
        addressRequestDto.setExistingAddressId(null);

        dto.setAddressRequestDto(addressRequestDto);
        dto.setOrderItemSaveRequestDtos(orderItemSaveRequestDtos);

        return dto;

    }

    private int calcFinalPrice(List<Item> items, Integer usedPoints, Integer deliveryPrice) {
        int finalPrice = 0;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            finalPrice += item.getPrice();
            finalPrice -= item.getDiscountPrice();
        }

        finalPrice -= usedPoints;
        finalPrice += deliveryPrice;

        return finalPrice;
    }

    private int calcFinalDiscountPrice(List<Item> items, Integer usedPoints) {

        int finalDiscountPrice = 0;

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            finalDiscountPrice += item.getDiscountPrice();
        }

        finalDiscountPrice += usedPoints;
        return finalDiscountPrice;

    }

}
