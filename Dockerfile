FROM openjdk:21-slim

WORKDIR /app

COPY *.jar app.jar

CMD ["java", "-jar", "app.jar"]