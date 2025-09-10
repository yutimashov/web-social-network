FROM maven:3.9.9-amazoncorretto-17-al2023 as builder
WORKDIR /opt/app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8080

COPY --from=builder /opt/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]