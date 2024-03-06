FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY docker-context/*.jar /app
RUN chmod +x /app/*.jar
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
