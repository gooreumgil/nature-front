package com.rainyheaven.nature.core.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidErrorResponseDtoWrapper {

    String statusCode;
    String requestUrl;
    String code;
    String message;
    String resultCode;

    List<ValidErrorResponseDto> errorList;

}
