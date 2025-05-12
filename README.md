# URL Analyzer
This project has two components: frontend and backend. Both are different entities built in same repo.
If your concern is to run this whole app (both frontend and backend), 
simply spin up frontend (which will spin up backend as well) below command:
```bash
docker-compose up frontend --build
```
If you like to dig deeper than just interview evaluation, read further.


# URL Analyzer - Frontend

A React + TypeScript app to analyze web pages for image stats and nested links.

## Features
- Analyze any public URL for:
    - Image counts and byte sizes by type
    - Internal and external links
- Click on any discovered link to recursively analyze it
- Input validation and error handling
- Dockerized with NGINX for production
- Metrics logging with `web-vitals`

## Local Setup

```bash
npm install
npm start
```
or
```bash
docker-compose up frontend --build
```

## Run Tests

```bash
npm run test
```
or
```bash
docker-compose run frontend-tests
```

# URL Analyzer - Backend

## Run Lint fixes

```bash
npm run lint:fix
```
or
```bash
docker-compose run frontend-fix-lints
```

## Tech Stack
- React + TypeScript
- Axios for API calls
- Jest + RTL for testing
- CRA build + NGINX for deployment

## Future Enhancements
- Display images as thumbnails with lazy loading
- Client-side caching of previously analyzed URLs
- Support for PDF/Word/media links as distinct types

---

# URL Analyzer - Backend

This is the backend service for the **URL Analyzer** full-stack app. It accepts a URL, parses the webpage, and returns:
- A breakdown of image types (e.g. `.jpg`, `.png`) with their count and estimated size
- All internal and external links found on the page

## Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3
- **HTML Parsing**: Jsoup
- **Validation**: Jakarta Bean Validation (JSR-380)
- **Documentation**: Springdoc OpenAPI (Swagger UI)
- **Observability**: Spring Boot Actuator
- **Testing**: JUnit 5, Spring `@MockMvc`
- **Linting**: Checkstyle via Maven
- **Rate Limiting**: Custom IP-based filter

## Running the App (Docker)

Use Docker Compose to build and start the backend:

```bash
docker-compose up backend --build
```
- API Base URL: http://localhost:8080/api/v1/analyze
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health

## API Reference

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

## Features

### Input Sanitization
* Validates URL presence and format with @NotBlank
* Prepends missing schemes (https://)

### Rate Limiting
* Per-IP filter, allows 60 requests/minute

### Logging
* SLF4J with info, warn, and error levels
* Correlates actions by IP and URL

### Linting with Checkstyle
To lint your codebase inside Docker:
```bash
docker-compose run backend-fix-lints
```
Uses a strict ruleset: final variables, no star imports, proper JavaDoc, no unused imports, etc.

### Observability

| Endpoint            | Description              |
| ------------------- | ------------------------ |
| `/actuator/health`  | Health check             |
| `/actuator/metrics` | Prometheus-style metrics |
| `/swagger-ui.html`  | Interactive API explorer |
| `/v3/api-docs`      | Raw OpenAPI spec (JSON)  |

### Testing
Run tests:
```bash
docker-compose run backend-tests
```

### Future Enhancements

* Integration tests for controller
* Redis caching for repeat requests
* Async task queue for non-blocking fetch
* Authentication for protected usage
* Export metrics to Prometheus/Grafana