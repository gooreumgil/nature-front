package com.rainyheaven.nature.core.domain.qna.dto.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaSaveRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 10, max = 500, message = "10 ~ 500자로 입력해주세요.")
    private String content;

    private boolean isSecret = true;

}
