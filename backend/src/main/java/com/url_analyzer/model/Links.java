package com.url_analyzer.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Links {
  private Set<String> internalLinks;
  private Set<String> externalLinks;
}
