package com.rainyheaven.nature.core.domain.category;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.categoryitem.CategoryItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    List<CategoryItem> categoryItems = new ArrayList<>();

    public static Category create(String name) {
        Category category = new Category();
        category.name = name;
        category.setCreatedDate(LocalDateTime.now());
        category.setLastModifiedDate(LocalDateTime.now());
        return category;
    }

    // 연관관계 편의 메소드
    public void addCategoryItem(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
    }
}
