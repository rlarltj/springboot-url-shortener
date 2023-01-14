package com.example.urlshortener.encode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base32EncoderTest {
    private Base32Encoder base32Encoder;

    @BeforeEach
    void setup() {
        base32Encoder = new Base32Encoder();
    }

    @Test
    void encoderTest() {
        //given
        String encodedUrl = base32Encoder.encode("https://www.naver.com");
        int limitLength = 8;

        //when & then
        assertTrue(encodedUrl.length() <= limitLength);
    }
}