package com.example.urlshortener.service;

import com.example.urlshortener.domain.ShortUrl;
import com.example.urlshortener.domain.Url;
import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.dto.ShortUrlResponse;
import com.example.urlshortener.encode.ShortUrlEncoder;
import com.example.urlshortener.encode.UrlEncoders;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.util.ShortUrlValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository urlRepository;
    private final ShortUrlValidator urlValidator;
    private final UrlEncoders urlEncoder;
    public static final int FIRST_REQUEST = 1;

    @Override
    @Transactional
    public ShortUrlResponse generateShortUrl(ShortUrlRequest urlRequest) {
        String originalUrl = urlRequest.getOriginalUrl();
        urlValidator.validateUrl(originalUrl);

        Optional<Url> findUrl = urlRepository.findByOriginalUrl(originalUrl);

        if (findUrl.isPresent()) {
            Url urlEntity = findUrl.get();
            Set<ShortUrl> shortenUrlSet = urlEntity.getShortUrl();

            ShortUrl shortUrl = shortenUrlSet.stream()
                    .filter(urlSet -> Objects.equals(urlSet.getAlgorithm(), urlRequest.getAlgorithm()))
                    .findAny()
                    .orElseGet(() -> encoding(urlEntity, urlRequest.getAlgorithm()));


            shortenUrlSet.add(shortUrl);

            return toShortUrlResponse(urlEntity, shortUrl.getShortUrl());
        }


        Url savedUrl = urlRepository.save(Url.builder().originalUrl(originalUrl).build());
        ShortUrl shortUrl = encoding(savedUrl, urlRequest.getAlgorithm());

        savedUrl.getShortUrl().add(shortUrl);

        return toShortUrlResponse(savedUrl, shortUrl.getShortUrl());
    }

    @Override
    public String decodeUrl(String shortUrl) {
        Url findURL = urlRepository.findUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException());

        return findURL.getOriginalUrl();
    }


    private ShortUrl encoding(Url url, String algorithm){
        ShortUrlEncoder encoder = urlEncoder.findUrlEncoder(algorithm);
        String encodeURL = encoder.encode(url);

        return ShortUrl.builder()
                .shortUrl(encodeURL)
                .requestCount(FIRST_REQUEST)
                .algorithm(algorithm)
                .build();
    }


    private ShortUrlResponse toShortUrlResponse(Url url, String shortUrl) {
        return ShortUrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortUrl(shortUrl)
                .build();
    }
}
