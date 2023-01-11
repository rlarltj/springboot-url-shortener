package com.example.urlshortener.encode;

import com.example.urlshortener.exception.NoEncoderFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EncodeLayer {
    private final List<ShortUrlEncoder> urlEncoderList;

    public EncodeLayer(List<ShortUrlEncoder> urlEncoderList) {
        this.urlEncoderList = urlEncoderList;
    }

    public ShortUrlEncoder findUrlEncoder(String algorithm) {
        return urlEncoderList.stream()
                .filter(encoder -> encoder.supports(algorithm))
                .findAny()
                .orElseThrow(() -> new NoEncoderFoundException());
    }
}
