package com.rainyheaven.nature.core.domain.orderitem.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemSaveRequestDto {

    private Long id;
    private Integer itemPrice;
    private Integer itemDiscountPrice;
    private Integer itemQuantity;

}
