package com.example.urlshortener.encode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Base64EncoderTest {
    private Base64Encoder base62Encoder;

    @BeforeEach
    void setup() {
        base62Encoder = new Base64Encoder();
    }

    @Test
    void encoderTest() {
        //given
        String encodedUrl = base62Encoder.encode("https://www.naver.com");
        int limitLength = 8;

        //when & then
        assertTrue(encodedUrl.length() <= limitLength);
    }
}