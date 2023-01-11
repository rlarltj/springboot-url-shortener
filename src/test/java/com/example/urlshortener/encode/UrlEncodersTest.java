package com.example.urlshortener.encode;

import com.example.urlshortener.exception.NoEncoderFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UrlEncodersTest {
    private UrlEncoders encoders;

    @BeforeEach
    void setup() {
        List<ShortUrlEncoder> encoderList = Arrays.asList(new Base32Encoder(), new Base62Encoder());
        this.encoders = new UrlEncoders(encoderList);
    }

    @Test
    @DisplayName("입력받은 Algorithm에 해당하는 인코더를 찾을 수 있다.")
    void 인코더_검색_성공() {
        String Base32Algorithm = EncodeType.BASE32.name();
        ShortUrlEncoder url32Encoder = encoders.findUrlEncoder(Base32Algorithm);

        String Base62Algorithm = EncodeType.BASE62.name();;
        ShortUrlEncoder url62Encoder = encoders.findUrlEncoder(Base62Algorithm);

        assertTrue(url32Encoder instanceof Base32Encoder);
        assertTrue(url62Encoder instanceof Base62Encoder);
    }

    @Test
    @DisplayName("입력받은 Algorithm에 해당하는 인코더가 존재하지 않는 경우 예외가 발생한다.")
    void 인코더_검색_실패() {
        String strangeAlgorithm = "WonderfulEncoder";

        assertThrows(NoEncoderFoundException.class, () -> encoders.findUrlEncoder(strangeAlgorithm));
    }
}