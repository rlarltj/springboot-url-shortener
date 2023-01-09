package com.example.urlshortener.encode;

import com.example.urlshortener.domain.Url;
import com.google.common.io.BaseEncoding;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.nio.charset.StandardCharsets.*;

@Component
//@Primary
public class Base32Encoder implements ShortUrlEncoder {
    public static final int SHORTEN_LENGTH = 8;

    @Override
    public String encode(Url url) {
        String encode = BaseEncoding.base32()
                .encode(url.getOriginalUrl().getBytes(UTF_8));

        if(encode.length() > SHORTEN_LENGTH){
            return encode.substring(0, SHORTEN_LENGTH);
        }

        return encode;
    }

    @Override
    public boolean supports(String algorithm) {
        return Objects.equals(EncodeType.BASE32.name(), algorithm);
    }
}
