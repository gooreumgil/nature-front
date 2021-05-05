package com.rainyheaven.nature.core.domain.itemlike;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    boolean existsByItemIdAndUserId(Long itemId, Long userId);

    @Query(value = "select il from ItemLike il join fetch il.item where il.user.id = :userId",
            countQuery = "select count (il) from ItemLike il where il.user.id = :userId")
    Page<ItemLike> findByUserIdWithItem(@Param("userId") Long userId, Pageable pageable);

    @Modifying
    void deleteByItemIdAndUserId(Long itemId, Long userId);

    @Modifying
    void deleteByIdInAndUserId(List<Long> ids, Long userId);


}
