package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Base62EncoderTest {
    private Base62Encoder base62Encoder;
    private Url url;

    @BeforeEach
    void setup() {
        base62Encoder = new Base62Encoder();

        String longUrl = "https://www.naver.com";

        url = Url.builder()
                .id(1)
                .originalUrl(longUrl)
                .build();
    }

    @Test
    void encoderTest() {
        String encodedUrl = base62Encoder.encode(url);
        int limitLength = 8;
        
        assertTrue(encodedUrl.length() <= limitLength);
    }
}