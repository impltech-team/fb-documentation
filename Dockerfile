FROM gradle:8.6-jdk21 AS build

WORKDIR /app

COPY build.gradle .
COPY gradle gradle
COPY gradlew .

RUN chmod +x ./gradlew

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew build -x test --no-daemon

RUN VERSION=$(curl -s https://dependency-check.github.io/DependencyCheck/current.txt)
RUN curl -Ls "https://github.com/dependency-check/DependencyCheck/releases/download/v$VERSION/dependency-check-$VERSION-release.zip" --output dependency-check.zip
RUN unzip dependency-check.zip && dependency-check/bin/dependency-check.sh --out . --scan *.jar

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/odc-reports/* odc-reports/

CMD ["java", "-jar", "app.jar"]