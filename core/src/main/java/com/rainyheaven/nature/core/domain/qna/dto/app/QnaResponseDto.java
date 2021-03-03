package com.rainyheaven.nature.core.domain.qna.dto.app;

import com.rainyheaven.nature.core.domain.qna.Qna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaResponseDto {

    private Long id;
    private String content;
    private Boolean isSecret;
    private String status;
    private LocalDateTime wroteAt;

    public QnaResponseDto(Qna qna) {
        this.id = qna.getId();
        this.content = qna.getContent();
        this.isSecret = qna.isSecret();
        this.status = qna.getQnaStatus().name();
        this.wroteAt = qna.getCreatedDate();
    }
}
