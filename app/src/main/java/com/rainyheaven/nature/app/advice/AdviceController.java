package com.rainyheaven.nature.app.advice;

import com.rainyheaven.nature.app.domain.user.UserController;
import com.rainyheaven.nature.core.exception.DomainException;
import com.rainyheaven.nature.core.exception.dto.ExceptionResponseDto;
import com.rainyheaven.nature.core.exception.dto.ValidErrorResponseDto;
import com.rainyheaven.nature.core.exception.dto.ValidErrorResponseDtoWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseDto> domainException(DomainException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDto(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidErrorResponseDtoWrapper> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<ValidErrorResponseDto> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {

            FieldError field = (FieldError) error;
            String fieldName = field.getField();
            String message = field.getDefaultMessage();

            Object rejectedValue = field.getRejectedValue();
            String value = null;

            if (rejectedValue != null) value = field.getRejectedValue().toString();

            ValidErrorResponseDto errorResponseDto = new ValidErrorResponseDto();
            errorResponseDto.setField(fieldName);
            errorResponseDto.setMessage(message);
            errorResponseDto.setInvalidValue(value);

            errorList.add(errorResponseDto);

        });

        ValidErrorResponseDtoWrapper errorResponseDtoWrapper = new ValidErrorResponseDtoWrapper();
        errorResponseDtoWrapper.setErrorList(errorList);
        errorResponseDtoWrapper.setMessage("");
        errorResponseDtoWrapper.setRequestUrl(request.getRequestURI());
        errorResponseDtoWrapper.setStatusCode(HttpStatus.BAD_REQUEST.name());
        errorResponseDtoWrapper.setResultCode("FAIL");

        return ResponseEntity.badRequest().body(errorResponseDtoWrapper);


    }

}
