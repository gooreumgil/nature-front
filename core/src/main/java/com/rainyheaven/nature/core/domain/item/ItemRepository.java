package com.rainyheaven.nature.core.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i join fetch i.itemImages where i.id = :id")
    Optional<Item> findByIdWithImages(@Param("id") Long id);

    @Query(value = "select i from Item i left join i.categoryItems ci where ci.categoryName = :categoryName",
            countQuery = "select count (i) from Item i left join i.categoryItems ci where ci.categoryName = :categoryName")
    Page<Item> findAllByCategory(Pageable pageable, @Param("categoryName") String category);

    List<Item> findByIdIn(@Param("ids") List<Long> ids);




}
