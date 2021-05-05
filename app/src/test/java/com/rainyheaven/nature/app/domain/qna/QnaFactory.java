package com.rainyheaven.nature.app.domain.qna;

import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.qna.Qna;
import com.rainyheaven.nature.core.domain.qna.QnaRepository;
import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QnaFactory {

    private final QnaRepository qnaRepository;

    public Qna save(User user, Item item, String content, boolean isSecret) {
        QnaSaveRequestDto qnaSaveRequestDto = new QnaSaveRequestDto(content, isSecret);
        Qna qna = Qna.create(qnaSaveRequestDto, item, user);
        return qnaRepository.save(qna);
    }

}
