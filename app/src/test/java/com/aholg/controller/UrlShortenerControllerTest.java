package com.aholg.controller;


import com.aholg.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlProperties mockUrlProperties;

    @MockBean
    private UrlRepository mockUrlRepository;

    @Test
    void returnsShortenedUrl() throws Exception {
        String url = "http://www.url.com";
        String urlId = "shortUrl";

        when(this.mockUrlProperties.host()).thenReturn("test.com");
        when(this.mockUrlProperties.port()).thenReturn(1010);
        when(this.mockUrlRepository.save(url)).thenReturn(urlId);

        this.mockMvc.perform(
                        post("/")
                                .param("url", url))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(String.format("www.test.com:1010/%s", urlId))));
    }

    @Test
    void returnsBadRequestForMissingUrlParam() throws Exception {
        this.mockMvc.perform(
                        post("/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void resolvesShortenedUrl() throws Exception {
        String urlId = "urlId";
        String url = "http://www.url.com";

        when(this.mockUrlRepository.retrieve(urlId)).thenReturn(Optional.of(url));

        this.mockMvc.perform(
                        get("/{urlId}", urlId)
                )
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", url));
    }

    @Test
    void returnsNotFoundForNonExistingUrlId() throws Exception {
        when(this.mockUrlRepository.retrieve(any())).thenReturn(Optional.empty());

        this.mockMvc.perform(
                get("/{urlId}", "urlId")
        ).andExpect(status().isNotFound());
    }
}

