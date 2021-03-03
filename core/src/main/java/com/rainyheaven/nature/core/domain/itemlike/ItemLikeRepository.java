package com.rainyheaven.nature.core.domain.itemlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    boolean existsByItemIdAndUserId(Long itemId, Long userId);

    @Modifying
    void deleteByItemIdAndUserId(Long itemId, Long userId);
}
