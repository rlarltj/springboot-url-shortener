package com.example.urlshortener.service;

import com.example.urlshortener.domain.ShortUrl;
import com.example.urlshortener.domain.Url;
import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.dto.ShortUrlResponse;
import com.example.urlshortener.encode.ShortUrlEncoder;
import com.example.urlshortener.encode.EncodeLayer;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.repository.UrlRepository;
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
    private final UrlRepository urlRepository;
    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlValidator urlValidator;
    private final EncodeLayer encodeLayer;
    public static final int FIRST_REQUEST = 1;

    @Override
    @Transactional
    public ShortUrlResponse generateShortUrl(ShortUrlRequest urlRequest) {
        String originalUrl = urlRequest.getOriginalUrl();
        urlValidator.validateUrl(originalUrl);

        Optional<Url> optionalUrl = urlRepository.findByOriginalUrl(originalUrl);

        if (optionalUrl.isPresent()) {
            Url findUrl = optionalUrl.get();
            Set<ShortUrl> shortUrlSet = findUrl.getShortUrl();

            ShortUrl shortUrl = findShortUrlOrEncode(urlRequest, originalUrl, shortUrlSet);
            shortUrl.registerUrl(findUrl);
            return toShortUrlResponse(findUrl, shortUrl);
        }

        ShortUrl shortUrl = encoding(originalUrl, urlRequest.getAlgorithm());

        Url url = createUrl(originalUrl, shortUrl);

        shortUrl.registerUrl(url);

        urlRepository.save(url);

        return toShortUrlResponse(url, shortUrl);
    }

    private Url createUrl(String originalUrl, ShortUrl shortUrl) {
        return Url
                .builder()
                .shortUrl(Set.of(shortUrl))
                .originalUrl(originalUrl)
                .build();
    }

    @Override
    @Transactional
    public String decodeUrl(String shortUrl) {
        ShortUrl findShortUrl = shortUrlRepository.findShortUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException("단축 URL을 찾을 수 없습니다."));

        findShortUrl.increaseCount();

        return findShortUrl.getUrl().getOriginalUrl();
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


    private ShortUrlResponse toShortUrlResponse(Url url, ShortUrl shortUrl) {
        return ShortUrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortUrl(shortUrl.getShortUrl())
                .build();
    }

    private ShortUrl findShortUrlOrEncode(ShortUrlRequest urlRequest, String originalUrl, Set<ShortUrl> shortenUrlSet) {
        return shortenUrlSet.stream()
                .filter(urlSet -> Objects.equals(urlSet.getAlgorithm(), urlRequest.getAlgorithm()))
                .findAny()
                .orElseGet(() -> {
                    ShortUrl encodedURL = encoding(originalUrl, urlRequest.getAlgorithm());
                    shortenUrlSet.add(encodedURL);
                    return encodedURL;
                });
    }

}
