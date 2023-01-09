package com.example.urlshortener.encode;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlEncoders {
    private final List<ShortUrlEncoder> urlEncoderList;

    public UrlEncoders(List<ShortUrlEncoder> urlEncoderList) {
        this.urlEncoderList = urlEncoderList;
    }

    public ShortUrlEncoder findUrlEncoder(String algorithm) {
        return urlEncoderList.stream()
                .filter(encoder -> encoder.supports(algorithm))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
