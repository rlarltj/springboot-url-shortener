package com.example.urlshortener.domain;


import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShortUrl {
    private String shortUrl;

    private String algorithm;

    private int requestCount;

    public void increaseCount() {
        this.requestCount ++;
    }

    @Builder
    protected ShortUrl(String shortUrl, String algorithm, int requestCount) {
        validateShortUrl(shortUrl);
        validateAlgorithm(algorithm);
        this.shortUrl = shortUrl;
        this.algorithm = algorithm;
        this.requestCount = requestCount;
    }

    private void validateShortUrl(String shortUrl) {
        if (!StringUtils.hasText(shortUrl)) {
            throw new IllegalArgumentException("shortURL은 null이 저장될 수 없습니다.");
        }
    }

    private void validateAlgorithm(String algorithm) {
        if (!StringUtils.hasText(algorithm)) {
            throw new IllegalArgumentException("단축 알고리즘은 null이 저장될 수 없습니다.");
        }
    }
}
