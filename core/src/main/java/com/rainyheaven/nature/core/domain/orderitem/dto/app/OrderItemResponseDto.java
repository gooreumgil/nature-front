package com.rainyheaven.nature.core.domain.orderitem.dto.app;

import com.rainyheaven.nature.core.domain.itemsrc.ImgType;
import com.rainyheaven.nature.core.domain.order.Order;
import com.rainyheaven.nature.core.domain.order.dto.app.OrderResponseDto;
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
    private String mainSrcPath;
    private LocalDateTime orderedAt;

    public OrderItemResponseDto(OrderItem orderItem, String srcPrefix) {
        this.id = orderItem.getId();
        this.itemId = orderItem.getId();
        this.itemNameKor = orderItem.getItem().getNameKor();
        this.itemNameEng = orderItem.getItem().getNameEng();
        this.itemPrice = orderItem.getItemPrice();
        this.itemDiscountPrice = orderItem.getItemDiscountPrice();
        this.itemQuantity = orderItem.getItemQuantity();
        this.mainSrcPath = srcPrefix + orderItem.getItem().getMainSrcPath();
    }
}
