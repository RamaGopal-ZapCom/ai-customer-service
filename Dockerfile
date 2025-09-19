# Build stage with Gradle and JDK 21
FROM gradle:8.4-jdk21 AS builder

WORKDIR /app

# Copy Gradle files and source code
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Build the Spring Boot JAR
RUN ./gradlew clean build --no-daemon

# Runtime stage with lightweight JDK image
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8082

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
