package com.rainyheaven.nature.app.domain.item;


import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemDetailResponseDto;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import com.rainyheaven.nature.core.domain.itemlike.ItemLikeService;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.qna.QnaService;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaResponseDto;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.domain.review.Review;
import com.rainyheaven.nature.core.domain.review.ReviewService;
import com.rainyheaven.nature.core.domain.review.dto.app.ReviewResponseDto;
import com.rainyheaven.nature.core.domain.user.TokenUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemLikeService itemLikeService;
    private final QnaService qnaService;
    private final ReviewService reviewService;

    private static final String ALL = "ALL";

    @Value("${src-prefix}")
    private String imgSrcPrefix;

    @GetMapping
    public ResponseEntity<?> findAll(
            Pageable pageable,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<Long> ids) {

        if (StringUtils.isNotEmpty(category) && !StringUtils.equals(category, ALL)) {

            Page<ItemSimpleResponseDto> itemSimpleResponseDtos = itemService.findAllByCategory(pageable, category)
                    .map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix));

            return ResponseEntity.ok(itemSimpleResponseDtos);
        }

        if (ids != null && !ids.isEmpty()) {
            List<Item> items = itemService.findByIds(ids);
            List<ItemSimpleResponseDto> ItemSimpleResponseDtos = items.stream()
                            .map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix))
                            .collect(Collectors.toList());

            return ResponseEntity.ok(ItemSimpleResponseDtos);

        }

        Page<Item> items = itemService.findAll(pageable);
        return ResponseEntity.ok(items.map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailResponseDto> get(@PathVariable Long id) {
        Item item = itemService.findByIdWithImages(id);
        return ResponseEntity.ok(new ItemDetailResponseDto(item, imgSrcPrefix));
    }

    @PostMapping("/{id}/item-likes")
    public ResponseEntity<Void> saveItemLike(@PathVariable Long id, @AuthenticationPrincipal TokenUser tokenUser) {
        itemService.addItemLike(id, tokenUser.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/item-likes")
    public ResponseEntity<Void> deleteItemLike(@PathVariable Long id, @AuthenticationPrincipal TokenUser tokenUser) {
        itemLikeService.deleteByItemAndUser(id, tokenUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/qnas")
    public ResponseEntity<Page<QnaResponseDto>> getQnas(
            @PathVariable Long id,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Qna> qnaPage = qnaService.pageByItem(id, pageable);
        Page<QnaResponseDto> qnaResponseDtos = qnaPage.map(QnaResponseDto::new);

        return ResponseEntity.ok(qnaResponseDtos);

    }

    @PostMapping("/{id}/qnas")
    public ResponseEntity<Void> addQna(
            @PathVariable Long id,
            @RequestBody QnaSaveRequestDto qnaSaveRequestDto,
            @AuthenticationPrincipal TokenUser tokenUser) {

        itemService.addQna(qnaSaveRequestDto, id, tokenUser.getId());
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getReviews(
            @PathVariable Long id,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Review> reviewPage = reviewService.getPageByItem(id, pageable);
        Page<ReviewResponseDto> reviewPageMap = reviewPage.map(review -> {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review, imgSrcPrefix);
            reviewResponseDto.setWriter(review.getUser().getName());
            return reviewResponseDto;
        });

        return ResponseEntity.ok(reviewPageMap);

    }

}
