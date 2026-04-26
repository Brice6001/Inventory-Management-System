# 1. Build Stage: Use a Maven image with JDK 17
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copy the Maven project definition files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download dependencies (caching this step saves time)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# 2. Run Stage: Use Eclipse Temurin (official OpenJDK replacement)
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
