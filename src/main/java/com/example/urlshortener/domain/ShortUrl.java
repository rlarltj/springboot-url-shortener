package com.example.urlshortener.domain;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShortUrl {
    private String shortUrl;

    private String algorithm;

    private int requestCount;

    public void increaseCount() {
        this.requestCount ++;
    }
}
