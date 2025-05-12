package com.url_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfo {
  private String url;
  private Document document;
}
