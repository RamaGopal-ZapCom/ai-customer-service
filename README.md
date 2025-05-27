# Customer Service - Microservice

## 1. Project Overview

**Project Name:** Customer Service – Microservice  
**Description:**  
This microservice is part of the Customer Registration Platform. It handles customer data capture and secure storage using Java Spring Boot and MongoDB. It accepts customer registration data via the API Gateway, validates it, communicates with the Authentication Service to obtain a JWT token, and stores the data in MongoDB.

---

## 2. Prerequisites

- **Java Version:** 21  
- **Spring Boot Version:** 3.0.4  
- **Database:** MongoDB 6.0.21  
- **Build Tool:** Gradle 8.4  
- **Other Tools:**  
  - IntelliJ IDEA Community Edition 2023.3.1  
  - Postman or Swagger (for API testing)

---

## 3. Project Setup

**Clone the Repository:**
```bash
git clone https://zapcomai@dev.azure.com/zapcomai/ACSA%20-%20AI%20Customer%20Support%20Agents/_git/OptimusAPI_Customer_service
```

**Build the Project:**
```bash
./gradlew clean build
```

**Run the Application:**
```bash
./gradlew bootRun
```

---

## 4. Configuration

**Environment Configurations:**
- Ensure MongoDB is running locally.
- Start MongoDB Compass:  
  - Click on **Add new Connection** → **Save and Connect**

**Database Connection Settings:**
- Located in `application.properties` or `application.yml`.  
  Example:
  ```properties
  spring.data.mongodb.uri=mongodb://localhost:27017/customerDB
  ```
**Swagger Dependency**
```properties
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'
```

---

## 5. Directory Structure

```plaintext
customer-service/
├── .gradle/
├── .idea/
├── build/
├── gradle/
├── scripts/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── zapcom/
│   │   │           ├── advice/
│   │   │           │   └── CustomerServiceAdvice.java
│   │   │           ├── config/
│   │   │           │   └── CustomerServiceConfiguration.java
│   │   │           ├── controller/
│   │   │           │   └── CustomerController.java
│   │   │           ├── entity/
│   │   │           │   ├── Customer.java
│   │   │           │   └── ApiKey.java
│   │   │           ├── exceptions/
│   │   │           │   └── CustomerServiceException.java
│   │   │           ├── model/
│   │   │           │   ├── AdminDetails.java
│   │   │           │   ├── AdminPrimary.java
│   │   │           │   ├── AdminTechnical.java
│   │   │           │   ├── Agreements.java
│   │   │           │   ├── ApiConfiguration.java
│   │   │           │   ├── ApiKeyRequest.java
│   │   │           │   ├── BankingDetails.java
│   │   │           │   ├── Branding.java
│   │   │           │   ├── ChatbotConfig.java
│   │   │           │   ├── CustomerProfile.java
│   │   │           │   ├── CustomerRequest.java
│   │   │           │   ├── LegalAndTaxCompliance.java
│   │   │           │   ├── OperatingHours.java
│   │   │           │   ├── Operations.java
│   │   │           │   ├── RegisteredAddress.java
│   │   │           │   ├── Staff.java
│   │   │           │   ├── Theme.java
│   │   │           │   └── response/
│   │   │           │       └── CustomerResponse.java
│   │   │           ├── repository/
│   │   │           │   ├── CustomerServiceRepository.java
│   │   │           │   └── ApiKeyRepository.java
│   │   │           ├── service/
│   │   │           │   └── impl/
│   │   │           │       ├── CustomerServiceImpl.java
│   │   │           │       └── CustomerService.java
│   │   │           └── CustomerServiceApplication.java
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
│       └── java/
│           └── com/
│               └── zapcom/
│                   └── CustomerServiceApplicationTests.java
├── .gitattributes
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```



---

## 6. API Documentation

**Swagger Setup (if available):**  
URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 7. API Endpoints

- **POST `/api/customer`**  
  Receives customer details from the Authentication Service and stores them in the database.

- **POST `/api/api-key`**  
  Stores the API key received from the Authentication Service into the database.
---

## 8. Error Handling

**Error Handling Description:**
- All exceptions are managed using global exception handlers.
- Errors are returned with appropriate HTTP status codes and JSON body.

**Common Error Messages:**
- `400 Bad Request`: Invalid or incomplete request payload.
- `404 Not Found`: Requested resource does not exist.
- `500 Internal Server Error`: Unhandled server error.

---

## 9. Testing

- From IntelliJ: Right-click → Run Tests

**Run Unit and Integration Tests:**
```bash
./gradlew test
```

- Use Postman or Swagger for manual endpoint testing.


---

## 10. Deployment Instructions

**Local Deployment:**
- Start MongoDB locally or via Docker.
- Run the Spring Boot application using `./gradlew bootRun`.

**QA/Production Deployment:**
- Modify environment variables or `application.properties` for each environment.
- Use CI/CD pipeline for deployment on cloud infrastructure (e.g., Azure, AWS).

**Docker Setup (Optional):**
Create a `Dockerfile`:
```dockerfile
FROM openjdk:21
COPY build/libs/customer-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run Docker container:
```bash
docker build -t customer-service .
docker run -p 8080:8080 customer-service
```
---