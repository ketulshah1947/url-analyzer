package com.url_analyzer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Schema(description = "Request payload for URL analysis")
@Getter
@Setter
public class UrlAnalysisRequest {

  @NotBlank(message = "URL must not be blank")
  private String url;
}
