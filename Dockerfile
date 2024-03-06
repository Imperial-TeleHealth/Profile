FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY docker-context/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
