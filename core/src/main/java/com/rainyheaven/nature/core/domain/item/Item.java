package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.categoryitem.CategoryItem;
import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.itemimage.ItemImage;
import com.rainyheaven.nature.core.domain.orderitem.OrderItem;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String mainImgPath;
    private int price;
    private int discountPrice;
    private int stockQuantity;
    private int likesCount;
    private String description;
    private int capacity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemLike> itemLikes = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Qna> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    
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

    // item like 추가할 때 likesCount 증가
    public void plusLikesCount() {
        this.likesCount++;
    }

    // item like 삭제할 때 likesCount 감소
    public void minusLikesCount() {
        this.likesCount--;
    }

    // 연관관계 편의 메소드
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    // 주문취소시 재고 다시 추가
    public void plusStockQuantity(int itemQuantity) {
        this.stockQuantity += itemQuantity;
    }

    // 주문시 재고에서 빼기
    public void minusStockQuantity(int itemQuantity) {
        this.stockQuantity -= itemQuantity;
    }
}
