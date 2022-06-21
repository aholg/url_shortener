package com.aholg.controller;

import com.aholg.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.Optional;

@Controller
public class UrlShortenerController extends DefaultErrorAttributes {

    private final UrlProperties urlProperties;

    private final UrlRepository urlRepository;

    private final Logger log = LoggerFactory.getLogger(UrlShortenerController.class);

    public UrlShortenerController(UrlProperties urlProperties, UrlRepository urlRepository) {
        this.urlProperties = urlProperties;
        this.urlRepository = urlRepository;
    }

    @PostMapping("/")
    public String storeUrl(@RequestParam(value = "url") String url) {
        String urlId = urlRepository.save(url);
        String shortUrl = String.format("www.%s:%s/%s", urlProperties.host(), urlProperties.port(), urlId);
        return String.format("redirect:/shortenUrl?shortUrl=%s", shortUrl);
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<Object> getUrl(@PathVariable String urlId) {
        return urlRepository.retrieve(urlId)
                .map(url ->
                        ResponseEntity
                                .status(HttpStatus.MOVED_PERMANENTLY)
                                .location(URI.create(url))
                                .build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/shortenUrl")
    public String getPage(@RequestParam(value = "shortUrl") Optional<String> shortUrl, Model model) {
        shortUrl.ifPresent(s -> model.addAttribute("shortUrl", s));
        return "shortenUrl.html";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleExceptions(Exception ex, WebRequest request) {
        log.error(String.format("Unknown error when handling request: %s", request.toString()), ex);
        return ResponseEntity.internalServerError().body("Unknown error occurred");
    }
}
