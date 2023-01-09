package com.example.urlshortener.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlValidator {
    public boolean validateUrl(String url) {
        if(UrlValidator.getInstance().isValid(url)){
            return true;
        }

        throw new IllegalArgumentException();
    }
}
