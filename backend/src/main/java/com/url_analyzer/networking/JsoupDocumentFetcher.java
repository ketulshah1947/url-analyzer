package com.url_analyzer.networking;

import com.url_analyzer.model.DocumentInfo;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class JsoupDocumentFetcher implements DocumentFetcher {

  @Override
  public DocumentInfo fetch(String url) throws IOException {
    DocumentInfo info = new DocumentInfo();
    if (url.startsWith("http://") || url.startsWith("https://")) {
      return new DocumentInfo(
          url, Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(10000).get());
    }
    try {
      String urlWithScheme = "https://" + url;
      return new DocumentInfo(
          urlWithScheme,
          Jsoup.connect(urlWithScheme).userAgent("Mozilla/5.0").timeout(10000).get());
    } catch (IOException e) {
      String urlWithScheme = "http://" + url;
      return new DocumentInfo(
          urlWithScheme,
          Jsoup.connect(urlWithScheme).userAgent("Mozilla/5.0").timeout(10000).get());
    }
  }
}
