package com.example.urlshortener.encode;


public interface ShortUrlEncoder {
    String encode(String originalURL);

    boolean supports(String algorithm);
}
