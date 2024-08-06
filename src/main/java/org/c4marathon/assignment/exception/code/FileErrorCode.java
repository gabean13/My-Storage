package org.c4marathon.assignment.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode{
    FILE_EMPTY_ERROR(HttpStatus.BAD_REQUEST, 400, "파일을 업로드해주세요."),
    FILE_EXTENSION_ERROR(HttpStatus.BAD_REQUEST, 400, "지원하지 않는 확장자입니다."),
    FILE_SIZE_EXCEEDED_ERROR(HttpStatus.BAD_REQUEST, 400, "한번에 파일은 1024MB까지 업로드가능합니다."),;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
