package com.aholg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    private final String host;
    private final Integer port;

    public UrlShortenerController(UrlProperties urlProperties) {
        this.host = urlProperties.host();
        this.port = urlProperties.port();
    }

    @GetMapping("/")
    public String shortUrl(@RequestParam(value = "url") String url) {
        return String.format("www.%s:%s/%s", host, port, "woololololo");
    }
}
