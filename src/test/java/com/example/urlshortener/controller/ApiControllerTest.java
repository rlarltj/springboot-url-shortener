package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortUrlRequest;
import com.example.urlshortener.encode.EncodeType;
import com.example.urlshortener.service.ShortUrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShortUrlService shortUrlService;

    @Test
    void 인코딩_요청_성공() throws Exception {
        //given
        ShortUrlRequest urlRequest = ShortUrlRequest.builder()
                .originalUrl("https://www.google.com")
                .algorithm(EncodeType.BASE32.name())
                .build();

        //when & then
        mockMvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urlRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void 디코딩_요청_성공() throws Exception {
        //given
        String originalUrl = "https://www.google.com";
        String shortUrl = "goo.gl";

        // when & then
        when(shortUrlService.decodeUrl(shortUrl)).thenReturn(originalUrl);

        mockMvc.perform(get("/{shortUrl}", shortUrl))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        verify(shortUrlService).decodeUrl(shortUrl);
    }
}