package com.rainyheaven.nature.core.domain.qna.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaSaveRequestDto {

    private String content;
    private boolean isSecret = true;

}
