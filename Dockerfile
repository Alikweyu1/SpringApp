# Build stage
FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml ./

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the JAR file from builder stage
COPY --from=builder /app/target/spring-boot-apharm-rest-api-0.0.1-SNAPSHOT.jar /app/app.jar

# Create directories for logs and uploads
RUN mkdir -p /app/logs /app/upload && \
    chmod -R 777 /app/logs /app/upload

# Create volumes
VOLUME ["/app/logs", "/app/upload"]

# Expose port
EXPOSE 8092

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=mysql", "--server.port=8092"]