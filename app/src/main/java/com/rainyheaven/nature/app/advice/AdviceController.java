package com.rainyheaven.nature.app.advice;

import com.rainyheaven.nature.core.exception.DomainException;
import com.rainyheaven.nature.core.exception.dto.ErrorResponseDto;
import com.rainyheaven.nature.core.exception.dto.ErrorResponseDtoWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponseDtoWrapper> domainException(DomainException e, HttpServletRequest request) {

        List<ErrorResponseDto> errorList = new ArrayList<>();
        ErrorResponseDtoWrapper errorResponseDtoWrapper =
                getErrorResponseDtoWrapper(request.getRequestURI(), "DomainException." + e.getType(), e.getMessage(), errorList);

        return ResponseEntity.badRequest().body(errorResponseDtoWrapper);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDtoWrapper> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<ErrorResponseDto> errorList = new ArrayList<>();
        bindingResultConvert(e.getBindingResult(), errorList);
        ErrorResponseDtoWrapper errorResponseDtoWrapper =
                getErrorResponseDtoWrapper(request.getRequestURI(), "MethodArgumentException", "", errorList);

        return ResponseEntity.badRequest().body(errorResponseDtoWrapper);


    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDtoWrapper> bindException(BindException e, HttpServletRequest request) {
        List<ErrorResponseDto> errorList = new ArrayList<>();
        bindingResultConvert(e.getBindingResult(), errorList);
        ErrorResponseDtoWrapper errorResponseDtoWrapper =
                getErrorResponseDtoWrapper(request.getRequestURI(), "BindException", "", errorList);

        return ResponseEntity.badRequest().body(errorResponseDtoWrapper);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDtoWrapper> constraintViolationException(ConstraintViolationException e, HttpServletRequest request) {

        List<ErrorResponseDto> errorList = new ArrayList<>();
        constraintViolationExceptionConvert(e, errorList);
        ErrorResponseDtoWrapper errorResponseDtoWrapper =
                getErrorResponseDtoWrapper(request.getRequestURI(), "ConstraintViolationException", "", errorList);

        return ResponseEntity.badRequest().body(errorResponseDtoWrapper);

    }

    private void constraintViolationExceptionConvert(ConstraintViolationException e, List<ErrorResponseDto> errorList) {
        e.getConstraintViolations().forEach(error ->{
            Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());

            String field = list.get(list.size() -1).getName();
            String message = error.getMessage();
            String invalidValue = error.getInvalidValue().toString();

            errorList.add(new ErrorResponseDto(field, message, invalidValue));

        });
    }


    private void bindingResultConvert(BindingResult bindingResult, List<ErrorResponseDto> errorList) {

        bindingResult.getAllErrors().forEach(error -> {

            FieldError field = (FieldError) error;
            String fieldName = field.getField();
            String message = field.getDefaultMessage();

            Object rejectedValue = field.getRejectedValue();
            String value = null;

            if (rejectedValue != null) value = field.getRejectedValue().toString();

            errorList.add(new ErrorResponseDto(fieldName, message, value));

        });
    }

    private ErrorResponseDtoWrapper getErrorResponseDtoWrapper(String requestUrl, String type, String message, List<ErrorResponseDto> errorList) {
        return new ErrorResponseDtoWrapper(HttpStatus.BAD_REQUEST.name(), requestUrl, type, message, "FAIL", errorList);
    }

}
