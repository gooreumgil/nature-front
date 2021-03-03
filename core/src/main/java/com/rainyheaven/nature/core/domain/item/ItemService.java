package com.rainyheaven.nature.core.domain.item;

import com.rainyheaven.nature.core.domain.itemlike.ItemLike;
import com.rainyheaven.nature.core.domain.itemlike.ItemLikeService;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import com.rainyheaven.nature.core.domain.user.UserService;
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

    private Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Item findByIdWithSrcs(Long id) {
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

    @Transactional
    public void addItemLike(Long id, Long userId) {
        Item item = findById(id);
        User user = userService.findById(userId);
        ItemLike.create(item, user);
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
