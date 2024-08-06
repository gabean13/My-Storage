package org.c4marathon.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionDto {
    private HttpStatus StatusCode;
    private final int code;
    private String message;

    @Builder
    public ExceptionDto(HttpStatus statusCode, int code, String message) {
        StatusCode = statusCode;
        this.code = code;
        this.message = message;
    }
}
