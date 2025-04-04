FROM maven:3.9.9-amazoncorretto-17-al2023 as builder
WORKDIR /opt/app
COPY . .
RUN mvn clean install -DskipTests

FROM tomcat:11.0.5-jdk17-temurin-jammy
WORKDIR /opt/app
EXPOSE 8080

COPY --from=builder /opt/app/web/target/social.war /usr/local/tomcat/webapps/ROOT.war