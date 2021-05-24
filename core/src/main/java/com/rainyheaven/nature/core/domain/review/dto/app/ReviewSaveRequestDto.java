package com.rainyheaven.nature.core.domain.review.dto.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSaveRequestDto {

    @Min(value = 1, message = "평점은 1점부터 가능합니다.")
    @Max(value = 5, message = "평점은 5점까지 가능합니다.")
    private int rating;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 10, max = 500, message = "리뷰내용은 10 ~ 500자로 입력해주세요.")
    private String content;

    private List<MultipartFile> files = new ArrayList<>();

    @JsonIgnore
    @AssertTrue(message = "이미지는 3장까지만 가능합니다.")
    public boolean isFilesLengthValid() {
        return files.size() <= 3;
    }


}
