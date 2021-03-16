package com.rainyheaven.nature.core.exception.dto;

import com.rainyheaven.nature.core.exception.DomainException;
import lombok.Getter;

@Getter
public class ExceptionResponseDto {

    public String type;
    public String message;

    public ExceptionResponseDto(DomainException domainException) {
        this.type = domainException.getType();
        this.message = domainException.getMessage();
    }
}
