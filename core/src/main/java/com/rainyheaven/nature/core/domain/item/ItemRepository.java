package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.itemsrc.ImgType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i join fetch i.itemSrcs isrc where i.id = :id and isrc.imgType = com.rainyheaven.nature.core.domain.itemsrc.ImgType.MAIN")
    Optional<Item> findByIdWithMainSrc(@Param("id") Long id);

    @Query(value = "select i from Item i join fetch i.itemSrcs isrc where isrc.imgType = com.rainyheaven.nature.core.domain.itemsrc.ImgType.MAIN", countQuery = "select count (i) from Item i")
    Page<Item> findAllWithItemMainSrc(Pageable pageable);

    @Query(value = "select i from Item i join fetch i.itemSrcs isrc left join i.categoryItems ci where ci.categoryName = :categoryName and isrc.imgType = com.rainyheaven.nature.core.domain.itemsrc.ImgType.MAIN",
            countQuery = "select count (i) from Item i left join i.categoryItems ci where ci.categoryName = :categoryName")
    Page<Item> findAllByCategory(Pageable pageable, @Param("categoryName") String category);

    @Query("select i from Item i join fetch i.itemSrcs isrc where i.id in :ids and isrc.imgType = com.rainyheaven.nature.core.domain.itemsrc.ImgType.MAIN")
    List<Item> findByIdInWithMainSrc(@Param("ids") List<Long> ids);



}
