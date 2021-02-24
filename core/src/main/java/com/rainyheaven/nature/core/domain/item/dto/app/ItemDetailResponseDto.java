package com.rainyheaven.nature.core.domain.item.dto.app;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.itemsrc.ImgType;
import com.rainyheaven.nature.core.domain.itemsrc.ItemSrc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        this.category = "ALL";

        this.nameKor = item.getNameKor();
        this.nameEng = item.getNameEng();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.discountPrice = item.getDiscountPrice();
        this.capacity = item.getCapacity();
        this.savePoints = item.getSavePoints();

        ItemSrc mainSrc = item.getItemSrcs()
                .stream().filter(itemSrc -> itemSrc.getImgType().equals(ImgType.MAIN))
                .findFirst().get();

        ItemSrc detailSrc = item.getItemSrcs()
                .stream().filter(itemSrc -> itemSrc.getImgType().equals(ImgType.DETAIL))
                .findFirst().get();

        this.mainSrcPath = srcPrefix + mainSrc.getS3Key();
        this.detailSrcPath = srcPrefix + detailSrc.getS3Key();
    }
}
