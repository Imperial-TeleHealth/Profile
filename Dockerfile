FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY docker-context/demo-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
