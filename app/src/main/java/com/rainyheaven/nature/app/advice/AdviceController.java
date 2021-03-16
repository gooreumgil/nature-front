package com.rainyheaven.nature.app.advice;

import com.rainyheaven.nature.core.exception.DomainException;
import com.rainyheaven.nature.core.exception.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseDto> domainException(DomainException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDto(e));
    }

}
