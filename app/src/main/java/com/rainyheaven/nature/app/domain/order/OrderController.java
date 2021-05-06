package com.rainyheaven.nature.app.domain.order;

import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.OrderService;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderResponseDto;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Value("${src-prefix}")
    private String imgSrcPrefix;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> get(@PathVariable Long id) {
        Order order = orderService.findById(id);
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);
        orderResponseDto.addAllOrderItems(order.getOrderItems(), imgSrcPrefix);

        return ResponseEntity.ok(orderResponseDto);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid OrderSaveRequestDto orderSaveRequestDto, @AuthenticationPrincipal TokenUser tokenUser) throws URISyntaxException {
        Order saveOrder = orderService.save(orderSaveRequestDto, tokenUser.getId());
        return ResponseEntity.ok(saveOrder.getId());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> confirm(@PathVariable Long id, @AuthenticationPrincipal TokenUser tokenUser) {
        orderService.confirm(id, tokenUser.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal TokenUser tokenUser) {
        orderService.delete(id, tokenUser.getId());
        return ResponseEntity.ok().build();
    }

}
