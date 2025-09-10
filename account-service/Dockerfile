FROM maven:3.9.9-amazoncorretto-17-al2023 as builder
WORKDIR /opt/app
COPY . .
COPY lib/domain-1.0.0-SNAPSHOT.jar ./domain.jar

RUN mvn install:install-file \
      -Dfile=./domain.jar \
      -DgroupId=com.getjavajob \
      -DartifactId=domain \
      -Dversion=1.0.0-SNAPSHOT \
      -Dpackaging=jar

RUN mvn clean package -DskipTests

FROM tomcat:11.0.5-jre17-temurin-jammy
WORKDIR /usr/local/tomcat/webapps
EXPOSE 8080

COPY --from=builder /opt/app/target/*.war ./ROOT.war
