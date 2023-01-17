package com.example.urlshortener.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "url")
@Entity
public class Url {
    @Id
    @Column(name = "url_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "url", orphanRemoval = true)
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
