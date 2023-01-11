package com.example.urlshortener.exception;

public class NoEncoderFoundException extends RuntimeException{
    public NoEncoderFoundException() {
    }

    public NoEncoderFoundException(String message) {
        super(message);
    }
}
