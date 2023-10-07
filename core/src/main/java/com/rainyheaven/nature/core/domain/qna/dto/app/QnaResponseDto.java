package com.rainyheaven.nature.core.domain.qna.dto.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rainyheaven.nature.core.domain.item.Item;
import com.rainyheaven.nature.core.domain.item.dto.app.ItemSimpleResponseDto;
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
    private String writer;
    private String content;

    @JsonProperty("isSecret")
    private boolean isSecret;

    @JsonProperty("isOwn")
    private boolean isOwn = false;

    private String status;
    private LocalDateTime wroteAt;
    private ItemSimpleResponseDto itemResponseDto;

    public QnaResponseDto(Qna qna) {
        this.id = qna.getId();
        this.writer = qna.getUser().getName();
        this.content = qna.getContent();
        this.isSecret = qna.isSecret();
        this.status = qna.getQnaStatus().name();
        this.wroteAt = qna.getCreatedDate();
        this.itemResponseDto = new ItemSimpleResponseDto(qna.getItem());
    }

    public QnaResponseDto(Qna qna, Long userId) {
        this.id = qna.getId();
        this.writer = qna.getUser().getName();
        this.content = qna.getContent();
        this.isSecret = qna.isSecret();
        this.isOwn = isWriter(qna.getUser().getId(), userId);
        this.status = qna.getQnaStatus().name();
        this.wroteAt = qna.getCreatedDate();
    }

    private boolean isWriter(Long writerId, Long userId) {
        return writerId.equals(userId);
    }


}
