FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY /target/amorDePata-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]