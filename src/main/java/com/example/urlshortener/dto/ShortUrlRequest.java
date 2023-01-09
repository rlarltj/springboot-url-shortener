package com.example.urlshortener.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShortUrlRequest {
    private String originalUrl;
    private String algorithm;

}
