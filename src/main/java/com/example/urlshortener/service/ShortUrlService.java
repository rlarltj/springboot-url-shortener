package com.example.urlshortener.service;

import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.dto.ShortUrlResponse;

public interface ShortUrlService {
    ShortUrlResponse generateShortUrl(ShortUrlRequest urlRequest);
    String decodeUrl(String shortUrl);
}
