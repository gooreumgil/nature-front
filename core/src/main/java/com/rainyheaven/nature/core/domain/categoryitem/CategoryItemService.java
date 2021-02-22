package com.rainyheaven.nature.core.domain.categoryitem;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryItemService {

    private final CategoryItemRepository categoryItemRepository;

    public Page<CategoryItem> findAllByCategoryName(Pageable pageable, String categoryName) {
        return categoryItemRepository.findAllByCategoryName(pageable, categoryName);
    }

}
