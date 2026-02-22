# AdvAuthService

## Overview
AdvAuthService is a Spring-based authentication and authorization service designed to provide robust security mechanisms for modern applications.

## Complete Progress Flow
Please check the link for more details:
[Project Details and Flowchart](https://whimsical.com/jwt-authentication-and-authorization-complete-progress-flow-4vZSwY4L1exQbCD6abveoq)

## Features
- User authentication and session management
- Role-based access control (RBAC)
- JWT token support
- Secure password hashing
- RESTful API endpoints

## Prerequisites
- Java 11+
- Spring Boot 2.7+
- Maven or Gradle
- MySQL/PostgreSQL (or your preferred database)

## Installation

1. Clone the repository
2. Navigate to the project directory
3. Configure `application.properties` with your database credentials
4. Run `mvn clean install`
5. Start the application: `mvn spring-boot:run`

## Configuration
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/authdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

## API Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `POST /logout` - User logout
- `GET /api/users/profile` - Get user profile


## Support
For issues and questions, please open an issue in the repository.