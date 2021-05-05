package com.rainyheaven.nature.app.domain.itemlike;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.itemlike.ItemLikeRepository;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemLikeFactory {

    private final ItemLikeRepository itemLikeRepository;

    public ItemLike save(Item item, User user) {
        return itemLikeRepository.save(ItemLike.create(item, user));
    }

    public Page<ItemLike> find(User user) {
        return itemLikeRepository.findByUserIdWithItem(user.getId(), PageRequest.of(0, 10));
    }

}
