package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;

public interface ShortUrlEncoder {
    String encode(Url url);

    int decode(String url);

    boolean supports(String algorithm);
}
