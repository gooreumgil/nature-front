package com.rainyheaven.nature.core.domain.review.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSaveRequestDto {

    private int rating;
    private String content;
    private List<MultipartFile> files = new ArrayList<>();


}
