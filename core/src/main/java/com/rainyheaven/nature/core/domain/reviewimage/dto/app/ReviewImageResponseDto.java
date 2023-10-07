package com.rainyheaven.nature.core.domain.reviewimage.dto.app;

import com.rainyheaven.nature.core.domain.reviewimage.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImageResponseDto {

    private Long id;
    private String s3Key;
    private String url;

    public ReviewImageResponseDto(ReviewImage reviewImage) {
        this.id = reviewImage.getId();
        this.s3Key = reviewImage.getS3Key();
        this.url = reviewImage.getUrl();
    }
}
