package com.rainyheaven.nature.core.domain.reviewlike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
    void deleteByReviewIdAndUserId(Long reviewId, Long userId);

}
