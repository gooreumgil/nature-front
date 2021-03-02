package com.rainyheaven.nature.core.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public int getTotalUserReviews(Long userId) {
        return reviewRepository.countAllByUserId(userId);
    }

}
