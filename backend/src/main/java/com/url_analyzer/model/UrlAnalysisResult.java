package com.url_analyzer.model;

import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class UrlAnalysisResult {
  private Map<String, ImageStats> images;
  private Set<String> internalLinks;
  private Set<String> externalLinks;

  public UrlAnalysisResult(
      Map<String, ImageStats> images, Set<String> internalLinks, Set<String> externalLinks) {
    this.images = images;
    this.internalLinks = internalLinks;
    this.externalLinks = externalLinks;
  }
}
