package com.rainyheaven.nature.app.domain.user;

import com.rainyheaven.nature.core.domain.delivery.DeliveryStatus;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.itemlike.ItemLikeService;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.OrderService;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderResponseDto;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.orderitem.OrderItemService;
import com.rainyheaven.nature.core.domain.orderitem.dto.app.OrderItemResponseDto;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.qna.QnaService;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaResponseDto;
import com.rainyheaven.nature.core.domain.review.ReviewService;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.domain.user.dto.app.UserResponseDto;
import com.rainyheaven.nature.core.domain.user.dto.app.UserSaveRequestDto;
import com.rainyheaven.nature.core.utils.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final ItemLikeService itemLikeService;
    private final QnaService qnaService;
    private final OrderItemService orderItemService;

    @Value("${src-prefix}")
    private String imgSrcPrefix;

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
        Page<OrderResponseDto> orderResponseDtoPages = orderPages.map(order -> {
            OrderResponseDto orderResponseDto = new OrderResponseDto(order);
            orderResponseDto.addAllOrderItems(order.getOrderItems(), imgSrcPrefix);
            return orderResponseDto;
        });
        return ResponseEntity.ok(orderResponseDtoPages);

    }

    @GetMapping("/count/orders/{deliveryStatus}")
    public ResponseEntity<Integer> getTotalByDeliveryStatus(
            @PathVariable String deliveryStatus,
            @AuthenticationPrincipal TokenUser tokenUser) {

        return ResponseEntity.ok(orderService.countAllByUserAndDeliveryStatus(tokenUser.getId(), deliveryStatus));
    }

    @GetMapping("/count/reviews")
    public ResponseEntity<Integer> getReviews(@AuthenticationPrincipal TokenUser tokenUser) {
        int totalUserReviews = reviewService.getTotalUserReviews(tokenUser.getId());
        return ResponseEntity.ok(totalUserReviews);

    }

    @GetMapping("/order-items")
    public ResponseEntity<Page<OrderItemResponseDto>> getOrderItems(
            @AuthenticationPrincipal TokenUser tokenUser,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<OrderItem> orderItemPage = orderItemService.pageByUserIdAndDeliveryStatus(tokenUser.getId(), DeliveryStatus.COMP, pageable);
        Page<OrderItemResponseDto> orderItemPageMap = orderItemPage.map(orderItem -> new OrderItemResponseDto(orderItem, imgSrcPrefix));

        return ResponseEntity.ok(orderItemPageMap);

    }


    @GetMapping("/item-likes")
    public ResponseEntity<Page<ItemSimpleResponseDto>> getLikeItems(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal TokenUser tokenUser) {

        Page<ItemLike> itemLikePage = itemLikeService.pageByUser(tokenUser.getId(), pageable);
        Page<ItemSimpleResponseDto> itemLikePageMap = itemLikePage
                .map(itemLike -> new ItemSimpleResponseDto(itemLike.getItem(), imgSrcPrefix));

        return ResponseEntity.ok(itemLikePageMap);

    }

    @GetMapping("/check/item-likes")
    public ResponseEntity<Boolean> checkHasItemLike(@RequestParam Long itemId, @AuthenticationPrincipal TokenUser tokenUser) {
        boolean isExist = itemLikeService.existCheck(itemId, tokenUser.getId());
        return ResponseEntity.ok(isExist);
    }

    @GetMapping("/qnas")
    public ResponseEntity<Page<QnaResponseDto>> getQnaPage(
            @AuthenticationPrincipal TokenUser tokenUser,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Qna> qnaPage = qnaService.pageByUser(tokenUser.getId(), pageable);
        Page<QnaResponseDto> qnaPageMap = qnaPage.map(qna -> new QnaResponseDto(qna, imgSrcPrefix));

        return ResponseEntity.ok(qnaPageMap);

    }

}
