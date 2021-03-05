package com.rainyheaven.nature.core.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Page<Review> getPageByUser(Long userId, Pageable pageable) {
        return reviewRepository.findAllByUserIdWithItem(userId, pageable);

    }

    public int getTotalByUser(Long userId) {
        return reviewRepository.countAllByUserId(userId);
    }

}
