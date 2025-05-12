package com.url_analyzer.controller;

import com.url_analyzer.dto.UrlAnalysisRequest;
import com.url_analyzer.dto.UrlAnalysisResponse;
import com.url_analyzer.service.UrlAnalyzerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UrlAnalyzerController {

  private static final Logger logger = LoggerFactory.getLogger(UrlAnalyzerController.class);

  private final UrlAnalyzerService urlAnalyzerService;

  @Autowired
  public UrlAnalyzerController(UrlAnalyzerService service) {
    this.urlAnalyzerService = service;
  }

  @Operation(summary = "Analyze a URL for images and links")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid URL input"),
        @ApiResponse(responseCode = "500", description = "Server error")
      })
  @PostMapping("/analyze")
  public ResponseEntity<?> analyzeUrl(@Valid @RequestBody UrlAnalysisRequest request) {
    try {
      logger.info("Analyzing URL: {}", request.getUrl());
      UrlAnalysisResponse response = urlAnalyzerService.analyzeUrl(request.getUrl());
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      logger.warn("Bad input: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Error analyzing URL", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
    }
  }
}
