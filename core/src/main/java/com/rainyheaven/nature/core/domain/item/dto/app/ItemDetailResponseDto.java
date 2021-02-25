package com.rainyheaven.nature.core.domain.item.dto.app;

import com.rainyheaven.nature.core.domain.categoryitem.CategoryItem;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.itemsrc.ImgType;
import com.rainyheaven.nature.core.domain.itemsrc.ItemSrc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

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

    private String mainSrcPath;
    private String detailSrcPath;

    public ItemDetailResponseDto(Item item, String srcPrefix) {

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
        this.savePoints = item.getSavePoints();

        item.getItemSrcs()
                .stream().filter(filterImgType(ImgType.MAIN))
                .findFirst().ifPresent(itemSrc -> this.mainSrcPath = srcPrefix + itemSrc.getS3Key());

        item.getItemSrcs()
                .stream().filter(filterImgType(ImgType.DETAIL))
                .findFirst().ifPresent(itemSrc -> this.detailSrcPath = srcPrefix + itemSrc.getS3Key());

    }

    private Predicate<ItemSrc> filterImgType(ImgType imgType) {
        return (itemSrc) -> itemSrc.getImgType().equals(imgType);
    }
}
