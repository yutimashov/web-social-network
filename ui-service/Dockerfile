FROM maven:3.9.9-amazoncorretto-17-al2023 as builder
WORKDIR /opt/app
COPY . .

RUN mvn clean package -DskipTests

FROM tomcat:11.0.5-jre17-temurin-jammy
WORKDIR /usr/local/tomcat/webapps
EXPOSE 8080

COPY --from=builder /opt/app/target/*.war ./ROOT.war