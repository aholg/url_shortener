package com.aholg.domain;

import org.springframework.stereotype.Service;

@Service
public class SimpleUrlShortener implements UrlShortener {

    @Override
    public String shortenedUrl(String url) {
        return "shortUrl";
    }
}
