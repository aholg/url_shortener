package com.aholg.repository;



import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleUrlRepositoryTest {

    @Test
    void shouldStoreUrl() {
        UrlRepository urlRepository = new SimpleUrlRepository();

        String url = "www.long-url.com";
        String urlId = urlRepository.save(url);
        Optional<String> retrievedUrl = urlRepository.retrieve(urlId);

        assertTrue(retrievedUrl.isPresent());
        assertEquals(url, retrievedUrl.get());
    }

    @Test
    void shouldReturnEmptyForNonExistantKey() {
        UrlRepository urlRepository = new SimpleUrlRepository();

        Optional<String> retrievedUrl = urlRepository.retrieve("randomKey");

        assertEquals(retrievedUrl, Optional.empty());
    }
}