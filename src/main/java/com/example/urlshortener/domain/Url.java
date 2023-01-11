package com.example.urlshortener.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "url")
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Integer id;

    @ElementCollection
    @Builder.Default
    private Set<ShortUrl> shortUrl = new HashSet<>();

    @Column(name = "original_url", nullable = false, unique = true)
    private String originalUrl;

    @Builder
    protected Url(Integer id, Set<ShortUrl> shortUrl, String originalUrl) {
        checkNotNull(originalUrl);
        checkState(shortUrl.size() > 0);
        this.id = id;
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
    }
}
