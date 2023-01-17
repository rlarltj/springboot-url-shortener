package com.example.urlshortener.domain;

import com.example.urlshortener.encode.EncodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlTest {

    @Test
    @DisplayName("단축 url, 단축 알고리즘, 요청 횟수가 정상인 값이면 ShortUrl 생성에 성공한다.")
    void 단축URL_생성() {
        //given
        String shortUrl = "goo.gl";
        String algorithm = EncodeType.BASE32.name();
        int requestCount = 1;

        //when & then
        assertDoesNotThrow(() ->
                ShortUrl.builder()
                        .shortUrl(shortUrl)
                        .algorithm(algorithm)
                        .requestCount(requestCount)
                        .build());
    }

    @Test
    @DisplayName("단축 url이 누락되면 ShortUrl 생성에 실패한다.")
    void 단축URL_생성_실패1() {
        //given
        String shortUrl = null;
        String algorithm = EncodeType.BASE32.name();
        int requestCount = 1;

        //when & then
        assertThrows(NullPointerException.class, () ->
                ShortUrl.builder()
                        .shortUrl(shortUrl)
                        .algorithm(algorithm)
                        .requestCount(requestCount)
                        .build());
    }

    @Test
    @DisplayName("단축 알고리즘이 누락되면 ShortUrl 생성에 실패한다.")
    void 단축URL_생성_실패2() {
        //given
        String shortUrl = "goo.gl";
        String algorithm = null;
        int requestCount = 1;

        //when & then
        assertThrows(NullPointerException.class, () ->
                ShortUrl.builder()
                        .shortUrl(shortUrl)
                        .algorithm(algorithm)
                        .requestCount(requestCount)
                        .build());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -100, -100000})

    @DisplayName("요청 횟수가 0보다 작은 값이면 ShortUrl 생성에 실패한다.")
    void 단축URL_생성_실패3(int requestCount) {
        //given
        String shortUrl = "goo.gl";
        String algorithm = EncodeType.BASE32.name();

        //when & then
        assertThrows(IllegalArgumentException.class, () ->
                ShortUrl.builder()
                        .shortUrl(shortUrl)
                        .algorithm(algorithm)
                        .requestCount(requestCount)
                        .build());

    }
}