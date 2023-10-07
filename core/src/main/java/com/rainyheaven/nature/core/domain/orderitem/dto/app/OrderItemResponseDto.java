package com.rainyheaven.nature.core.domain.orderitem.dto.app;

import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {

    private Long id;
    private Long itemId;
    private String itemNameKor;
    private String itemNameEng;
    private Integer itemPrice;
    private Integer itemDiscountPrice;
    private Integer itemQuantity;
    private String mainImgPath;
    private LocalDateTime orderedAt;
    private Boolean isLeaveReview;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.itemId = orderItem.getItem().getId();
        this.itemNameKor = orderItem.getItem().getNameKor();
        this.itemNameEng = orderItem.getItem().getNameEng();
        this.itemPrice = orderItem.getItemPrice();
        this.itemDiscountPrice = orderItem.getItemDiscountPrice();
        this.itemQuantity = orderItem.getItemQuantity();
        this.mainImgPath = orderItem.getItem().getMainImgPath();
        this.isLeaveReview = orderItem.isLeaveReview();

    }
}
