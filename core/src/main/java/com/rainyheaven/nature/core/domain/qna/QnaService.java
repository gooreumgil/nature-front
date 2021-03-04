package com.rainyheaven.nature.core.domain.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    public Page<Qna> pageByItem(Long itemId, Pageable pageable) {
        return qnaRepository.findByItemIdWithUser(itemId, pageable);
    }

    public Page<Qna> pageByUser(Long userId, Pageable pageable) {
        return qnaRepository.findByUserWithItem(userId, pageable);
    }
}
