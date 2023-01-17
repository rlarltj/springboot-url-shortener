package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.dto.ShortUrlResponse;
import com.example.urlshortener.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ApiController {
    private final ShortUrlService urlService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "api/v1/urls", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ShortUrlResponse getShortUrl(@RequestBody @Valid ShortUrlRequest urlRequest) {
        ShortUrlResponse shortUrlResponse = urlService.generateShortUrl(urlRequest);

        return shortUrlResponse;
    }

    @GetMapping("/{shortUrl}")
    public void redirectUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.decodeUrl(shortUrl);
        response.sendRedirect(originalUrl);
    }
}
