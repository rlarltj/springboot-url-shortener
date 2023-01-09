package com.example.urlshortener.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortUrlResponse {
    private String originalUrl;
    private String shortUrl;
}
