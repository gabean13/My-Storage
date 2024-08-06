package org.c4marathon.assignment.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 404, "존재하지 않는 사용자입니다."),
    UNAUTHORIZED_USER_ERROR(HttpStatus.UNAUTHORIZED, 403, "권한이 없는 사용자입니다."),;
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
