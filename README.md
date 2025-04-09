# cline-tdd
Repo for playing around with Cline and TDD

## Project Overview
This is a Java 21 project set up with Maven, JUnit 5, and AssertJ for Test-Driven Development (TDD).

## Prerequisites
- Java 21 JDK
- Maven

## Project Structure
```
cline-tdd/
├── src/
│   ├── main/java/com/example/      # Application source code
│   └── test/java/com/example/      # Test source code
└── pom.xml                         # Maven configuration
```

## Building the Project
```bash
mvn clean install
```

## Running the Application
To start the Spring Boot service:
```bash
mvn spring-boot:run
```

The application will start on port 8080. You can access the API at http://localhost:8080/api/fibonacci

To stop the application, press `Ctrl+C` in the terminal.

## API Documentation
The API documentation is available through Swagger UI:
```bash
# Start the application first
mvn spring-boot:run

# Then open in your browser
http://localhost:8080/swagger-ui.html
```

You can also access the raw OpenAPI specification at:
```
http://localhost:8080/openapi.yaml
```

## Fibonacci API Endpoints
The following endpoints are available:

- `GET /api/fibonacci/{index}` - Get the Fibonacci number at the specified index
- `GET /api/fibonacci/next/{index}` - Get the next Fibonacci number after the specified index
- `GET /api/fibonacci/sequence?start={start}&count={count}` - Get a sequence of Fibonacci numbers starting from the specified index

## Running Tests
```bash
mvn test
```

## TDD Workflow
1. Write a failing test
2. Implement the minimum code to make the test pass
3. Refactor the code while keeping the tests passing
