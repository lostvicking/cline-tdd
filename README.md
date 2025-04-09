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
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

## Running Tests
```bash
mvn test
```

## TDD Workflow
1. Write a failing test
2. Implement the minimum code to make the test pass
3. Refactor the code while keeping the tests passing
