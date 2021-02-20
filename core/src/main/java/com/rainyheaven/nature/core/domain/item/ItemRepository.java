package com.rainyheaven.nature.core.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select i from Item i join fetch i.itemSrcs", countQuery = "select count (i) from Item i")
    Page<Item> findAllWithItemSrc(Pageable pageable);

}
