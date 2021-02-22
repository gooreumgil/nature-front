package com.rainyheaven.nature.app.domain.item;


import com.rainyheaven.nature.core.domain.categoryitem.CategoryItemService;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryItemService categoryItemService;

    @Value("${src-prefix}")
    private String imgSrcPrefix;

    @GetMapping
    public ResponseEntity<Page<ItemResponseDto>> findAll(Pageable pageable, @RequestParam(required = false) String category) {

        if (StringUtils.isNotEmpty(category) && !StringUtils.equals(category, "ALL")) {
            return ResponseEntity.ok(
                itemService.findAllByCategory(pageable, category)
                        .map(item -> new ItemResponseDto(item, imgSrcPrefix))
            );
        }

        Page<Item> items = itemService.findAll(pageable);
        return ResponseEntity.ok(items.map(item -> new ItemResponseDto(item, imgSrcPrefix)));
    }

}
