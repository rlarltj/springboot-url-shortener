package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;

public interface ShortUrlEncoder {
    String encode(String originalURL);

    boolean supports(String algorithm);
}
