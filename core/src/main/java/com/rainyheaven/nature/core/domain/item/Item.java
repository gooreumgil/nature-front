package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.categoryitem.CategoryItem;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.itemsrc.ItemSrc;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.qna.Qna;
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
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String nameKor;
    private String nameEng;
    private String mainSrcPath;
    private int price;
    private int discountPrice;
    private int stockQuantity;
    private int likesCount;
    private String description;
    private int capacity;
    private int savePoints;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSrc> itemSrcs = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemLike> itemLikes = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Qna> qnaList = new ArrayList<>();
    
    // 연관관계 편의 메소드
    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    // 연관관계 편의 메소드
    public void addItemLike(ItemLike itemLike) {
        this.itemLikes.add(itemLike);
    }

    // 연관관계 편의 메소드
    public void addQna(Qna qna) {
        this.qnaList.add(qna);
    }
}
