package com.rainyheaven.nature.core.domain.reviewlike;

import com.rainyheaven.nature.core.domain.review.Review;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static ReviewLike create(Review review, User user) {
        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setReview(review);
        reviewLike.setUser(user);
        return reviewLike;
    }

    // 연관관계 편의 메소드
    private void setReview(Review review) {
        this.review = review;
        review.addReviewLike(this);
    }

    // 연관관계 편의 메소드
    private void setUser(User user) {
        this.user = user;
    }
}
