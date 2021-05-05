package com.rainyheaven.nature.core.domain.qna;

import com.rainyheaven.nature.core.domain.qna.dto.app.QnaSaveRequestDto;
import com.rainyheaven.nature.core.exception.QnaException;
import com.rainyheaven.nature.core.exception.QnaExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class QnaValidator {

    public void saveValidate(QnaSaveRequestDto qnaSaveRequestDto) {

        String content = qnaSaveRequestDto.getContent();

        if (!StringUtils.hasText(content))
            throw new QnaException(QnaExceptionType.CONTENT_NULL);

        if (content.trim().length() > 500)
            throw new QnaException(QnaExceptionType.CONTENT_LENGTH_NOT_MATCHED);
    }

}
