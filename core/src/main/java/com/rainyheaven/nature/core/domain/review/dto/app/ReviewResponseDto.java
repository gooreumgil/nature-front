package com.rainyheaven.nature.core.domain.review.dto.app;

import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import com.rainyheaven.nature.core.domain.review.Review;
import com.rainyheaven.nature.core.domain.reviewimage.ReviewImage;
import com.rainyheaven.nature.core.domain.reviewimage.dto.app.ReviewImageResponseDto;
import com.rainyheaven.nature.core.domain.user.dto.app.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String content;
    private int likesCount;
    private int rating;
    private LocalDateTime wroteAt;
    private ItemSimpleResponseDto itemResponseDto;
    private List<ReviewImageResponseDto> reviewImageResponseDtos = new ArrayList<>();
    private String writer;

    public ReviewResponseDto(Review review, String srcPrefix) {
        this.id = review.getId();
        this.content = review.getContent();
        this.likesCount = review.getLikesCount();
        this.rating = review.getRating();
        this.wroteAt = review.getCreatedDate();
        this.itemResponseDto = new ItemSimpleResponseDto(review.getItem(), srcPrefix);
        List<ReviewImage> reviewImages = review.getReviewImages();
        if (!reviewImages.isEmpty()) {
            this.reviewImageResponseDtos = reviewImages.stream().map(ReviewImageResponseDto::new).collect(Collectors.toList());
        }
    }
}
