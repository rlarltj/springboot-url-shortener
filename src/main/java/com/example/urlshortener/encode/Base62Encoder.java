package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.urlshortener.encode.Base32Encoder.SHORTEN_LENGTH;

@Component
public class Base62Encoder implements ShortUrlEncoder {
    private final int BASE62 = 62;
    private static final char[] BASE62_CHAR = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public String encode(Url url) {
        int id = url.getId();

        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(BASE62_CHAR[id % BASE62]);
            id /= BASE62;
        }

        shortUrl.reverse();

        if (shortUrl.length() > SHORTEN_LENGTH) {
            String substring = shortUrl.substring(0, SHORTEN_LENGTH);
            return substring;
        }

        return shortUrl.toString();
    }

    @Override
    public boolean supports(String algorithm) {
        return Objects.equals(EncodeType.BASE62.name(), algorithm);
    }
}
