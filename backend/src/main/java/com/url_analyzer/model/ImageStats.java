package com.url_analyzer.model;

import lombok.Getter;

@Getter
public class ImageStats {
  private int count;
  private long totalBytes;

  public ImageStats(int count, long totalBytes) {
    this.count = count;
    this.totalBytes = totalBytes;
  }

  public void incrementCount() {
    this.count++;
  }
}
