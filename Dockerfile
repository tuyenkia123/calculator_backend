FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /calculator_backend
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /calculator_backend
COPY --from=build /calculator_backend/target/*.jar calculator_backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "calculator_backend-0.0.1-SNAPSHOT.jar"]