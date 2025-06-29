# Student Management System

A modern, professional student management system built with Spring Boot 3, providing comprehensive REST APIs for managing student records with advanced features like caching, validation, documentation, and monitoring.

## 🚀 Features

- **Complete CRUD Operations**: Create, read, update, and delete student records
- **Advanced Search**: Search students by name with pagination
- **Status Management**: Filter students by status (ACTIVE, INACTIVE, GRADUATED, SUSPENDED)
- **Data Validation**: Comprehensive input validation with detailed error messages
- **Caching**: Redis-ready caching for improved performance
- **API Documentation**: Interactive Swagger UI documentation
- **Monitoring**: Spring Boot Actuator endpoints for health checks and metrics
- **Exception Handling**: Global exception handling with consistent error responses
- **Database Auditing**: Automatic tracking of creation and modification timestamps
- **Optimistic Locking**: Version-based concurrency control
- **Professional Logging**: Structured logging with different levels for different environments

## 🛠️ Technology Stack

- **Java 22**: Latest LTS version with modern language features
- **Spring Boot 3.3.3**: Latest Spring Boot with enhanced performance
- **Spring Data JPA**: Simplified data access layer
- **MySQL**: Robust relational database
- **MapStruct**: Type-safe bean mapping
- **Lombok**: Reduced boilerplate code
- **SpringDoc OpenAPI**: API documentation
- **Spring Boot Actuator**: Production monitoring
- **Maven**: Dependency management and build tool

## 📋 Prerequisites

- Java 22 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd student-management-system
```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE studentdb;
```

### 3. Configuration

Update `src/main/resources/application.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/studentdb
    username: your_username
    password: your_password
```

### 4. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 🔗 API Endpoints

### Student Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/students` | Get all students (paginated) |
| GET | `/api/v1/students/active` | Get all active students |
| GET | `/api/v1/students/{id}` | Get student by ID |
| GET | `/api/v1/students/email/{email}` | Get student by email |
| POST | `/api/v1/students` | Create new student |
| PUT | `/api/v1/students/{id}` | Update student |
| DELETE | `/api/v1/students/{id}` | Delete student |
| GET | `/api/v1/students/search?q={term}` | Search students by name |
| GET | `/api/v1/students/status/{status}` | Get students by status |
| GET | `/api/v1/students/statistics` | Get student statistics |

### Monitoring

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/actuator/health` | Application health status |
| GET | `/actuator/info` | Application information |
| GET | `/actuator/metrics` | Application metrics |

## 📊 Sample API Requests

### Create a Student

```bash
curl -X POST http://localhost:8080/api/v1/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "address": "123 Main St, City, State 12345"
  }'
```

### Get All Students (Paginated)

```bash
curl "http://localhost:8080/api/v1/students?page=0&size=10&sortBy=lastName&sortDir=asc"
```

### Search Students

```bash
curl "http://localhost:8080/api/v1/students/search?q=John&page=0&size=10"
```

## 🏗️ Project Structure

```
src/main/java/edu/icet2/
├── StudentManagementApplication.java    # Main application class
├── config/                              # Configuration classes
│   ├── CacheConfig.java
│   └── OpenApiConfig.java
├── controller/                          # REST controllers
│   └── StudentController.java
├── dto/                                 # Data Transfer Objects
│   ├── ApiResponse.java
│   ├── StudentDto.java
│   ├── StudentCreateRequest.java
│   └── StudentUpdateRequest.java
├── entity/                              # JPA entities
│   └── Student.java
├── exception/                           # Exception handling
│   ├── DuplicateResourceException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── mapper/                              # MapStruct mappers
│   └── StudentMapper.java
├── repository/                          # Data access layer
│   └── StudentRepository.java
└── service/                            # Business logic
    ├── StudentService.java
    └── impl/
        └── StudentServiceImpl.java
```

## 🔧 Configuration Profiles

The application supports multiple profiles:

- **default**: Standard configuration
- **dev**: Development environment with detailed logging
- **prod**: Production environment with optimized settings

Run with specific profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 📈 Monitoring and Health Checks

The application includes comprehensive monitoring capabilities:

- **Health Checks**: `/actuator/health`
- **Application Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

## 🚀 Deployment

### Building for Production

```bash
mvn clean package -Pprod
```

### Docker Deployment

```dockerfile
FROM openjdk:22-jdk-slim
COPY target/student-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- **Student Management Team** - *Initial work* - [GitHub](https://github.com/your-org)

## 📞 Support

For support, email support@studentmanagement.edu or create an issue in the GitHub repository.

---

**Built with ❤️ using Spring Boot**