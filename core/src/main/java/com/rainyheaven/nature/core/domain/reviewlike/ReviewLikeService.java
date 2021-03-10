package com.rainyheaven.nature.core.domain.reviewlike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;

    public boolean existByReviewAndUser(Long reviewId, Long userId) {
        return reviewLikeRepository.existsByReviewIdAndUserId(reviewId, userId);
    }

}
