package org.c4marathon.assignment.exception.code;

import org.springframework.http.HttpStatus;
public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    int getCode();
    String getMessage();
}
