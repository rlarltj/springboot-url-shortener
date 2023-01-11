package com.example.urlshortener.exception;

public class NotValidUrlException extends RuntimeException{
    public NotValidUrlException() {
    }

    public NotValidUrlException(String message) {
        super(message);
    }
}
