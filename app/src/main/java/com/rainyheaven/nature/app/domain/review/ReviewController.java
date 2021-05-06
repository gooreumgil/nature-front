package com.rainyheaven.nature.app.domain.review;

import com.rainyheaven.nature.core.domain.orderitem.OrderItemService;
import com.rainyheaven.nature.core.domain.review.ReviewService;
import com.rainyheaven.nature.core.domain.review.dto.app.ReviewSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.exception.ReviewValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewValidator reviewValidator;

    @PostMapping
    public ResponseEntity<Void> save(@ModelAttribute @Valid ReviewSaveRequestDto reviewSaveRequestDto,
                                     @AuthenticationPrincipal TokenUser tokenUser,
                                     @RequestParam Long itemId, @RequestParam Long orderItemId) {

        reviewValidator.saveValidate(reviewSaveRequestDto);
        reviewService.save(reviewSaveRequestDto, tokenUser.getId(), itemId, orderItemId);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/{id}/review-likes")
    public ResponseEntity<Void> saveLike(@PathVariable Long id, @AuthenticationPrincipal TokenUser tokenUser) {

        reviewService.addLike(id, tokenUser.getId());
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}/review-likes")
    public ResponseEntity<Void> cancelLike(@PathVariable Long id, @AuthenticationPrincipal TokenUser tokenUser) {
        reviewService.deleteLike(id, tokenUser.getId());
        return ResponseEntity.ok().build();

    }


}
