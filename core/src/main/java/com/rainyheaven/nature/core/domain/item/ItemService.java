package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.categoryitem.CategoryItemService;
import com.rainyheaven.nature.core.domain.itemsrc.ImgType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryItemService categoryItemService;

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAllWithItemSrc(pageable);
    }

    public Page<Item> findAllByCategory(Pageable pageable, String category) {
        return itemRepository.findAllByCategory(pageable, category);
    }

}
