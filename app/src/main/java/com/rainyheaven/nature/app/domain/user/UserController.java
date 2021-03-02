package com.rainyheaven.nature.app.domain.user;

import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.OrderService;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderResponseDto;
import com.rainyheaven.nature.core.domain.review.ReviewService;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.domain.user.dto.app.UserResponseDto;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AES256Util aes256Util;
    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<UserResponseDto> get(@AuthenticationPrincipal TokenUser tokenUser) {
        User user = userService.findByIdWithAddress(tokenUser.getId());
        return ResponseEntity.ok(new UserResponseDto(user, aes256Util.decode(user.getEmail())));
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        userService.save(userSaveRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponseDto>> getOrderPages(
            @AuthenticationPrincipal TokenUser tokenUser,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Order> orderPages = orderService.findByUserId(tokenUser.getId(), pageable);
        return ResponseEntity.ok(orderPages.map(OrderResponseDto::new));

    }

    @GetMapping("/count/orders/{deliveryStatus}")
    public ResponseEntity<Integer> getTotalByDeliveryStatus(@PathVariable String deliveryStatus, @AuthenticationPrincipal TokenUser tokenUser) {
        return ResponseEntity.ok(orderService.countAllByUserAndDeliveryStatus(tokenUser.getId(), deliveryStatus));
    }

    @GetMapping("/count/reviews")
    public ResponseEntity<Integer> getReviews(@AuthenticationPrincipal TokenUser tokenUser) {
        int totalUserReviews = reviewService.getTotalUserReviews(tokenUser.getId());
        return ResponseEntity.ok(totalUserReviews);

    }

}
