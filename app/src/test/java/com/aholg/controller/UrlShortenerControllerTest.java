package com.aholg.controller;


import com.aholg.domain.UrlShortener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UrlShortenerController.class)
public class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlProperties mockUrlProperties;

    @MockBean
    private UrlShortener mockUrlShortener;

    @Test
    public void returnsShortenedUrl() throws Exception {
        when(this.mockUrlProperties.host()).thenReturn("test.com");
        when(this.mockUrlProperties.port()).thenReturn(1010);

        String url = "www.url.com";
        String shortUrl = "shortUrl";
        when(this.mockUrlShortener.shortenedUrl(url)).thenReturn(shortUrl);

        this.mockMvc.perform(
                        get("/")
                                .queryParam("url", url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("www.test.com:1010/%s", url))));
    }

    @Test
    public void returnsBadRequestForMissingUrlParam() throws Exception {
        this.mockMvc.perform(
                        get("/"))
                .andExpect(status().isBadRequest());
    }
}

