package com.rainyheaven.nature.app.domain.review;

import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping
    public ResponseEntity save(@RequestBody ReviewSaveRequestDto reviewSaveRequestDto, @AuthenticationPrincipal TokenUser tokenUser) {

        return null;

    }

}
