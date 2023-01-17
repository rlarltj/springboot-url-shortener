package com.example.urlshortener.global.exception;

public class NoEncoderFoundException extends RuntimeException{
    public NoEncoderFoundException() {
    }

    public NoEncoderFoundException(String message) {
        super(message);
    }
}
