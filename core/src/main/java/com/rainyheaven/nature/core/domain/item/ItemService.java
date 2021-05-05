package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
import com.rainyheaven.nature.core.exception.ItemException;
import com.rainyheaven.nature.core.exception.ItemExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemException(ItemExceptionType.NOT_EXIST_ITEM));
    }

    public Item findByIdWithImages(Long id) {
        return itemRepository.findByIdWithImages(id).orElseThrow(() -> new ItemException(ItemExceptionType.NOT_EXIST_ITEM));
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

    public Page<Item> search(String keyword, Pageable pageable) {
        return itemRepository.search(keyword, pageable);

    }

    @Transactional
    public void addItemLike(Long id, Long userId) {
        Item item = findById(id);
        User user = userService.findById(userId);
        ItemLike.create(item, user);
        item.plusLikesCount();
        itemRepository.save(item);
    }

    @Transactional
    public void addQna(QnaSaveRequestDto qnaSaveRequestDto, Long id, Long userId) {
        Item item = findById(id);
        User user = userService.findById(userId);
        Qna.create(qnaSaveRequestDto, item, user);
        itemRepository.save(item);
    }
}
