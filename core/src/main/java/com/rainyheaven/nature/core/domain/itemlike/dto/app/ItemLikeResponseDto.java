package com.rainyheaven.nature.core.domain.itemlike.dto.app;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemLikeResponseDto {

    private Long id;
    private ItemSimpleResponseDto itemResponseDto;

    public ItemLikeResponseDto(ItemLike itemLike) {
        this.id = itemLike.getId();
        this.itemResponseDto = new ItemSimpleResponseDto(itemLike.getItem());
    }
}
