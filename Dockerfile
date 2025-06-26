FROM eclipse-temurin:21

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN apt update && apt install -y curl

CMD ["java", "-jar", "app.jar"]
