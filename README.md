# 🌐 URL Analyzer – Backend

This is the backend service for the **URL Analyzer** full-stack app. It accepts a URL, parses the webpage, and returns:
- A breakdown of image types (e.g. `.jpg`, `.png`) with their count and estimated size
- All internal and external links found on the page

---

## 🚀 Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3
- **HTML Parsing**: Jsoup
- **Validation**: Jakarta Bean Validation (JSR-380)
- **Documentation**: Springdoc OpenAPI (Swagger UI)
- **Observability**: Spring Boot Actuator
- **Testing**: JUnit 5, Spring `@MockMvc`
- **Linting**: Checkstyle via Maven
- **Rate Limiting**: Custom IP-based filter

---

## 🐳 Running the App (Docker)

Use Docker Compose to build and start the backend:

```bash
docker-compose up --build
```
- API Base URL: http://localhost:8080/api/v1/analyze
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health

## 📤 API Reference

POST /api/analyze

Request Body:
```json
{
  "url": "https://example.com"
}
```
Response:
```json
{
  "images": {
    ".jpg": { "count": 3, "totalBytes": 123456 },
    ".png": { "count": 2, "totalBytes": 78901 }
  },
  "internalLinks": [
    "https://example.com/about"
  ],
  "externalLinks": [
    "https://external.com"
  ]
}
```

## 🔐 Features

### ✅ Input Sanitization
* Validates URL presence and format with @NotBlank
* Prepends missing schemes (https://)

### ✅ Rate Limiting
* Per-IP filter, allows 60 requests/minute

### ✅ Logging
* SLF4J with info, warn, and error levels
* Correlates actions by IP and URL

### ✅ Linting with Checkstyle
To lint your codebase inside Docker:
```bash
docker-compose run backend-fix-lints
```
Uses a strict ruleset: final variables, no star imports, proper JavaDoc, no unused imports, etc.

### 📈 Observability

| Endpoint            | Description              |
| ------------------- | ------------------------ |
| `/actuator/health`  | Health check             |
| `/actuator/metrics` | Prometheus-style metrics |
| `/swagger-ui.html`  | Interactive API explorer |
| `/v3/api-docs`      | Raw OpenAPI spec (JSON)  |

### 🧪 Testing
Run tests:
```bash
docker-compose run backend-tests
```

### 📝 Future Enhancements

* Redis caching for repeat requests
* Async task queue for non-blocking fetch
* Authentication for protected usage
* Export metrics to Prometheus/Grafana