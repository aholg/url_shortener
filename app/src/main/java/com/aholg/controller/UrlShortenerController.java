package com.aholg.controller;

import com.aholg.domain.UrlShortener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    private final UrlProperties urlProperties;

    private final UrlShortener urlShortener;

    public UrlShortenerController(UrlProperties urlProperties, UrlShortener urlShortener) {
        this.urlProperties = urlProperties;
        this.urlShortener = urlShortener;
    }

    @GetMapping("/")
    public String shortUrl(@RequestParam(value = "url") String url) {
        String shortUrl = urlShortener.shortenedUrl(url);
        return String.format("www.%s:%s/%s", urlProperties.host(), urlProperties.port(), shortUrl);
    }
}
