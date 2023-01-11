package com.example.urlshortener.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShortUrl {

    @Column(name = "short_url", unique = true, nullable = false)
    private String shortUrl;

    @Column(name = "algorithm", nullable = false)
    private String algorithm;

    @Column(name = "request_count", nullable = false)
    private int requestCount;

    public void increaseCount() {
        this.requestCount ++;
    }

    @Builder
    protected ShortUrl(String shortUrl, String algorithm, int requestCount) {
        checkNotNull(shortUrl, "단축 url은 null일 수 없습니다.");
        checkNotNull(algorithm, "인코딩 타입은 null일 수 없습니다.");
        checkArgument(requestCount > 0, "요청 횟수는 0보다 커야합니다.");
        this.shortUrl = shortUrl;
        this.algorithm = algorithm;
        this.requestCount = requestCount;
    }

}
