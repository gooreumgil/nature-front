package com.rainyheaven.nature.core.domain.review.dto.app;

import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import com.rainyheaven.nature.core.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String content;
    private int likesCount;
    private int rating;
    private ItemSimpleResponseDto itemSimpleResponseDto;

    public ReviewResponseDto(Review review, String srcPrefix) {
        this.id = review.getId();
        this.content = review.getContent();
        this.likesCount = review.getLikesCount();
        this.rating = review.getRating();
        this.itemSimpleResponseDto = new ItemSimpleResponseDto(review.getItem(), srcPrefix);
    }
}
