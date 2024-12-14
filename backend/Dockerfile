FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM quay.io/wildfly/wildfly:28.0.0.Final-jdk17
EXPOSE 8080
COPY --from=build /app/target/backend.war /opt/jboss/wildfly/standalone/deployments/backend.war