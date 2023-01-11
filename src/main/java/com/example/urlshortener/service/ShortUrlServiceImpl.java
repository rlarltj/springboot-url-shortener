package com.example.urlshortener.service;

import com.example.urlshortener.domain.ShortUrl;
import com.example.urlshortener.domain.Url;
import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.dto.ShortUrlResponse;
import com.example.urlshortener.encode.ShortUrlEncoder;
import com.example.urlshortener.encode.EncodeLayer;
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
    private final EncodeLayer encodeLayer;
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
                    .orElseGet(() -> {
                        ShortUrl encodedURL = encoding(originalUrl, urlRequest.getAlgorithm());
                        shortenUrlSet.add(encodedURL);
                        return encodedURL;
                    });

            return toShortUrlResponse(urlEntity, shortUrl.getShortUrl());
        }


        ShortUrl shortUrl = encoding(originalUrl, urlRequest.getAlgorithm());

        Url createURL = Url
                .builder()
                .shortUrl(Set.of(shortUrl))
                .originalUrl(originalUrl)
                .build();

        Url savedUrl = urlRepository.save(createURL);


        return toShortUrlResponse(savedUrl, shortUrl.getShortUrl());
    }


    @Override
    @Transactional
    public String decodeUrl(String shortUrl) {
        Url findURL = urlRepository.findUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException());

        findURL.getShortUrl()
                .stream()
                .filter(url -> Objects.equals(url.getShortUrl(), shortUrl))
                .findAny()
                .orElseThrow()
                .increaseCount();

        return findURL.getOriginalUrl();
    }


    private ShortUrl encoding(String originalURL, String algorithm){
        ShortUrlEncoder encoder = encodeLayer.findUrlEncoder(algorithm);
        String encodeURL = encoder.encode(originalURL);

        return ShortUrl.builder()
                .shortUrl(encodeURL)
                .algorithm(algorithm)
                .requestCount(FIRST_REQUEST)
                .build();
    }


    private ShortUrlResponse toShortUrlResponse(Url url, String shortUrl) {
        return ShortUrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortUrl(shortUrl)
                .build();
    }
}
