package com.rainyheaven.nature.core.domain.categoryitem;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.category.Category;
import com.rainyheaven.nature.core.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_item_id")
    private Long id;

    private String categoryName;
    private boolean isMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static CategoryItem create(boolean isMain) {
        CategoryItem categoryItem = new CategoryItem();
        categoryItem.isMain = isMain;
        return categoryItem;
    }
    
    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addCategoryItem(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        this.categoryName = category.getName();
        category.addCategoryItem(this);
    }

}
