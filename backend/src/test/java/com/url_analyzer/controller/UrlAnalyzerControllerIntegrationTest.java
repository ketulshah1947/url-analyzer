package com.url_analyzer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.url_analyzer.dto.UrlAnalysisRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlAnalyzerControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void shouldReturn200AndJsonStructure_WhenValidUrl() throws Exception {
    UrlAnalysisRequest request = new UrlAnalysisRequest();
    request.setUrl("https://example.com");

    mockMvc
        .perform(
            post("/api/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.images").exists())
        .andExpect(jsonPath("$.internalLinks").exists())
        .andExpect(jsonPath("$.externalLinks").exists());
  }

  @Test
  void shouldReturn400_WhenMissingUrl() throws Exception {
    mockMvc
        .perform(post("/api/analyze").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest());
  }
}
