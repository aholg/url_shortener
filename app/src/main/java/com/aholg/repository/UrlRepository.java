package com.aholg.repository;

import java.util.Optional;

public interface UrlRepository {

    String save(String url);

    Optional<String> retrieve(String urlId);
}
