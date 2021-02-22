package com.rainyheaven.nature.core.domain.item.dto.app;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.itemsrc.ImgType;
import com.rainyheaven.nature.core.domain.itemsrc.ItemSrc;
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
    private String imgSrcPath;
    private String description;

    public ItemResponseDto(Item item, String srcPrefix) {
        this.nameKor = item.getNameKor();
        this.nameEng = item.getNameEng();
        this.price = item.getPrice();
        this.discountPrice = item.getDiscountPrice();

        ItemSrc itemSrc = item.getItemSrcs().stream()
                .filter(src -> src.getImgType().equals(ImgType.MAIN))
                .findFirst().get();

        this.imgSrcPath = srcPrefix + itemSrc.getS3Key();
        this.description = item.getDescription();
    }
}
