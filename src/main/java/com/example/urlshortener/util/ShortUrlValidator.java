package com.example.urlshortener.util;

import com.example.urlshortener.exception.NotValidUrlException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlValidator {
    public boolean validateUrl(String url) {
        if(UrlValidator.getInstance().isValid(url)){
            return true;
        }

        throw new NotValidUrlException("유효하지 않은 URL 형식입니다.");
    }
}
