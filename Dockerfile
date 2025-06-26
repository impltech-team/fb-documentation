FROM amazoncorretto:21-al2023

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN dnf update -y && dnf install -y curl

CMD ["java", "-jar", "app.jar"]