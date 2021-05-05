package com.rainyheaven.nature.core.domain.item.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequestDto {

    private String nameKor;
    private String nameEng;
    private String mainImgPath;
    private int price;
    private int discountPrice;
    private int stockQuantity;
    private String description;
    private int capacity;

}
