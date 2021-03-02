package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.categoryitem.CategoryItemService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findById(Long id) {
        return itemRepository.findByIdWithSrcs(id).orElseThrow(RuntimeException::new);
    }

    public List<Item> findByIds(List<Long> ids) {
        return itemRepository.findByIdIn(ids);
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Page<Item> findAllByCategory(Pageable pageable, String category) {
        return itemRepository.findAllByCategory(pageable, category);
    }


}
