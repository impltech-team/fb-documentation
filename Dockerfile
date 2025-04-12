FROM openjdk:25-slim

WORKDIR /app

COPY *.jar app.jar

RUN apt update && apt install -y curl

CMD ["java", "-jar", "app.jar"]
