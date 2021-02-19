package com.rainyheaven.nature.app.domain.item;


import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.ItemService;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Page<ItemResponseDto>> findAll(Pageable pageable) {
        Page<Item> items = itemService.findAll(pageable);
        return ResponseEntity.ok(items.map(ItemResponseDto::new));
    }

}
