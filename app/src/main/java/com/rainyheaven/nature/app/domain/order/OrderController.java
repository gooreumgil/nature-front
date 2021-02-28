package com.rainyheaven.nature.app.domain.order;

import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.OrderService;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderResponseDto;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> get(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(new OrderResponseDto(order));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> pageByUser(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal TokenUser tokenUser) {

        Page<Order> orderPages = orderService.findByUserId(tokenUser.getId(), pageable);
        return ResponseEntity.ok(orderPages.map(OrderResponseDto::new));

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderSaveRequestDto orderSaveRequestDto, @AuthenticationPrincipal TokenUser tokenUser) throws URISyntaxException {
        Order saveOrder = orderService.save(orderSaveRequestDto, tokenUser.getId());
        URI uri = new URI("/orders/" + saveOrder.getId());
        return ResponseEntity.created(uri).build();
    }


}
