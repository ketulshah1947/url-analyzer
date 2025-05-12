package com.url_analyzer.dto;

import com.url_analyzer.model.ImageStats;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class UrlAnalysisResponse {
  private Map<String, ImageStats> images;
  private Set<String> internalLinks;
  private Set<String> externalLinks;

  public UrlAnalysisResponse(
      Map<String, ImageStats> images, Set<String> internalLinks, Set<String> externalLinks) {
    this.images = images;
    this.internalLinks = internalLinks;
    this.externalLinks = externalLinks;
  }
}
