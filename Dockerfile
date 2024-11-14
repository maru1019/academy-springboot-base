# ビルドステージ
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app
COPY . /app
RUN ./gradlew build -x test

# 実行環境ステージ
FROM eclipse-temurin:17-alpine
WORKDIR /app
RUN mkdir -p /app
COPY --from=build /app/build/libs/spring-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
