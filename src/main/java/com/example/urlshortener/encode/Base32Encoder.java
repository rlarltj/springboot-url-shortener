package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import com.google.common.io.BaseEncoding;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.nio.charset.StandardCharsets.*;

@Component
public class Base32Encoder implements ShortUrlEncoder {
    public static final int SHORTEN_LENGTH = 8;

    @Override
    public String encode(String originalURL) {
        StringBuilder shortUrl = new StringBuilder();

        shortUrl.append(
                BaseEncoding.base32()
                        .encode(originalURL.getBytes(UTF_8)));

        shortUrl.reverse();

        if(shortUrl.length() > SHORTEN_LENGTH){
            String substring = shortUrl.substring(0, SHORTEN_LENGTH);

            return substring;
        }

        return shortUrl.toString();
    }

    @Override
    public boolean supports(String algorithm) {
        return Objects.equals(EncodeType.BASE32.name(), algorithm);
    }
}
