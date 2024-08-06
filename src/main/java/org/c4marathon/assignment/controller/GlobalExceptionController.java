package org.c4marathon.assignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.dto.ExceptionDto;
import org.c4marathon.assignment.exception.*;
import org.c4marathon.assignment.exception.code.CommonErrorCode;
import org.c4marathon.assignment.exception.code.ErrorCode;
import org.c4marathon.assignment.exception.code.FileErrorCode;
import org.c4marathon.assignment.exception.code.UserErrorCode;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {
    protected void printLog(ErrorCode errorCode, Exception ex){
        log.error("[{}] cause: {}, message : {}", errorCode.name(), NestedExceptionUtils.getMostSpecificCause(ex), errorCode.getMessage());
    }
    protected ResponseEntity<ExceptionDto> buildExceptionDto(ErrorCode errorCode, Exception ex){
        //로그 찍기
        printLog(errorCode, ex);

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .statusCode(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(exceptionDto);
    }

    /**
     * COMMON ERROR CODE
     */
    //모든 에러 -> 하위 에러에서 못받을 때
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(Exception ex){
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return buildExceptionDto(errorCode, ex);
    }

    @ExceptionHandler(ParameterEmptyException.class)
    public ResponseEntity<ExceptionDto> parameterEmptyException(Exception ex){
        ErrorCode errorCode = CommonErrorCode.ILLEGAL_ARGUMENT_ERROR;
        return buildExceptionDto(errorCode, ex);
    }

    @ExceptionHandler(IllegalParameterException.class)
    public ResponseEntity<ExceptionDto> illegalParameterException(Exception ex){
        ErrorCode errorCode = CommonErrorCode.ILLEGAL_ARGUMENT_ERROR;
        return buildExceptionDto(errorCode, ex);
    }

    /**
     * USER ERROR CODE
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> userNotFoundException(Exception ex){
        ErrorCode errorCode = UserErrorCode.USER_NOT_FOUND_ERROR;
        return buildExceptionDto(errorCode, ex);

    }

    /**
     * FILE ERROR CODE
     */
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ExceptionDto> fileSizeExceededException(Exception ex){
        ErrorCode errorCode = FileErrorCode.FILE_SIZE_EXCEEDED_ERROR;
        return buildExceptionDto(errorCode, ex);

    }
    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<ExceptionDto> fileNotFoundException(Exception ex){
        ErrorCode errorCode = FileErrorCode.FILE_EMPTY_ERROR;
        return buildExceptionDto(errorCode, ex);
    }
    @ExceptionHandler(FileExtensionInvalidException.class)
    public ResponseEntity<ExceptionDto> fileExtensionInvalidException(Exception ex){
        ErrorCode errorCode = FileErrorCode.FILE_EXTENSION_ERROR;
        return buildExceptionDto(errorCode, ex);

    }
}

