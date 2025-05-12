package com.url_analyzer.service;

import com.url_analyzer.dto.UrlAnalysisResponse;
import com.url_analyzer.model.DocumentInfo;
import com.url_analyzer.model.ImageStats;
import com.url_analyzer.model.Links;
import com.url_analyzer.model.UrlAnalysisResult;
import com.url_analyzer.networking.DocumentFetcher;
import com.url_analyzer.utils.DocumentParserUtils;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlAnalyzerService {

  private final DocumentFetcher fetcher;

  @Autowired
  public UrlAnalyzerService(DocumentFetcher fetcher) {
    this.fetcher = fetcher;
  }

  public UrlAnalysisResponse analyzeUrl(String url) {
    UrlAnalysisResult result = parse(url);
    return new UrlAnalysisResponse(
        result.getImages(), result.getInternalLinks(), result.getExternalLinks());
  }

  private UrlAnalysisResult parse(String urlStr) {
    try {
      DocumentInfo documentInfo = fetcher.fetch(urlStr);
      String url = documentInfo.getUrl();
      Document doc = documentInfo.getDocument();

      URL base = new URL(url);
      String baseHost = base.getHost().replaceFirst("^www\\.", "");

      Map<String, ImageStats> imageStats = DocumentParserUtils.getImageSets(doc.select("img[src]"));

      Links links = DocumentParserUtils.getLinks(baseHost, doc.select("a[href]"));

      return new UrlAnalysisResult(imageStats, links.getInternalLinks(), links.getExternalLinks());

    } catch (IllegalArgumentException | UnknownHostException e) {
      throw new IllegalArgumentException("Invalid URL: " + urlStr);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse URL: " + urlStr, e);
    }
  }
}
