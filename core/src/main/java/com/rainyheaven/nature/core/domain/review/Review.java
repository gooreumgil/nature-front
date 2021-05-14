package com.rainyheaven.nature.core.domain.review;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import com.rainyheaven.nature.core.domain.reviewlike.ReviewLike;
import com.rainyheaven.nature.core.domain.reviewimage.ReviewImage;
import com.rainyheaven.nature.core.domain.user.User;
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
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;
    private int likesCount;
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();


    public static Review create(ReviewSaveRequestDto reviewSaveRequestDto, Item item, User user) {
        Review review = new Review();
        review.content = reviewSaveRequestDto.getContent().trim();
        review.rating = reviewSaveRequestDto.getRating();
        review.setItem(item);
        review.setUser(user);
        return review;
    }

    // 연관관계 편의 메소드
    private void setItem(Item item) {
        this.item = item;
        item.addReview(this);
    }

    // 연관관계 편의 메소드
    private void setUser(User user) {
        this.user = user;
        user.addReview(this);
    }

    // 이미지 추가
    public void addAllReviewImages(List<ReviewImage> reviewImages) {
        this.reviewImages.addAll(reviewImages);
        reviewImages.forEach(reviewImage -> reviewImage.setReview(this));

    }

    // 연관관계 편의 메소드
    public void addReviewLike(ReviewLike reviewLike) {
        this.reviewLikes.add(reviewLike);
        this.likesCount++;
    }

    // 좋아요 취소시 likesCount 빼기
    public void minusLikesCount() {
        this.likesCount--;
    }
}
