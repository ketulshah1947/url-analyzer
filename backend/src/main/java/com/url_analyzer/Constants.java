package com.url_analyzer;

import java.util.Set;

public class Constants {

  public static final Set<String> IMAGE_EXTENSIONS =
      Set.of(
          // Raster
          ".jpg",
          ".jpeg",
          ".png",
          ".gif",
          ".bmp",
          ".tiff",
          ".tif",
          ".webp",
          ".heic",
          ".heif",
          ".apng",
          ".ico",
          // Vector
          ".svg",
          ".eps",
          ".pdf",
          ".ai",
          // Raw
          ".raw",
          ".cr2",
          ".nef",
          ".arw",
          ".dng",
          ".orf",
          ".rw2",
          // Other
          ".psd",
          ".xcf",
          ".dds");
}
