# Multi-stage build for Spring Boot Fibonacci Service
# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy the Maven POM and source code
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Build the application with Maven
# Skip tests in the container build - tests should be run in the CI pipeline before
RUN mvn package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre

# Install curl for health checks
RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Add a non-root user to run the application
RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --gid 1001 appuser

# Set ownership and permissions
RUN mkdir -p /app/logs && \
    chown -R appuser:appgroup /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE="production"

# Expose the application port
EXPOSE 8080

# Set the user to run the application
USER appuser

# Health check using Spring Boot Actuator
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Add metadata labels
LABEL maintainer="Example Team <example@example.com>"
LABEL org.opencontainers.image.title="Fibonacci Service"
LABEL org.opencontainers.image.description="Spring Boot application that calculates Fibonacci numbers"
LABEL org.opencontainers.image.version="1.0.0"
