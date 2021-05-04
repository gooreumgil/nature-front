package com.rainyheaven.nature.core.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidErrorResponseDto {

    private String field;
    private String message;
    private String invalidValue;

}
