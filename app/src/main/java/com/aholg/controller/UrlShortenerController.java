package com.aholg.controller;

import com.aholg.repository.UrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    private final UrlProperties urlProperties;

    private final UrlRepository urlRepository;

    public UrlShortenerController(UrlProperties urlProperties, UrlRepository urlRepository) {
        this.urlProperties = urlProperties;
        this.urlRepository = urlRepository;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String storeUrl(@RequestParam(value = "url") String url) {
        String shortUrl = urlRepository.save(url);
        return String.format("www.%s:%s/%s", urlProperties.host(), urlProperties.port(), shortUrl);
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<String> getUrl(@PathVariable String urlId) {
        return urlRepository.retrieve(urlId)
                .map(url -> ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body(url))
                .orElse(ResponseEntity.notFound().build());
    }
}
