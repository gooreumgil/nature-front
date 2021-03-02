package com.rainyheaven.nature.core.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    int countAllByUserId(Long userId);
}
