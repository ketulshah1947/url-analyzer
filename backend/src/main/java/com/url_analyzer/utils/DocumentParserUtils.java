package com.url_analyzer.utils;

import com.url_analyzer.model.ImageStats;
import com.url_analyzer.model.Links;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@UtilityClass
public class DocumentParserUtils {

  public Links getLinks(String baseHost, Elements links) throws MalformedURLException {
    Set<String> internalLinks = new HashSet<>();
    Set<String> externalLinks = new HashSet<>();
    for (Element link : links) {
      String href = link.absUrl("href");
      if (!href.isEmpty()) {
        URL hrefUrl = new URL(href);
        String linkHost = hrefUrl.getHost().replaceFirst("^www\\.", "");
        if (linkHost.equals(baseHost)) {
          internalLinks.add(href);
        } else {
          externalLinks.add(href);
        }
      }
    }
    return new Links(internalLinks, externalLinks);
  }

  public Map<String, ImageStats> getImageSets(Elements images) {
    Map<String, ImageStats> imageStats = new HashMap<>();
    for (Element img : images) {
      String src = img.absUrl("src");
      if (!src.isEmpty()) {
        String ext =
            src.contains(".") ? src.substring(src.lastIndexOf(".")).split("\\?")[0] : "unknown";
        imageStats.putIfAbsent(ext, new ImageStats(0, 0));
        imageStats.get(ext).incrementCount();
      }
    }
    return imageStats;
  }
}
