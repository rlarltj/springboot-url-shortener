package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import com.google.common.io.BaseEncoding;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.urlshortener.encode.Base32Encoder.SHORTEN_LENGTH;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Base64Encoder implements ShortUrlEncoder {

    @Override
    public String encode(String originalURL) {
        StringBuilder shortUrl = new StringBuilder();

        shortUrl.append(
                BaseEncoding.base64Url()
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
        return Objects.equals(EncodeType.BASE64.name(), algorithm);
    }
}
