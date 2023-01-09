package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.urlshortener.encode.Base32Encoder.SHORTEN_LENGTH;

@Component
//@Primary
public class Base62Encoder implements ShortUrlEncoder {
    private final int BASE62 = 62;
    private static final char[] BASE62_CHAR = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public String encode(Url url) {
        int id = url.getId();

        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(BASE62_CHAR[id % BASE62]);
            id /= BASE62;
        }

        if (shortUrl.length() > SHORTEN_LENGTH) {
            return shortUrl.substring(0, SHORTEN_LENGTH);
        }

        return shortUrl.toString();
    }

    public int decode(String url) {
        int id = 0;

        for (int i = 0; i < url.length(); i++) {
            char ch = url.charAt(i);
            if ('a' <= ch && ch <= 'z') {
                id = id * BASE62 + ch - 'a';
            } else if ('A' <= ch && ch <= 'Z') {
                id = id * BASE62 + ch - 'A' + 36;
            } else if ('0' <= ch && ch <= '9') {
                id = id * BASE62 + ch - '0' + 26;
            }
        }
        return id;
    }

    @Override
    public boolean supports(String algorithm) {
        return Objects.equals(EncodeType.BASE62.name(), algorithm);
    }
}
