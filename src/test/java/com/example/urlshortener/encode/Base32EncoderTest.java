package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base32EncoderTest {
    private Base32Encoder base32Encoder;
    private Url url;

    @BeforeEach
    void setup() {
        base32Encoder = new Base32Encoder();

        String longUrl = "https://www.naver.com";

        url = Url.builder()
                .id(1)
                .originalUrl(longUrl)
                .build();
    }

    @Test
    void encoderTest() {
        String encodedUrl = base32Encoder.encode(url);
        int limitLength = 8;

        System.out.println(encodedUrl);
        assertTrue(encodedUrl.length() <= limitLength);
    }
}