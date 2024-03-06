FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY docker-context/*.jar /app/
EXPOSE 8080
RUN ls -l /app
RUN  chmod +x /app/demo-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
