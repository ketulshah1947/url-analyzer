package com.url_analyzer.networking;

import com.url_analyzer.model.DocumentInfo;
import java.io.IOException;

public interface DocumentFetcher {
  DocumentInfo fetch(String url) throws IOException;
}
