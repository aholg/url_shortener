package com.aholg.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SimpleUrlRepository implements UrlRepository {

    private final HashMap<String, String> urlMap = new HashMap<>();

    //TODO: Handle conflicting keys
    @Override
    public String save(String url) {
        String urlKey = UUID.randomUUID().toString().substring(0, 8);
        urlMap.put(urlKey, url);

        return urlKey;
    }

    @Override
    public Optional<String> retrieve(String urlId) {
        return Optional.ofNullable(urlMap.get(urlId));
    }
}
