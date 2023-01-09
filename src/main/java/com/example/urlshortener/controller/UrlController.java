package com.example.urlshortener.controller;

import com.example.urlshortener.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UrlController {
    private final ShortUrlService urlService;

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
