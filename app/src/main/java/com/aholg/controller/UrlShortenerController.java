package com.aholg.controller;

import com.aholg.repository.UrlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    private final UrlProperties urlProperties;

    private final UrlRepository urlRepository;

    public UrlShortenerController(UrlProperties urlProperties, UrlRepository urlRepository) {
        this.urlProperties = urlProperties;
        this.urlRepository = urlRepository;
    }

    @GetMapping("/")
    public String shortUrl(@RequestParam(value = "url") String url) {
        String shortUrl = urlRepository.save(url);
        return String.format("www.%s:%s/%s", urlProperties.host(), urlProperties.port(), shortUrl);
    }
}
