package com.example.urlshortener.util;

import com.example.urlshortener.exception.NotValidUrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"https://www.naver.com",
            "http://www.google.com",
            "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101",
            "https://mr-popo.tistory.com/226"})
    @DisplayName("올바른 형태의 url이 입력될 경우 true를 반환한다.")
    void 통과케이스(String url) {
        ShortUrlValidator validator = new ShortUrlValidator();

        assertTrue(validator.validateUrl(url));
    }


    @ParameterizedTest
    @ValueSource(strings = {"www.naver.com", "www.google.com"})
    @DisplayName("프로토콜(schema)이 누락된 경우 예외가 발생한다.")
    void 프로토콜_누락_오류(String url) {
        ShortUrlValidator validator = new ShortUrlValidator();

        assertThrows(NotValidUrlException.class, () -> validator.validateUrl(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://www.naver.c",
            "https://www.naver",
            "https://abc.def.ghi",
            "https:/www.naver.com"
        })
    @DisplayName("도메인의 형식이 올바르지 않은 경우 예외가 발생한다.")
    void 도메인_형식_오류(String url) {
        ShortUrlValidator validator = new ShortUrlValidator();

        assertThrows(NotValidUrlException.class, () -> validator.validateUrl(url));
    }
}