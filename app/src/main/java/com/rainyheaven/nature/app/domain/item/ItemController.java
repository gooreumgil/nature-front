package com.rainyheaven.nature.app.domain.item;


import com.rainyheaven.nature.core.domain.categoryitem.CategoryItemService;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemDetailResponseDto;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

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
            List<ItemSimpleResponseDto> ItemSimpleResponseDtos =
                    items.stream().map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix)).collect(Collectors.toList());

            return ResponseEntity.ok(ItemSimpleResponseDtos);

        }

        Page<Item> items = itemService.findAll(pageable);
        return ResponseEntity.ok(items.map(item -> new ItemSimpleResponseDto(item, imgSrcPrefix)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailResponseDto> get(@PathVariable Long id) {
        Item item = itemService.findById(id);
        return ResponseEntity.ok(new ItemDetailResponseDto(item, imgSrcPrefix));
    }

}
