package com.rainyheaven.nature.core.exception;

import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ReviewValidator {

    public void saveValidate(ReviewSaveRequestDto reviewSaveRequestDto) {
        String content = reviewSaveRequestDto.getContent();
        int rating = reviewSaveRequestDto.getRating();
        List<MultipartFile> files = reviewSaveRequestDto.getFiles();

        isValidContent(content);
        isValidRating(rating);
        isValidFiles(files);


    }

    private void isValidContent(String content) {
        if (!StringUtils.hasText(content)) {
            throw new ReviewException(ReviewExceptionType.CONTENT_NULL);
        }

        if (content.trim().length() > 500) {
            throw new ReviewException(ReviewExceptionType.CONTENT_LENGTH_NOT_MATCHED);
        }
    }

    private void isValidRating(int rating) {
        if (rating == 0) {
            throw new ReviewException(ReviewExceptionType.RATING_NULL);
        }
    }

    private void isValidFiles(List<MultipartFile> files) {
        if (files.size() > 3) {
            throw new ReviewException(ReviewExceptionType.IMAGE_FILE_LENGTH_NOT_MATCHED);
        }
    }

}
