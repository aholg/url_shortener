package com.aholg.controller;

import com.aholg.repository.UrlRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity getUrl(@PathVariable String urlId) {
        System.out.println(urlRepository.retrieve(urlId));
        return urlRepository.retrieve(urlId)
                .map(url ->
                        ResponseEntity
                                .status(HttpStatus.MOVED_PERMANENTLY)
                                .location(URI.create(url))
                                .build())
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
