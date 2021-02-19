package com.rainyheaven.nature.core.domain.item.dto.app;

import com.rainyheaven.nature.core.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDto {

    private String nameKor;
    private String nameEng;
    private Integer price;
    private Integer discountPrice;

    public ItemResponseDto(Item item) {
        this.nameKor = item.getNameKor();
        this.nameEng = item.getNameEng();
        this.price = item.getPrice();
        this.discountPrice = item.getDiscountPrice();
    }
}
