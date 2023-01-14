package com.example.urlshortener.global;

import com.example.urlshortener.dto.ErrorResponse;
import com.example.urlshortener.global.exception.NoEncoderFoundException;
import com.example.urlshortener.global.exception.NotValidUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoEncoderFoundException.class)
    public ErrorResponse noEncoderFoundExceptionHandler(IllegalArgumentException e) {
        log.warn("NoEncoderFoundException 발생-> {}", e.getMessage());

        return ErrorResponse.fail(e.getMessage(), 400);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotValidUrlException.class)
    public ErrorResponse notValidUrlExceptionHandler(NotValidUrlException e) {
        log.warn("NotValidUrlException 발생-> {}", e.getMessage());

        return ErrorResponse.fail(e.getMessage(), 400);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse bindExceptionHandler(BindException e) {
        FieldError fieldError = e.getFieldError();

        String message = Objects.requireNonNull(fieldError).getDefaultMessage();

        log.warn("BindException 발생-> {}", message);

        return ErrorResponse.fail(message, 400);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse runTimeExceptionHandler(RuntimeException e) {
        String message = e.getMessage();

        log.warn("RuntimeException 발생-> {}", message);

        return ErrorResponse.fail(message, 400);
    }
}
