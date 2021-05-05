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

    @NotBlank
    @Size(min = 10, max = 500)
    private String content;
    private boolean isSecret = true;

}
