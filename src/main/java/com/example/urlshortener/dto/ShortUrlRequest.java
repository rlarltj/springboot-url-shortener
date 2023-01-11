package com.example.urlshortener.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class ShortUrlRequest {
    @NotBlank(message = "URL을 입력해주세요.")
    private String originalUrl;

    @NotBlank(message = "인코딩 타입을 입력해주세요.")
    private String algorithm;
}
