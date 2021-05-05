package com.rainyheaven.nature.core.domain.item.dto.app;

import com.rainyheaven.nature.core.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemSimpleResponseDto {

    private Long id;
    private String nameKor;
    private String nameEng;
    private Integer price;
    private Integer discountPrice;
    private String mainImgPath;
    private String description;

    public ItemSimpleResponseDto(Item item, String srcPrefix) {
        this.id = item.getId();
        this.nameKor = item.getNameKor();
        this.nameEng = item.getNameEng();
        this.price = item.getPrice();
        this.discountPrice = item.getDiscountPrice();
        this.mainImgPath = srcPrefix + item.getMainImgPath();
        this.description = item.getDescription();
    }
}
