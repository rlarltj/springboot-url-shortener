package com.example.urlshortener.domain;


import lombok.*;

import javax.persistence.*;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EqualsAndHashCode
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "short_url", unique = true, nullable = false)
    private String shortUrl;

    @Column(name = "algorithm", nullable = false)
    private String algorithm;

    @Column(name = "request_count", nullable = false)
    private int requestCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id")
    private Url url;

    public void increaseCount() {
        this.requestCount ++;
    }

    @Builder
    protected ShortUrl(String shortUrl, String algorithm, int requestCount, Url url) {
        checkNotNull(shortUrl, "단축 url은 null일 수 없습니다.");
        checkNotNull(algorithm, "인코딩 타입은 null일 수 없습니다.");
        checkArgument(requestCount > 0, "요청 횟수는 0보다 커야합니다.");
        this.shortUrl = shortUrl;
        this.algorithm = algorithm;
        this.requestCount = requestCount;
        this.url = url;
    }


    public void registerUrl(Url url) {
        if (Objects.nonNull(this.url)) {
            this.url.getShortUrl().remove(this);
        }

        this.url = url;
        url.getShortUrl().add(this);
    }
}
