package com.rainyheaven.nature.core.domain.itemlike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;

    public boolean existCheck(Long itemId, Long userId) {
        return itemLikeRepository.existsByItemIdAndUserId(itemId, userId);
    }

    @Transactional
    public void deleteByItemAndUser(Long itemId, Long userId) {
        itemLikeRepository.deleteByItemIdAndUserId(itemId, userId);
    }

}
