package com.example.urlshortener.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "url")
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Integer id;

    @ElementCollection
    @AttributeOverrides({
            @AttributeOverride(name = "shortUrl",
                    column = @Column(name = "short_url", nullable = false)),
            @AttributeOverride(name = "algorithm",
                    column = @Column(name = "algorithm", nullable = false)),
            @AttributeOverride(name = "requestCount",
                    column = @Column(name = "request_count"))
    })
    @CollectionTable(
            name = "short_url",
            joinColumns = @JoinColumn(name = "url_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = "short_url")
    )
    @Column(name = "short_url")
    @Builder.Default
    private Set<ShortUrl> shortUrl = new HashSet<>();

    @Column(name = "original_url", nullable = false, unique = true)
    private String originalUrl;

}
