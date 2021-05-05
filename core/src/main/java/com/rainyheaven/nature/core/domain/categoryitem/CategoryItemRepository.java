package com.rainyheaven.nature.core.domain.categoryitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {

    @Query(value = "select ci from CategoryItem ci join fetch ci.item it where ci.categoryName = :categoryName", countQuery = "select count (ci) from CategoryItem ci where ci.categoryName = :categoryName")
    Page<CategoryItem> findAllByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);

}
