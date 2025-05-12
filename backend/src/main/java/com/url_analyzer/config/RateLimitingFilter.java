package com.url_analyzer.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RateLimitingFilter implements Filter {
  private static final int MAX_REQUESTS_PER_MINUTE = 60;
  private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String ip = req.getRemoteAddr();

    requestCounts.putIfAbsent(ip, new AtomicInteger(0));
    int requests = requestCounts.get(ip).incrementAndGet();

    if (requests > MAX_REQUESTS_PER_MINUTE) {
      ((HttpServletResponse) response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.getWriter().write("Rate limit exceeded");
      return;
    }

    chain.doFilter(request, response);
  }
}
