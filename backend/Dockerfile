# ----------- STAGE 1: Build -----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn spotless:check
RUN mvn clean package -DskipTests

# ----------- STAGE 2: Run -----------
FROM eclipse-temurin:21-jdk
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT ["java", "-jar", "app.jar"]
