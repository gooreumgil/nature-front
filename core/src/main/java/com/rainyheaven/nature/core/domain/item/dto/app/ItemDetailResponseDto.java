package com.rainyheaven.nature.core.domain.item.dto.app;

import com.rainyheaven.nature.core.domain.categoryitem.CategoryItem;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.itemimage.ImgType;
import com.rainyheaven.nature.core.domain.itemimage.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Predicate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailResponseDto {

    private Long id;
    private String category;
    private String nameKor;
    private String nameEng;
    private String description;
    private Integer price;
    private Integer discountPrice;
    private Integer capacity;
    private Integer savePoints;

    private String mainImgPath;
    private String detailImgPath;

    public ItemDetailResponseDto(Item item) {

        this.id = item.getId();
        item.getCategoryItems().
                stream().filter(CategoryItem::isMain)
                .findFirst().ifPresent(categoryItem -> this.category = categoryItem.getCategoryName());

        this.nameKor = item.getNameKor();
        this.nameEng = item.getNameEng();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.discountPrice = item.getDiscountPrice();
        this.capacity = item.getCapacity();

        item.getItemImages()
                .stream().filter(filterImgType(ImgType.MAIN))
                .findFirst().ifPresent(itemImage -> this.mainImgPath = item.getMainImgPath());

        item.getItemImages()
                .stream().filter(filterImgType(ImgType.DETAIL))
                .findFirst().ifPresent(itemImage -> this.detailImgPath = itemImage.getUrl());

    }

    private Predicate<ItemImage> filterImgType(ImgType imgType) {
        return (itemImage) -> itemImage.getImgType().equals(imgType);
    }
}
