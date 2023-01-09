package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Primary
public class Base32Encoder implements ShortUrlEncoder {
    public static final int SHORTEN_LENGTH = 8;
    private static final char[] BASE32_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray();
    public static final int BASE32 = 32;

    public String encode(Url url) {
        int id = url.getId();

        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(BASE32_CHAR[id % BASE32]);
            id /= BASE32;
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
            if ('0' <= ch && ch <= '9') {
                id = id * BASE32 + ch - '0';
            } else if ('A' <= ch && ch <= 'V') {
                id = id * BASE32 + ch - 'A' + 10;
            }
        }

        System.out.println("id = " + id);
        return id;
    }

    @Override
    public boolean supports(String algorithm) {
        return Objects.equals(EncodeType.BASE32.name(), algorithm);
    }
}
