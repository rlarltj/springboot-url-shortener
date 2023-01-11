package com.example.urlshortener.domain;

import com.example.urlshortener.encode.EncodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UrlTest {

    private ShortUrl shortUrl;
    private String originalURL = "www.google.com";

    @BeforeEach
    void setup() {
        shortUrl = ShortUrl
                .builder()
                .shortUrl("goo.gl")
                .algorithm(EncodeType.BASE32.name())
                .requestCount(1)
                .build();
    }

    @Test
    @DisplayName("원본 URL, 단축 URL 필드가 정상적으로 입력된 경우 URL 생성에 성공한다.")
    void URL_생성_성공() {
        assertDoesNotThrow(() -> Url.builder()
                .originalUrl(originalURL)
                .shortUrl(Set.of(shortUrl))
                .build());
    }


    @Test
    @DisplayName("원본 URL 필드가 누락된 경우 URL 생성에 실패한다.")
    void URL_생성_실패1() {
        assertThrows(NullPointerException.class, () -> Url.builder()
                .originalUrl(null)
                .shortUrl(Set.of(shortUrl))
                .build());
    }

    @Test
    @DisplayName("단축 URL 필드가 비어있는 Set인 경우 URL 생성에 실패한다.")
    void URL_생성_실패2() {
        assertThrows(NullPointerException.class, () -> Url.builder()
                .originalUrl(null)
                .shortUrl(Collections.emptySet())
                .build());
    }
}