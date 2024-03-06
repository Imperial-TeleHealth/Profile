FROM adoptopenjdk/openjdk11:armv7l-ubuntu-jre-11.0.23_4-ea-beta-nightly
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
