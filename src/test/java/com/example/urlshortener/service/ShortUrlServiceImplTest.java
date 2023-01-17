package com.example.urlshortener.service;

import com.example.urlshortener.domain.ShortUrl;
import com.example.urlshortener.domain.Url;
import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.dto.ShortUrlResponse;
import com.example.urlshortener.encode.Base32Encoder;
import com.example.urlshortener.encode.EncodeType;
import com.example.urlshortener.encode.EncodeLayer;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.util.ShortUrlValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ShortUrlServiceImplTest {

    @InjectMocks
    ShortUrlServiceImpl shortUrlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @Spy
    private Base32Encoder encoder;

    @Mock
    private EncodeLayer encodeLayer;

    @Spy
    private ShortUrlValidator validator;

    private ShortUrlRequest shortUrlRequest;

    private String shortURL = "goo.gl";
    private String originalURL = "https://www.google.com";
    private String algorithm = EncodeType.BASE32.name();
    @BeforeEach
    void setup() {
        shortUrlRequest = ShortUrlRequest.builder()
                .algorithm(algorithm)
                .originalUrl(originalURL)
                .build();
    }

    @Test
    @Transactional
    void 단축Url_생성() {
        //given
        Url url = getUrl();

        when(urlRepository.findByOriginalUrl(originalURL)).thenReturn(Optional.empty());
        given(urlRepository.save(any())).willReturn(url);
        when(encodeLayer.findUrlEncoder(algorithm)).thenReturn(encoder);
        when(encoder.encode(originalURL)).thenReturn(shortURL);
        when(validator.validateUrl(originalURL)).thenReturn(true);

        //when
        ShortUrlResponse shortUrlResponse = shortUrlService.generateShortUrl(shortUrlRequest);

        verify(urlRepository).findByOriginalUrl(originalURL);
        verify(urlRepository).save(any());
        verify(encodeLayer).findUrlEncoder(algorithm);
        verify(encoder).encode(originalURL);
        verify(validator).validateUrl(originalURL);

        //then
        assertThat(shortUrlResponse)
                .hasFieldOrPropertyWithValue("originalUrl", originalURL)
                .hasFieldOrPropertyWithValue("shortUrl", shortURL);
    }

    @Test
    @Transactional
    void 원본URL_조회() {
        //given
        Url url = getUrl();

        when(shortUrlRepository.findShortUrlByShortUrl(shortURL))
                .thenReturn(Optional.of(ShortUrl.builder().shortUrl(shortURL).build()));

        //when
        String original = shortUrlService.decodeUrl(shortURL);

        verify(urlRepository).findUrlByShortUrl(shortURL);

        //then
        assertThat(original).isEqualTo(url.getOriginalUrl());
    }

    private Url getUrl() {
        return  Url.builder()
                .originalUrl(originalURL)
                .shortUrl(Set.of(ShortUrl
                        .builder()
                        .shortUrl(shortURL)
                        .requestCount(1)
                        .algorithm(algorithm)
                        .build()))
                .build();
    }
}