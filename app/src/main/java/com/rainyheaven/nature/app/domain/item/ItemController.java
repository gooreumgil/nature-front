package com.rainyheaven.nature.app.domain.item;


import com.rainyheaven.nature.core.domain.categoryitem.CategoryItemService;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private static final String ALL = "ALL";
    private static final String SIMPLE = "SIMPLE";
    private static final String DETAIL = "DETAIL";

    @Value("${src-prefix}")
    private String imgSrcPrefix;

    @GetMapping
    public ResponseEntity<Page<ItemSimpleResponseDto>> findAll(Pageable pageable, @RequestParam(required = false) String category) {

        if (StringUtils.isNotEmpty(category) && !StringUtils.equals(category, ALL)) {
            return ResponseEntity.ok(
                itemService.findAllByCategory(pageable, category)
                        .map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix))
            );
        }

        Page<Item> items = itemService.findAll(pageable);
        return ResponseEntity.ok(items.map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestParam String type) {

        if (StringUtils.isNotEmpty(type) && StringUtils.equals(type, SIMPLE)) {
            Item item = itemService.findByIdSimple(id);
            return ResponseEntity.ok(new ItemSimpleResponseDto(item, imgSrcPrefix));
        }

        return ResponseEntity.ok().build();

    }

}
