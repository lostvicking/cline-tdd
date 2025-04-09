# Docker for Fibonacci Service

This directory contains Docker configuration for running the Fibonacci Service in containers, optimized for Kubernetes deployments.

## Building the Docker Image

From the project root directory:

```bash
docker build -t fibonacci-service:latest -f docker/Dockerfile .
```

## Running the Docker Container Locally

```bash
docker run -p 8080:8080 fibonacci-service:latest
```

The service will be available at http://localhost:8080

## Environment Variables

The following environment variables can be used to configure the application:

| Variable | Description | Default |
|----------|-------------|---------|
| `JAVA_OPTS` | JVM options | `-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom` |
| `SPRING_PROFILES_ACTIVE` | Spring profile | `production` |

Example with custom settings:

```bash
docker run -p 8080:8080 \
  -e JAVA_OPTS="-Xms512m -Xmx1g" \
  -e SPRING_PROFILES_ACTIVE="staging" \
  fibonacci-service:latest
```

## Kubernetes Deployment

This image is optimized for Kubernetes with:

- Proper health checks via Spring Boot Actuator
- Resource-efficient JVM settings
- Non-root user for security
- Small image size through multi-stage build

See the Kubernetes deployment files in the `k8s/` directory (if available) for example deployment configurations.

## Image Details

- Base image: Eclipse Temurin 21 (JRE)
- Exposed port: 8080
- Health check: `/actuator/health` endpoint
- User: Non-root (UID 1001)
