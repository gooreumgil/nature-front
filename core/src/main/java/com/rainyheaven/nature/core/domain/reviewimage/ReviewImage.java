package com.rainyheaven.nature.core.domain.reviewimage;

import com.rainyheaven.nature.core.domain.base.BaseTimeEntity;
import com.rainyheaven.nature.core.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_src_id")
    private Long id;
    private String s3Key;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public static ReviewImage create(String key) {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.s3Key = key;
        return reviewImage;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
