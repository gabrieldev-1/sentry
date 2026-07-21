# compilation phase
FROM maven:3.8-eclipse-temurin-17 AS constructor
WORKDIR  /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# execution fase
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=constructor /app/target/sentry-1.3.jar app.jar

USER 1000

ENTRYPOINT ["java", "-jar", "app.jar"]


