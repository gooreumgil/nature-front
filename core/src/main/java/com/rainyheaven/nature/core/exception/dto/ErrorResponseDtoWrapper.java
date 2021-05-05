package com.rainyheaven.nature.core.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDtoWrapper {

    String statusCode;
    String requestUrl;
    String type;
    String message;
    String resultCode;

    List<ErrorResponseDto> errorList;

}
