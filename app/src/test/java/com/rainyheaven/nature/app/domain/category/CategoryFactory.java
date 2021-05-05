package com.rainyheaven.nature.app.domain.category;

import com.rainyheaven.nature.core.domain.category.Category;
import com.rainyheaven.nature.core.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryRepository categoryRepository;

    public Category save(String name) {
        return categoryRepository.save(Category.create(name));
    }

}
