package org.c4marathon.assignment.controller;

import org.c4marathon.assignment.dto.ExceptionDto;
import org.c4marathon.assignment.exception.FileEmptyException;
import org.c4marathon.assignment.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({UserNotFoundException.class, FileEmptyException.class})
    public ResponseEntity<ExceptionDto> notFoundException(Exception ex){
        System.out.println( );
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
    }
}
