package com.example.urlshortener.repository;

import com.example.urlshortener.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByOriginalUrl(String url);

    @Query("select u from Url u join u.shortUrl su where su.shortUrl in :url")
    Optional<Url> findUrlByShortUrl(@Param("url") String shortUrl);
}
