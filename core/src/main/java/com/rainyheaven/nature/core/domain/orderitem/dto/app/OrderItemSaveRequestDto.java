package com.rainyheaven.nature.core.domain.orderitem.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemSaveRequestDto {

    @NotNull(message = "상품의 아이디는 필수입니다.")
    private Long id;

    private Integer itemPrice;
    private Integer itemDiscountPrice;

    @NotNull(message = "상품의 수량을 입력해주세요.")
    @Min(value = 1, message = "상품의 수량은 최소 1개 이상이어야합니다.")
    private Integer itemQuantity;

}
