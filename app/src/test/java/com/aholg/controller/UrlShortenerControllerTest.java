package com.aholg.controller;


import com.aholg.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(this.mockUrlProperties.host()).thenReturn("test.com");
        when(this.mockUrlProperties.port()).thenReturn(1010);

        String url = "www.url.com";
        String urlId = "shortUrl";
        when(this.mockUrlRepository.save(url)).thenReturn(urlId);

        this.mockMvc.perform(
                        post("/")
                                .param("url", url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("www.test.com:1010/%s", urlId))));
    }

    @Test
    void returnsBadRequestForMissingUrlParam() throws Exception {
        this.mockMvc.perform(
                        post("/"))
                .andExpect(status().isBadRequest());
    }
}

