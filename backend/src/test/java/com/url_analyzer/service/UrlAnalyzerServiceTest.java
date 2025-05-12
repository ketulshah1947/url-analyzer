package com.url_analyzer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.url_analyzer.dto.UrlAnalysisResponse;
import com.url_analyzer.model.DocumentInfo;
import com.url_analyzer.model.ImageStats;
import com.url_analyzer.networking.DocumentFetcher;
import java.util.Map;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlAnalyzerServiceTest {

  @Mock private DocumentFetcher fetcher;

  @InjectMocks private UrlAnalyzerService service;

  @Test
  void testAnalyzeUrl_withValidHtml_shouldReturnStats() throws Exception {
    String testUrl = "https://example.com";

    Document document = mock(Document.class);
    Elements imgElements = new Elements();
    Elements linkElements = new Elements();
    DocumentInfo documentInfo = new DocumentInfo(testUrl, document);

    Element img = mock(Element.class);
    when(img.absUrl("src")).thenReturn("https://example.com/image.png");
    imgElements.add(img);

    Element internalLink = mock(Element.class);
    when(internalLink.absUrl("href")).thenReturn("https://example.com/page");
    linkElements.add(internalLink);

    when(document.select("img[src]")).thenReturn(imgElements);
    when(document.select("a[href]")).thenReturn(linkElements);
    when(fetcher.fetch(testUrl)).thenReturn(documentInfo);

    UrlAnalysisResponse response = service.analyzeUrl(testUrl);

    Map<String, ImageStats> images = response.getImages();
    Set<String> internalLinks = response.getInternalLinks();
    Set<String> externalLinks = response.getExternalLinks();

    assertTrue(images.containsKey(".png"));
    assertEquals(1, images.get(".png").getCount());
    assertTrue(internalLinks.contains("https://example.com/page"));
    assertTrue(externalLinks.isEmpty());
  }

  @Test
  void testAnalyzeUrl_invalidUrl_shouldThrowException() {
    assertThrows(RuntimeException.class, () -> service.analyzeUrl("ht!tp://bad-url"));
  }

  @Test
  void testAnalyzeUrl_externalLink_shouldCategorizeCorrectly() throws Exception {
    String testUrl = "https://example.com";

    Document document = mock(Document.class);
    Elements imgElements = new Elements();
    Elements linkElements = new Elements();
    DocumentInfo documentInfo = new DocumentInfo(testUrl, document);

    Element externalLink = mock(Element.class);
    when(externalLink.absUrl("href")).thenReturn("https://external.com/page");
    linkElements.add(externalLink);

    when(document.select("img[src]")).thenReturn(imgElements);
    when(document.select("a[href]")).thenReturn(linkElements);
    when(fetcher.fetch(testUrl)).thenReturn(documentInfo);

    UrlAnalysisResponse response = service.analyzeUrl(testUrl);

    assertTrue(response.getExternalLinks().contains("https://external.com/page"));
    assertTrue(response.getInternalLinks().isEmpty());
  }

  @Test
  void testAnalyzeUrl_noImagesOrLinks_shouldReturnEmptyResults() throws Exception {
    String testUrl = "https://example.com";
    Document document = mock(Document.class);
    when(document.select("img[src]")).thenReturn(new Elements());
    when(document.select("a[href]")).thenReturn(new Elements());
    DocumentInfo documentInfo = new DocumentInfo(testUrl, document);
    when(fetcher.fetch(testUrl)).thenReturn(documentInfo);

    UrlAnalysisResponse response = service.analyzeUrl(testUrl);

    assertTrue(response.getImages().isEmpty());
    assertTrue(response.getInternalLinks().isEmpty());
    assertTrue(response.getExternalLinks().isEmpty());
  }

  @Test
  void testAnalyzeUrl_urlWithoutScheme_shouldStillWork() throws Exception {
    String testUrl = "www.example.com";
    Document document = mock(Document.class);
    when(document.select("img[src]")).thenReturn(new Elements());
    when(document.select("a[href]")).thenReturn(new Elements());
    DocumentInfo documentInfo = new DocumentInfo("https://example.com", document);
    when(fetcher.fetch(testUrl)).thenReturn(documentInfo);

    UrlAnalysisResponse response = service.analyzeUrl(testUrl);

    assertNotNull(response);
  }
}
