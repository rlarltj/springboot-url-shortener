package com.example.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final int statusCode;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDatetime;

    private ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.serverDatetime = LocalDateTime.now();
    }

    public static ErrorResponse fail(String message, int statusCode) {
        return new ErrorResponse(message, statusCode);
    }
}
