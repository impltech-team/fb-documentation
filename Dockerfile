FROM openjdk:25s-slim

WORKDIR /app

COPY *.jar app.jar

RUN apt update && apt install -y curl

CMD ["java", "-jar", "app.jar"]
