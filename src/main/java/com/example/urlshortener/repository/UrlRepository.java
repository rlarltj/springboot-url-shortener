package com.example.urlshortener.repository;

import com.example.urlshortener.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByOriginalUrl(String url);

    @Query("select u from Url u where u.shortUrl in :shortUrl")
    Optional<Url> findUrlByShortUrl(@Param("shortUrl") String shortUrl);

}
